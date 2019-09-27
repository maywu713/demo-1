package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {


    public static List<String> init(String... args) {
        List<String> rows = readRowsFromCSV("/Users/MAY/eclipse-workspace/demo-1/src/main/java/com/example/demo/AirQualityUCI.csv");
        return rows;
    }
    
    private static List<String> readRowsFromCSV(String fileName) {
    	 List<String> list = new ArrayList<>();
         Path pathToFile = Paths.get(fileName);
         
         try (BufferedReader br = Files.newBufferedReader(pathToFile,
                 StandardCharsets.US_ASCII)) {

             // read the first line from the text file
             String line = br.readLine();
             int lineCount = 0;
             // loop until all lines are read
             while (line != null) {
            	 
            	 if (lineCount == 0) {
            		 line = br.readLine();
            		 lineCount ++;
            	 }
            	 
            	 list.add(line.replaceAll(",", "."));
            	
                 // adding book into ArrayList

                 // read next line before looping
                 // if end of file reached, line would be null
                 line = br.readLine();
             }

         } catch (IOException ioe) {
             ioe.printStackTrace();
         }
         
         return list;
    }
}

