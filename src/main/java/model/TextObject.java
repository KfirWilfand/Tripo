package model;

import net.sf.javaml.core.Instance;

import java.util.Map;

public class TextObject {
    private final Dictionary dictionary;
    private final Map<String, Sentiment> sentiment;
    Map<String, Double> wordList;

    public TextObject(Dictionary dictionary, Map<String, Sentiment> sentiment) {
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

    public Instance getMLInstance(){
        //Nisim implementation
        return null;
    }


}
