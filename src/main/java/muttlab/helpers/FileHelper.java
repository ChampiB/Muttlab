package muttlab.helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {
    /**
     * Open file in writing mode (create file if not exists).
     * @param fileName: the file's name to open.
     * @return the file writer.
     * @throws IOException if an error occurred.
     */
    public static FileWriter openWriter(String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        return new FileWriter(file);
    }
}