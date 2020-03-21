package controller;

import com.mongodb.*;
import controller.utils.TextType;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MongoDbController {
    // static variable single_instance of type Singleton
    private static MongoDbController single_instance = null;

    private final String mongoServerURI = "mongodb://localhost:27017";
    private final String databaseName = "tripo";
    private DB database;

    // static method to create instance of Singleton class
    public static MongoDbController getInstance() {
        if (single_instance == null)
            single_instance = new MongoDbController();

        return single_instance;
    }

    // private constructor restricted to this class itself
    private MongoDbController() {
        // Creating a Mongo client
        try {
            MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoServerURI));
            database = mongoClient.getDB(databaseName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTextsByType(TextType type) {
        return this.getTexts(type);
    }

    public List<String> getTextsAll() {
        return this.getTexts(null);
    }

    private List<String> getTexts(TextType type) {
        DBCollection collection = database.getCollection("texts");
        List<String> tempTextsList = new ArrayList<>();


        DBCursor texts;
        if (type != null) {
            texts = collection.find((DBObject) new BasicDBObject().put("type", type));
        } else {
            texts = collection.find();
        }

        while (texts.hasNext()) {
            tempTextsList.add((String) texts.next().get("content"));
        }

        return tempTextsList;
    }

    public List<String> getStopWords() {
        DBCollection collection = database.getCollection("stop_words");
        List<String> stopWordsList = new ArrayList<>();

        DBCursor sets = collection.find();

        while (sets.hasNext()) {
            stopWordsList.addAll((List<String>) sets.next().get("set1"));
        }

        return stopWordsList;
    }

}
