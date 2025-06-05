package exercises.ex6.store;

import exercises.ex6.utils.DirectoryTools;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HtmlFileManager {
    private final String baseDir;

    public HtmlFileManager(String baseDir) {
        this.baseDir = baseDir;
        DirectoryTools.createDirectory(baseDir);
    }

    public void save(String relativePath, List<String> lines) {
        try {
            Path fullPath = Paths.get(baseDir, relativePath);
            DirectoryTools.createDirectory(fullPath.getParent().toString());

            try (PrintWriter out = new PrintWriter(fullPath.toFile())) {
                for (String line : lines) {
                    out.println(line);
                }
            }

            System.out.println("Saved: " + fullPath);
        } catch (Exception e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }
}