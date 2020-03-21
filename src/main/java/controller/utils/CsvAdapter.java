package controller.utils;

import com.opencsv.CSVWriter;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class CsvAdapter {
    String localDbResourcesPath = "./database/";
    TextAdapter textAdapter = TextAdapter.getInstance();

    String create(String fileName, Set<String> words, List<Map<String, Integer>> data) {

        String filePath = localDbResourcesPath + fileName;

        List<String> wordsList = new ArrayList<>(words);
        String[] wordsArr = (String[]) wordsList.toArray();

        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
            csvWriter.writeNext(wordsArr);

            for (Map<String, Integer> curr : data) {
                List<String> tempRow = new ArrayList<>();

                for (int i = 0; i < wordsArr.length; i++) {
                    tempRow.add(curr.get(wordsArr[i]).toString());
                }

                csvWriter.writeNext((String[]) tempRow.toArray());
            }

            csvWriter.flush();
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }
}
