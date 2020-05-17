package model;

import controller.utils.TextTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Dictionary {
    private final List<String> dictionaryWords;
    private final Map<String, Map<String, Integer>> perExOccur;
    private final Map<String, Map<String, Integer>> promoOccur;

    public Dictionary(Map<String, Map<String, Integer>> perExOccur, Map<String, Map<String, Integer>> promoOccur, List<String> dictionaryWords) {
        this.perExOccur = perExOccur;
        this.promoOccur = promoOccur;
        this.dictionaryWords = dictionaryWords;
    }

    public Map<String, Map<String, Integer>> getPerExOccur() {
        return perExOccur;
    }

    public Map<String, Map<String, Integer>> getPromoOccur() {
        return promoOccur;
    }

    public ArrayList<Integer> getOrderTextWordsOccur(Text text) {

        Map<String, Map<String, Integer>> occurList = null;

        if (text.getType() == TextTypeEnum.PersonalExperience)
            occurList = perExOccur;
        else
            occurList = promoOccur;

        if(occurList == null) return null;

        Map<String, Integer> textOccur = occurList.get(text.getId());
        ArrayList<Integer> listOfOccur = new ArrayList<>();

        for(String word : dictionaryWords){
            listOfOccur.add(textOccur.get(word));
        }

        return listOfOccur;

    }
}
