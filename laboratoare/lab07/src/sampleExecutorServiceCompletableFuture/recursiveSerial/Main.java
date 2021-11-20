package sampleExecutorServiceCompletableFuture.recursiveSerial;

import java.io.File;

public class Main {
    public static void findFile(String path, String filename) {
        File file = new File(path);
        if (file.isFile()) {
            if (file.getName().equals(filename)) {
                System.out.println(file.getAbsolutePath());
            }
        } else if (file.isDirectory()) {
            var files = file.listFiles();
            if (files != null) {
                for (var f : files) {
                    findFile(f.getPath(), filename);
                }
            }
        }
    }

    public static void main(String[] args) {
        findFile("files", "somefile.txt");
    }
}
