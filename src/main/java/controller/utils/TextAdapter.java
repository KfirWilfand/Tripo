package controller.utils;

import model.Text;
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

    public List<Double> getVectorByWord(Map<String , Map<String, Double>> data, String word) {
        List<Double> wordVec = new ArrayList<Double>();

        for (Map<String, Double> textMap : data.values()) {
            wordVec.add(textMap.get(word));
        }

        return wordVec;
    }

    public Map<String ,Map<String, Double>> getOccur(List<Text> texts, Set<String> words) {
        Map<String ,Map<String, Double>> occurList = new HashMap<>();

        for (Text text : texts) {
            Map<String, Double> wordOccurText = new HashMap<>();

            for (String word : words) {
                double count = StringUtils.countMatches(text.getContent().toLowerCase(), word);
                wordOccurText.put(word, count);
            }

            occurList.put(text.getId(),wordOccurText);
        }

        return occurList;
    }

    public Set<String> getAllWordsFromTexts(List<Text> texts) {
        Set<String> words = new HashSet();

        for (Text text : texts) {
            words.addAll(textToWord(text.getContent()));
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
