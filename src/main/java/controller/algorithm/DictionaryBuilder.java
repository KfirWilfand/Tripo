package controller.algorithm;

import controller.MongoDbController;
import controller.Settings;
import controller.utils.TextAdapter;
import controller.utils.TextType;
import model.Text;
import model.Dictionary;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.distance.SpearmanRankCorrelation;



import java.util.*;

public class DictionaryBuilder {
    MongoDbController db;
    TextAdapter textAdapter;

    public DictionaryBuilder() {
        db = MongoDbController.getInstance();
        textAdapter = TextAdapter.getInstance();
    }

    public Dictionary create() {
        Set<String> unFilteredWordList = textAdapter.getAllWordsFromTexts(db.getTextsAll());
//        unFilteredWordList.removeAll(db.getStopWords());

        List<Text> perExTexts = db.getTextsByType(TextType.PerEx);
        List<Text> promoTexts = db.getTextsByType(TextType.Promo);

        Map<String, Map<String, Double>> perExOccur = textAdapter.getOccur(perExTexts, unFilteredWordList);
        Map<String, Map<String, Double>> promoOccur = textAdapter.getOccur(promoTexts, unFilteredWordList);

        /*
         * The simplest incarnation of the DenseInstance constructor will only
         * take a double array as argument an will create an instance with given
         * values as attributes and no class value set. For unsupervised machine
         * learning techniques this is probably the most convenient constructor.
         */
        SpearmanRankCorrelation spearmanRankCorr = new SpearmanRankCorrelation();

        Map<String, Double> destMap = new HashMap<>();
        for (String word : unFilteredWordList) {

            List<Double> perExVecWord = textAdapter.getVectorByWord(perExOccur, word);
            Instance perExInstance = new DenseInstance(perExVecWord.stream().mapToDouble(d -> d).toArray(), TextType.PerEx.toString());

            List<Double> promoVecWord = textAdapter.getVectorByWord(promoOccur, word);
            Instance promoInstance = new DenseInstance(promoVecWord.stream().mapToDouble(d -> d).toArray(), TextType.Promo.toString());

            double dest = spearmanRankCorr.measure(perExInstance, promoInstance);
            destMap.put(word, dest);
        }

        List<Double> destList = new ArrayList<>(destMap.values());
        Collections.sort(destList);

        double sum = destList.stream().mapToDouble(Double::doubleValue).sum();
        double destThreshold = sum * (Settings.avgSpearmanRankCorrelationThreshold / 100);

        for (String key : destMap.keySet()) {
            if (destMap.get(key) < destThreshold) {
                perExOccur.remove(key);
                promoOccur.remove(key);
            }
        }

        return new Dictionary(perExOccur,promoOccur);
    }
}
