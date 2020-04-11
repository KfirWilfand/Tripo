package model;

import net.sf.javaml.core.Instance;

import java.util.List;
import java.util.Map;

public class TextObject {
    private Map<String, Double>  dictionary;
    private Sentiment sentiment;
    private List<String> wordList;

    public TextObject(Map<String, Double>  dictionary, Sentiment sentiment) {
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



     