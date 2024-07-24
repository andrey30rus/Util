package org.example;

import org.apache.commons.cli.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

public class CLIParser {
    private List<String> targetFilesList = new ArrayList<>();
    private final Map<String, String> optionsMap = new HashMap<>();

    public List<String> getTargetFilesList() {
        return targetFilesList;
    }

    public Map<String, String> getOptionsMap() {
        return optionsMap;
    }

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

    {
        options.addOption(output);
        options.addOption(prefix);
        options.addOption(shortStat);
        options.addOption(fullStat);
        options.addOption(append);
        File currentDirFile;
        try {
            currentDirFile = new File(CLIParser.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        final String currentDir = currentDirFile.getParent();

    }

    public void parse(String[] args) {
        // create the parser

        CommandLineParser parser = new DefaultParser();
        CommandLine cmdLine = null;
        try {
            cmdLine = parser.parse(options, args);
            Option[] optionsList = cmdLine.getOptions();

            for (Option option : optionsList) {
                optionsMap.put(option.getLongOpt(), option.getValue());
            }
//            String output = cmdLine.getOptionValue("output");
//            System.out.println(output);
//            if (cmdLine.hasOption("s")) {
//                System.out.println("Short Stat is set");
//            }
            targetFilesList = cmdLine.getArgList().stream().filter(this::isExistTxtFile).toList();
//            System.out.println(targetFilesList + "-: targetFilesList");
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

    private boolean isExistTxtFile(String fileName) {
        File file = new File(fileName);
        return file.exists() && file.isFile() && fileName.endsWith(".txt");
    }
}
