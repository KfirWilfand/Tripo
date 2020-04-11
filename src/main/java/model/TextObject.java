package model;

import controller.Settings;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

import java.util.*;

public class TextObject {
    private Map<String, Double> dictionary;
    private Sentiment sentiment;
    private List<String> wordList;
    private Object TextType;

    public TextObject(Map<String, Double> dictionary, Sentiment sentiment) {
        this.dictionary = dictionary;
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return "TextObject{" +
                "dictionary= " + dictionary +
                ", sentiment= " + sentiment +
                ", wordList= " + wordList +
                '}';
    }

    public Instance getMLInstance() {
        //Nisim implementation

        List<Double> InstanceList = new ArrayList<>();
        double[] arr = new double[InstanceList.size()];
        double[] dictionaryarr = new double[InstanceList.size()];
        InstanceList.add((double) (sentiment.getNaturalCountSentences()* Settings.naturalCountSentencesWeight));
        InstanceList.add((double) (sentiment.getNegativeCountSentences()* Settings.negativeCountSentencesWeight));
        InstanceList.add((double) (sentiment.getPositiveCountSentences()* Settings.positiveCountSentencesWeight));
        InstanceList.add((double) (sentiment.getVeryNegativeCountSentences()* Settings.veryNegativeCountSentencesWeight));
        InstanceList.add((double) (sentiment.getVeryPositiveCountSentences()* Settings.veryPositiveCountSentencesWeight));
        InstanceList.add((double) (sentiment.getNaturalCountWords()* Settings.naturalCountWordsWeight));
        InstanceList.add((double) (sentiment.getPositiveCountWords()* Settings.positiveCountWordsWeight));
        InstanceList.add((double) (sentiment.getVeryNegativeCountWords()* Settings.veryNegativeCountWordsWeight));
        InstanceList.add((double) (sentiment.getNegativeCountWords()* Settings.negativeCountWordsWeight));
        InstanceList.add((double) (sentiment.getVeryPositiveCountWords()* Settings.veryPositiveCountWordsWeight));

        Set set = dictionary.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            InstanceList.add((double) mentry.getValue()*Settings.dictionaryObjWeight);
        }

        for (int i = 0; i < InstanceList.size(); i++)
            arr[i] = InstanceList.get(i);
        Instance perExInstance = new DenseInstance(arr, TextType);

        return null;
    }
}


