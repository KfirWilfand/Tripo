package model;

import controller.Settings;
import controller.utils.TextType;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import java.util.*;

public class TextObject {
    private List<Double> wordsOccur;
    private Sentiment sentiment;
    private TextType type;

    public TextObject(List<Double> wordsOccur, Sentiment sentiment, TextType type) {
        this.wordsOccur = wordsOccur;
        this.sentiment = sentiment;
        this.type = type;
    }

    public Instance getMLInstance() {
        List<Double> featureList = new ArrayList<>();
        
        featureList.add(sentiment.getNaturalCountSentences()* Settings.naturalCountSentencesWeight);
        featureList.add(sentiment.getNegativeCountSentences()* Settings.negativeCountSentencesWeight);
        featureList.add(sentiment.getPositiveCountSentences()* Settings.positiveCountSentencesWeight);
        featureList.add(sentiment.getVeryNegativeCountSentences()* Settings.veryNegativeCountSentencesWeight);
        featureList.add(sentiment.getVeryPositiveCountSentences()* Settings.veryPositiveCountSentencesWeight);
        featureList.add(sentiment.getNaturalCountWords()* Settings.naturalCountWordsWeight);
        featureList.add(sentiment.getPositiveCountWords()* Settings.positiveCountWordsWeight);
        featureList.add(sentiment.getVeryNegativeCountWords()* Settings.veryNegativeCountWordsWeight);
        featureList.add(sentiment.getNegativeCountWords()* Settings.negativeCountWordsWeight);
        featureList.add(sentiment.getVeryPositiveCountWords()* Settings.veryPositiveCountWordsWeight);

        for(Double occur:wordsOccur){
            featureList.add(occur * Settings.dictionaryWordWeight);
        }

        Instance instance = new DenseInstance(featureList.stream().mapToDouble(d -> d).toArray(), type);

        return instance;
    }
}


