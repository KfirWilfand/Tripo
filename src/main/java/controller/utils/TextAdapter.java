package controller.utils;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

public class TextAdapter {

    // static variable single_instance of type Singleton
    private static TextAdapter single_instance = null;

    // static method to create instance of Singleton class
    public static TextAdapter getInstance() {
        if (single_instance == null)
            single_instance = new TextAdapter();

        return single_instance;
    }

    // private constructor restricted to this class itself
    private TextAdapter() {

    }

    public List<Double> getVectorByWord(List<Map<String, Double>> data, String word) {
        List<Double> wordVec = new ArrayList<Double>();

        for (Map<String, Double> textMap : data) {
            wordVec.add(textMap.get(word));
        }

        return wordVec;
    }

    public List<Map<String, Double>> getOccur(List<String> texts, Set<String> words) {
        List<Map<String, Double>> occurList = new ArrayList<>();

        for (String text : texts) {
            Map<String, Double> wordOccurText = new HashMap<>();

            for (String word : words) {
                double count = StringUtils.countMatches(text.toLowerCase(), word);
                wordOccurText.put(word, count);
            }

            occurList.add(wordOccurText);
        }

        return occurList;
    }

    public Set<String> getAllWordsFromTexts(List<String> texts) {
        Set<String> words = new HashSet();

        for (String text : texts) {
            words.addAll(textToWord(text));
        }

        return words;
    }

    Set<String> textToWord(String text) {
        String[] words = text.split("\\W");

        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].toLowerCase();
        }

        return new HashSet<>(Arrays.asList(words));
    }

}
