package exercises.ex6.store;

import exercises.ex6.utils.DirectoryTools;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HtmlFileManager {

    public HtmlFileManager() {

    }

    public void save(String saveFilePath, List<String> lines) {
        try {
            Path path = Paths.get(saveFilePath);
            DirectoryTools.createDirectory(path.getParent().toString());

            PrintWriter out = new PrintWriter(saveFilePath);
            for (String line : lines) {
                out.println(line);
            }
            out.close();

            System.out.println("Saved: " + saveFilePath);
        } catch (Exception e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }
}
