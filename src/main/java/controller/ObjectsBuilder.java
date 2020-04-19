package controller;

import controller.algorithm.DictionaryBuilder;
import controller.algorithm.SentimentBuilder;
import controller.utils.TextAdapter;
import controller.utils.TextType;
import model.Dictionary;
import model.Sentiment;
import model.Text;
import model.TextObject;
import net.sf.javaml.core.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectsBuilder {
    private final MongoDbController db;
    private final TextAdapter textAdapter;
    private final List<Text> texts;
    private List<Text> perExTexts;
    private List<Text> promoTexts;

    public ObjectsBuilder() {
        db = MongoDbController.getInstance();
        texts = db.getTextsAll();
        textAdapter = TextAdapter.getInstance();
        perExTexts = textAdapter.getTextsByType(texts, TextType.PersonalExperience);
        promoTexts = textAdapter.getTextsByType(texts, TextType.Promotion);
    }

    public List<Instance> build() {

        DictionaryBuilder dictionaryBuilder = new DictionaryBuilder();
        dictionaryBuilder.init(texts, perExTexts, promoTexts);
        Dictionary dictionary = dictionaryBuilder.getDictionary();

        Map<String, Sentiment> sentiments = new SentimentBuilder().create(texts);
        List<Instance> objects = new ArrayList<Instance>();

        for (Text text : texts) {
            ArrayList<Double> wordOccurLst = dictionary.getOrderTextWordsOccur(text);
            Sentiment textSentiment = sentiments.get(text.getId());

            TextObject textObject = new TextObject(wordOccurLst, textSentiment, text.getType());

            objects.add(textObject.getMLInstance());
        }

        return objects;
    }
}
