package controller;

import controller.algorithm.DictionaryBuilder;
import controller.algorithm.SentimentBuilder;
import controller.utils.TextType;
import model.Dictionary;
import model.Sentiment;
import model.Text;
import model.TextObject;

import java.util.List;
import java.util.Map;

public class ObjectBuilder {
    private final MongoDbController db;
    private final List<Text> texts;

    public ObjectBuilder() {
        db = MongoDbController.getInstance();
        texts = db.getTextsAll();
    }

    public TextObject build() {
//        Dictionary dictionary = new DictionaryBuilder().create(texts);
        Map<String, Sentiment> sentiment = new SentimentBuilder().create(texts);
        System.out.println(sentiment);
        return null;
//        return new TextObject(new Dictionary(), sentiment);
    }


}
