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
    private final Set<String> visitedImages = new HashSet<>();

    public DomainHtmlScraper(String domain, String saveDirectory) throws IOException {
        this.domain = domain;
        this.domainHost = new URL(domain).getHost();
        this.htmlFileManager = new HtmlFileManager(saveDirectory);
    }

    public void start() throws IOException {
        DirectoryTools.createDirectory("fetched_images");

        try (PrintWriter imageLinksWriter = new PrintWriter("fetched_images/image_links.txt")) {

            queue.add(domain);
            int counter = 0;

            while (!queue.isEmpty()) {
                String currentUrl = queue.poll();

                if (visitedUrls.contains(currentUrl)) continue;

                try {
                    List<String> htmlLines = HtmlFetcher.fetchHtml(currentUrl);
                    visitedUrls.add(currentUrl);
                    counter++;

                    String relativePath = getSavePathFromUrl(currentUrl);
                    htmlFileManager.save(relativePath, htmlLines);

                    List<String> foundUrls = HtmlParser.getAllUrlsFromList(htmlLines);

                    for (String url : foundUrls) {
                        try {
                            URL u = new URL(url);

                            if (isUrlInDomain(u) && !visitedUrls.contains(url)) {
                                queue.add(url);
                            }

                            if (isImageUrl(url) && !visitedImages.contains(url)) {
                                visitedImages.add(url);
                                imageLinksWriter.println(url);
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
        }
    }

    private boolean isUrlInDomain(URL url) {
        String host = url.getHost();
        return host.equals(domainHost) || host.endsWith("." + domainHost);
    }

    private boolean isImageUrl(String url) {
        String lower = url.toLowerCase();
        return lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg")
                || lower.endsWith(".gif") || lower.endsWith(".bmp") || lower.endsWith(".svg")
                || lower.endsWith(".webp");
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
}
