package exercises.ex7;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConfigReader {
    private static final String CONFIG_PATH = "exercises/ex7/config";
    private static final String DEFAULT_STORAGE = "tabsplit";

    public static String getStorageType() {
        createConfigIfNotExists();

        try {
            File configFile = new File(CONFIG_PATH);
            Scanner scanner = new Scanner(configFile);

            if (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("storage=")) {
                    String storageType = line.substring(8).trim().toLowerCase();
                    if (storageType.equals("tabsplit") || storageType.equals("json") || storageType.equals("sqlite")) {
                        return storageType;
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Error reading config: " + e.getMessage());
        }
        return DEFAULT_STORAGE;
    }

    private static void createConfigIfNotExists() {
        File configFile = new File(CONFIG_PATH);

        File parentDir = configFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        if (!configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write("storage=" + DEFAULT_STORAGE);
            } catch (IOException e) {
                System.err.println("Error creating config: " + e.getMessage());
            }
        }
    }

    public static boolean setStorageType(String newStorageType) {
        if (!newStorageType.equals("tabsplit") && !newStorageType.equals("json") && !newStorageType.equals("sqlite")) {
            return false;
        }

        File configFile = new File(CONFIG_PATH);
        File parentDir = configFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("storage=" + newStorageType);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
            return false;
        }
    }
}