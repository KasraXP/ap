package exercises.ex6.store;

import exercises.ex6.utils.DirectoryTools;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HtmlFileManager {

    private final String baseSaveDirectory;

    public HtmlFileManager(String baseSaveDirectory) {
        this.baseSaveDirectory = baseSaveDirectory;
    }

    public void save(String relativeSavePath, List<String> lines) {
        try {
            String fullPath = Paths.get(baseSaveDirectory, relativeSavePath).toString();
            Path path = Paths.get(fullPath);
            DirectoryTools.createDirectory(path.getParent().toString());

            try (PrintWriter out = new PrintWriter(fullPath)) {
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
