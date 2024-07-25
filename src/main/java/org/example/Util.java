package org.example;

import java.util.List;
import java.util.Map;

public class Util {
    private static final CLIParser parser = new CLIParser();

    public static void main(String[] args) {
        parser.parse(args);
        Map<String, String> options = parser.getOptionsMap();
        List<String> targetFilesList = parser.getTargetFilesList();
        TypeChecker typeChecker = new TypeChecker();
        FileHandler fileHandler = new FileHandler(options, targetFilesList, typeChecker);
        fileHandler.processFilesList();
    }
}
