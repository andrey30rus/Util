package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    String data;
    String filePath;

    public FileHandler(String data, String filePath) {
        this.data = data;
        this.filePath = filePath;
    }

    public void appendDataToFile(String filePath){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))){
            bw.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

