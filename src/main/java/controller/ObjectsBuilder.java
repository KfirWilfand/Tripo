package controller;

import controller.algorithm.DictionaryBuilder;
import controller.algorithm.SentimentBuilder;
import controller.utils.Logger;
import model.Dictionary;
import model.Sentiment;
import model.Text;
import model.TextObject;
import net.sf.javaml.core.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectsBuilder {

    public ObjectsBuilder() {

    }

    public List<Instance> build(List<Text> texts, List<Text> perExTexts, List<Text> promoTexts) {
        Logger.info("ObjectsBuilder process has been started!");

        DictionaryBuilder dictionaryBuilder = new DictionaryBuilder();
        dictionaryBuilder.init(texts, perExTexts, promoTexts);
        Dictionary dictionary = dictionaryBuilder.getDictionary();

        Map<String, Sentiment> sentiments = new SentimentBuilder().create(texts);

        List<Instance> objects = createInstances(texts, dictionary, sentiments);

        return objects;
    }

    public List<Instance> createInstances(List<Text> texts, Dictionary dictionary, Map<String, Sentiment> sentiments) {
        List<Instance> objects = new ArrayList<Instance>();

        for (Text text : texts) {
            ArrayList<Integer> wordOccurLst = dictionary.getOrderTextWordsOccur(text);
            Sentiment textSentiment = sentiments.get(text.getId());

            TextObject textObject = new TextObject(wordOccurLst, textSentiment, text.getType());

            objects.add(textObject.getMLInstance());
        }

        return objects;
    }
}
