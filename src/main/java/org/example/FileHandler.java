package org.example;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

public class FileHandler {
    private final Map<String, String> options;
    private final List<String> targetFilesList;
    private final boolean append;
    private final String currentDir;
    private final TypeChecker typeChecker = new TypeChecker();

    {
        File currentDirFile;
        try {
            currentDirFile = new File(CLIParser.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        currentDir = currentDirFile.getParent();
    }

    public FileHandler(Map<String, String> options, List<String> targetFilesList) {
        this.options = options;
        this.targetFilesList = targetFilesList;
        this.append = options.containsKey("append");
    }

    public void processFilesList() {
        Map<String, Queue<String>> dataTypesMap = new LinkedHashMap<>();
        List<BufferedReader> bufferedReadersList = new ArrayList<>();
        for (String fileName : targetFilesList) {
            try {
                bufferedReadersList.add(new BufferedReader(new FileReader(fileName)));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        boolean filesNotEmpty = true;
        while (filesNotEmpty) {
            filesNotEmpty = false;
            for (BufferedReader reader : bufferedReadersList) {
                try {
                    String line = reader.readLine();
                    if (line != null) {
                        filesNotEmpty = true;
                        String dataType = typeChecker.checkDataType(line.strip());
                        Queue<String> queue = dataTypesMap.computeIfAbsent(dataType, k -> new LinkedList<>());
                        queue.add(line.strip());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        closeBufferReader(bufferedReadersList);
        proceedWithResultMapAndWriteToFile(dataTypesMap);
    }

    private void proceedWithResultMapAndWriteToFile(Map<String, Queue<String>> dataTypesMap) {
        String filePathNoFileName = buildFilePath();
        for (Map.Entry<String, Queue<String>> entry : dataTypesMap.entrySet()) {
            String dataType = entry.getKey();
            Queue<String> queue = entry.getValue();
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePathNoFileName + dataType + ".txt", append));
                while (!queue.isEmpty()) {
                    String data = queue.poll();
                    bufferedWriter.write(data + '\n');
                }
                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void closeBufferReader(List<BufferedReader> bufferedReadersList) {
        for (BufferedReader reader : bufferedReadersList) {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String buildFilePath() {
        String filePath = currentDir;
        if (!(options.get("output") == null)) {
            filePath = options.get("output");
            File directory = new File(filePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
        String prefix = options.get("prefix");
        filePath = addPrefix(filePath, prefix);
        return filePath;
    }

    private String addPrefix(String filePath, String prefix) {
        if (!(prefix == null)) {
            filePath = filePath + prefix;
        }
        return filePath;
    }
}
