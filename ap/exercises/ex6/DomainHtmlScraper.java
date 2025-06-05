package exercises.ex6;

import exercises.ex6.fetcher.HtmlFetcher;
import exercises.ex6.parser.HtmlParser;
import exercises.ex6.store.HtmlFileManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DomainHtmlScraper {

    private String domainAddress;
    private Queue<String> queue;
    private HtmlFileManager htmlFileManager;
    private Set<String> visitedUrls;
    private Set<String> visitedImages;
    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".svg");

    public DomainHtmlScraper(String domainAddress, String savePath) {
        this.domainAddress = domainAddress;
        this.queue = new LinkedList<>();
        this.htmlFileManager = new HtmlFileManager(savePath);
        this.visitedUrls = new HashSet<>();
        this.visitedImages = new HashSet<>();
    }

    public void start() throws IOException {
        
        exercises.ex6.utils.DirectoryTools.createDirectory("fetched_images");

        
        try (PrintWriter imageLinksWriter = new PrintWriter("fetched_images/image_links.txt")) {

            
            List<String> htmlLines = HtmlFetcher.fetchHtml(domainAddress);
            this.htmlFileManager.save(htmlLines);
            visitedUrls.add(domainAddress);

            List<String> urls = HtmlParser.getAllUrlsFromList(htmlLines);
            addUrlsToQueue(urls);

            int counter = 1;

            while (!queue.isEmpty()) {
                String url = queue.poll();

                if (visitedUrls.contains(url)) {
                    continue;
                }

                try {
                    htmlLines = HtmlFetcher.fetchHtml(url);
                    this.htmlFileManager.save(htmlLines);
                    visitedUrls.add(url);

                    urls = HtmlParser.getAllUrlsFromList(htmlLines);
                    addUrlsToQueue(urls);

                   
                    for (String extractedUrl : urls) {
                        if (isImageUrl(extractedUrl) && !visitedImages.contains(extractedUrl)) {
                            visitedImages.add(extractedUrl);
                            imageLinksWriter.println(extractedUrl);
                        }
                    }

                    System.out.println("[" + counter + "] " + url + " fetched and saved (queue size: " + queue.size() + ").");
                    counter++;

                } catch (Exception e) {
                    System.out.println("ERROR fetching " + url + ": " + e.getMessage());
                }
            }

            System.out.println("Operation complete.");
        }
    }

    private void addUrlsToQueue(List<String> urls) {
        for (String url : urls) {
            if (url != null && !visitedUrls.contains(url)) {
                queue.add(url);
            }
        }
    }

    private boolean isImageUrl(String url) {
        if (url == null) return false;
        String lowerUrl = url.toLowerCase();
        for (String ext : IMAGE_EXTENSIONS) {
            if (lowerUrl.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}
