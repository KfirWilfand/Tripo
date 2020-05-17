package model;

import controller.Settings;
import controller.utils.TextTypeEnum;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

import java.util.*;

public class TextObject {
    private List<Integer> wordsOccur;
    private Sentiment sentiment;
    private TextTypeEnum type;

    public TextObject(List<Integer> wordsOccur, Sentiment sentiment, TextTypeEnum type) {
        this.wordsOccur = wordsOccur;
        this.sentiment = sentiment;
        this.type = type;
    }

    public TextObject(List<Integer> wordsOccur, Sentiment sentiment) {
        this.wordsOccur = wordsOccur;
        this.sentiment = sentiment;
    }


    public Instance getMLInstance() {
        List<Double> featureList = new ArrayList<>();
        int numOfSentences = sentiment.getNumOfSentences();
        int numOfWords = sentiment.getNumOfWords();
        if (sentiment != null) {
            featureList.add((sentiment.getNaturalCountSentences() / numOfSentences) * Settings.naturalCountSentencesWeight);
            featureList.add((sentiment.getNegativeCountSentences() / numOfSentences) * Settings.negativeCountSentencesWeight);
            featureList.add((sentiment.getPositiveCountSentences() / numOfSentences) * Settings.positiveCountSentencesWeight);
            featureList.add((sentiment.getVeryNegativeCountSentences() / numOfSentences) * Settings.veryNegativeCountSentencesWeight);
            featureList.add((sentiment.getVeryPositiveCountSentences() / numOfSentences) * Settings.veryPositiveCountSentencesWeight);
            featureList.add((sentiment.getNaturalCountWords() / numOfWords) * Settings.naturalCountWordsWeight);
            featureList.add((sentiment.getPositiveCountWords() / numOfWords) * Settings.positiveCountWordsWeight);
            featureList.add((sentiment.getVeryNegativeCountWords() / numOfWords) * Settings.veryNegativeCountWordsWeight);
            featureList.add((sentiment.getNegativeCountWords() / numOfWords) * Settings.negativeCountWordsWeight);
            featureList.add((sentiment.getVeryPositiveCountWords() / numOfWords) * Settings.veryPositiveCountWordsWeight);
        }
        if (wordsOccur != null) {
            for (Integer occur : wordsOccur) {
                featureList.add((occur / numOfWords) * Settings.dictionaryWordWeight);
            }
        }
        Instance instance;

        if (type != null) {
            instance = new DenseInstance(featureList.stream().mapToDouble(d -> d).toArray(), type);
        } else {
            int numOfAttr = featureList.size();
            instance = new DenseInstance(numOfAttr);

            for (int i = 0; i < numOfAttr; i++) {
                instance.put(i, featureList.get(i));
            }
        }

        return instance;
    }
}


