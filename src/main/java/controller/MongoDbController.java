package controller;

import com.mongodb.*;
import com.mongodb.util.JSON;
import controller.utils.Logger;
import controller.utils.TextType;
import jdk.nashorn.internal.runtime.Debug;
import model.Text;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static net.sf.javaml.utils.MathUtils.eq;

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

    public List<Text> getTextsByType(TextType type) {
        return this.getTexts(type);
    }

    public List<Text> getTextsAll() {
        return this.getTexts(null);
    }

    private List<Text> getTexts(TextType type) {
        DBCollection collection = database.getCollection("texts");
        List<Text> tempTextsList = new ArrayList<Text>();
        tempTextsList.clear();


        DBCursor texts;
        if (type != null) {
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("type", type.toString());
            texts = collection.find(searchQuery);
        } else {
            texts = collection.find();
        }

        while (texts.hasNext()) {
            DBObject item = texts.next();

            Text text = new Text(
                    item.get("id").toString(),
                    (String) item.get("link"),
                    (String) item.get("content"),
                    (String) item.get("attributes"),
                    TextType.valueOf(((String) item.get("type"))));

            tempTextsList.add(text);
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

    public void addText(Text text) {
        Logger.debug(text.toString());

        DBCollection collection = database.getCollection("texts");
        DBObject dbObject = (DBObject) JSON.parse(text.getJsonFormat());
        collection.insert(dbObject);
    }
}
