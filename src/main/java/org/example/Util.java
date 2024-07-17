package org.example;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
    public static void main(String[] args) {
        String currentFolderPath;
        List<String> allowedParameters = Arrays.asList("o", "p");
        List<String> allArgs = List.of(args);
        System.out.println(allArgs);
        for(String arg: allArgs){
            System.out.println(arg);
        }
        System.out.println("Hello world!");
    }
}