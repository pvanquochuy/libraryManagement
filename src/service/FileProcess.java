package service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileProcess {
    private static final Logger logger = Logger.getLogger(FileProcess.class.getName());

    public static CompletableFuture<List<String>> readFromFileAsync(String filename) {
        return CompletableFuture.supplyAsync(() -> {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error reading file: " + filename, e);
            }
            return lines;
        });
    }

    public static CompletableFuture<Void> writeToFileAsync(String filename, List<String> data) {
        return CompletableFuture.runAsync(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (String line : data) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error writing to file: " + filename, e);
            }
        });
    }
}
