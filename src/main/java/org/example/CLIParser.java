package org.example;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CLIParser {
    List<String> filesList = new ArrayList<>();
    Map<String, String> optionsMap = new HashMap<>();
    Options options = new Options();
    Option output = Option.builder("o")
            .longOpt("output")
            .argName("/some/path")
            .hasArg()
            .desc("Output Files Path")
            .build();

    {
        options.addOption(output);
    }


    public void parse(String[] args) {
        // create the parser
        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;
        try {
            // parse the command line arguments
            line = parser.parse(options, args);
            String option = line.getOptionValue("output");
            System.out.println(option);
        }
        catch (ParseException exp) {
            // oops, something went wrong
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }

    }
}
