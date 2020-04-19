package model;

import controller.utils.TextType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Dictionary {
    private final List<String> dictionaryWords;
    private final Map<String, Map<String, Double>> perExOccur;
    private final Map<String, Map<String, Double>> promoOccur;

    public Dictionary(Map<String, Map<String, Double>> perExOccur, Map<String, Map<String, Double>> promoOccur, List<String> dictionaryWords) {
        this.perExOccur = perExOccur;
        this.promoOccur = promoOccur;
        this.dictionaryWords = dictionaryWords;
    }

    public Map<String, Map<String, Double>> getPerExOccur() {
        return perExOccur;
    }

    public Map<String, Map<String, Double>> getPromoOccur() {
        return promoOccur;
    }

    public ArrayList<Double> getOrderTextWordsOccur(Text text) {

        Map<String, Map<String, Double>> occurList = null;

        if (text.getType() == TextType.PersonalExperience)
            occurList = perExOccur;
        else
            occurList = promoOccur;

        if(occurList == null) return null;

        Map<String, Double> textOccur = occurList.get(text.getId());
        ArrayList<Double> listOfOccur = new ArrayList<>();

        for(String word : dictionaryWords){
            listOfOccur.add(textOccur.get(word));
        }

        return listOfOccur;

    }
}
