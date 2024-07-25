package org.example;

import java.io.*;
import java.util.*;


public class FileHandler {
    private final Map<String, String> options;
    private final List<String> targetFilesList;
    private final boolean append;
    private final boolean shortStats;
    private final boolean fullStats;
    private final TypeChecker typeChecker;


    public FileHandler(Map<String, String> options, List<String> targetFilesList, TypeChecker typeChecker) {
        this.options = options;
        this.targetFilesList = targetFilesList;
        this.append = options.containsKey("append");
        this.shortStats = options.containsKey("short");
        this.fullStats = options.containsKey("full");
        this.typeChecker = typeChecker;
    }

    private static Map<String, ArrayList<String>> getArrayListFromQueueForStat(Map<String, Queue<String>> dataTypesMap) {
        Map<String, ArrayList<String>> dataTypesMapForStats = new LinkedHashMap<>();
        for (Map.Entry<String, Queue<String>> entry : dataTypesMap.entrySet()) {
            String key = entry.getKey();
            Queue<String> valuesQueue = entry.getValue();
            ArrayList<String> arrayListFromQueue = new ArrayList<>(valuesQueue);
            dataTypesMapForStats.put(key, arrayListFromQueue);
        }
        return dataTypesMapForStats;
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
                    if (line != null && !line.isEmpty()) {
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
        Map<String, ArrayList<String>> dataTypesMapForStats = getArrayListFromQueueForStat(dataTypesMap);

        proceedWithResultMapAndWriteToFile(dataTypesMap);
        StatsGenerator.getStats(dataTypesMapForStats, fullStats, shortStats);
    }

    private void proceedWithResultMapAndWriteToFile(Map<String, Queue<String>> dataTypesMap) {
        String filePathNoFileName = buildFilePath();
        System.out.println("filePathNoFileName" + filePathNoFileName);
        String prefix = options.getOrDefault("prefix", "");
        System.out.println(prefix);
        for (Map.Entry<String, Queue<String>> entry : dataTypesMap.entrySet()) {
            String dataType = entry.getKey();
            Queue<String> queue = entry.getValue();
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePathNoFileName + prefix + dataType + ".txt", append));
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
        String filePath = "";
        if (!(options.get("output") == null)) {
            filePath = options.get("output");
            if (!filePath.endsWith("/")) {
                filePath = filePath + "/";
            }
            File directory = new File(filePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
        return filePath;
    }
}
