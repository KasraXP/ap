package exercises.ex6;

import exercises.ex6.fetcher.HtmlFetcher;
import exercises.ex6.parser.HtmlParser;
import exercises.ex6.store.HtmlFileManager;
import exercises.ex6.utils.DirectoryTools;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

public class DomainHtmlScraper {

    private final String domain;
    private final String domainHost;
    private final HtmlFileManager htmlFileManager;
    private final Queue<String> queue = new LinkedList<>();
    private final Set<String> visitedUrls = new HashSet<>();
    private final Set<String> downloadedImages = new HashSet<>();
    private final Set<String> downloadedSongs = new HashSet<>();

    public DomainHtmlScraper(String domain, String saveDirectory) throws IOException {
        this.domain = domain;
        this.domainHost = new URL(domain).getHost();
        this.htmlFileManager = new HtmlFileManager(saveDirectory);
    }

    public void start() throws IOException {

        DirectoryTools.createDirectory("fetched_images");
        DirectoryTools.createDirectory("fetched_music");

        try (PrintWriter imageLinksWriter = new PrintWriter("fetched_images/image_links.txt");
             PrintWriter songLinksWriter = new PrintWriter("fetched_music/song_links.txt")) {

            queue.add(domain);
            int counter = 0;

            while (!queue.isEmpty()) {
                String currentUrl = queue.poll();

                if (visitedUrls.contains(currentUrl)) continue;

                try {
                    Thread.sleep(Conf.K_SECONDS_DELAY * 1000L);

                    List<String> htmlLines = HtmlFetcher.fetchHtml(currentUrl);
                    visitedUrls.add(currentUrl);
                    counter++;

                    String relativePath = getSavePathFromUrl(currentUrl);
                    htmlFileManager.save(relativePath, htmlLines);

                    List<String> foundUrls = HtmlParser.getAllUrlsFromList(htmlLines);

                    for (String url : foundUrls) {
                        try {
                            URL u = new URL(url);

                            if (u.getHost().endsWith(domainHost) && !visitedUrls.contains(url)) {
                                queue.add(url);
                            }

                            if (isImageUrl(url) && !downloadedImages.contains(url)) {
                                downloadedImages.add(url);
                                imageLinksWriter.println(url);
                                imageLinksWriter.flush();
                            }

                            if (isAudioUrl(url) && !downloadedSongs.contains(url)) {
                                downloadedSongs.add(url);
                                songLinksWriter.println(url);
                                songLinksWriter.flush();
                            }

                        } catch (Exception ignored) {
                        }
                    }

                    System.out.println("[" + counter + "] Saved: " + currentUrl);

                } catch (Exception e) {
                    System.err.println("ERROR: " + currentUrl + " -> " + e.getMessage());
                }
            }

            System.out.println("Scraping completed. Total pages saved: " + counter);
            System.out.println("Total images: " + downloadedImages.size());
            System.out.println("Total songs: " + downloadedSongs.size());

        } catch (Exception e) {
            System.err.println("Global error: " + e.getMessage());
        }
    }

    private String getSavePathFromUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            String host = url.getHost();
            String path = url.getPath();

            if (path.endsWith("/")) path += "index.html";
            if (path.equals("")) path = "/index.html";

            String folder = "";
            if (!host.equals(domainHost)) {
                String sub = host.replace("." + domainHost, "");
                folder = "_" + sub;
            }

            return (folder + path).replaceAll("^/+", "");
        } catch (Exception e) {
            return "unknown_page_" + System.currentTimeMillis() + ".html";
        }
    }

    private boolean isImageUrl(String url) {
        return url.matches("(?i).+\\.(png|jpg|jpeg|gif|bmp|webp)$");
    }

    private boolean isAudioUrl(String url) {
        return url.matches("(?i).+\\.(mp3|wav|ogg|aac|flac)$");
    }
}
