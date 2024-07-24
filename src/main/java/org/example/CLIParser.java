package org.example;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CLIParser {
    private final Map<String, String> optionsMap = new HashMap<>();
    private final Options options = new Options();
    private final Option output = Option.builder("o")
            .longOpt("output")
            .argName("/some/path")
            .hasArg()
            .desc("Output Files Path")
            .build();
    private final Option prefix = Option.builder("p")
            .longOpt("prefix")
            .argName("define prefix")
            .hasArg()
            .desc("Prefix for output files")
            .build();
    private final Option shortStat = Option.builder("s")
            .longOpt("short")
            .desc("Short Stat")
            .build();
    private final Option fullStat = Option.builder("f")
            .longOpt("full")
            .desc("full Stat")
            .build();
    private final Option append = Option.builder("a")
            .longOpt("append")
            .desc("append to exist files")
            .build();
    private List<String> targetFilesList = new ArrayList<>();

    {
        options.addOption(output);
        options.addOption(prefix);
        options.addOption(shortStat);
        options.addOption(fullStat);
        options.addOption(append);
    }

    public List<String> getTargetFilesList() {
        return targetFilesList;
    }

    public Map<String, String> getOptionsMap() {
        return optionsMap;
    }

    public void parse(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmdLine;
        try {
            cmdLine = parser.parse(options, args);
            Option[] optionsList = cmdLine.getOptions();

            for (Option option : optionsList) {
                optionsMap.put(option.getLongOpt(), option.getValue());
            }
            targetFilesList = cmdLine.getArgList().stream().filter(this::isExistTxtFile).toList();
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

    private boolean isExistTxtFile(String fileName) {
        File file = new File(fileName);
        return file.exists() && file.isFile() && fileName.endsWith(".txt");
    }
}
