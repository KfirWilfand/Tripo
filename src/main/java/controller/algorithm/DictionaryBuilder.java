package controller.algorithm;

import controller.MongoDbController;
import controller.utils.TextAdapter;
import controller.utils.TextType;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.distance.SpearmanRankCorrelation;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DictionaryBuilder {
    private static final double IRRELEVANT_WORD_THRESHOLD = 0.001;
    private static final int K_VALUE_KNN = 3;

    MongoDbController db;
    TextAdapter textAdapter;
    Dataset dictionary;

    public DictionaryBuilder() {
        db = MongoDbController.getInstance();
        textAdapter = TextAdapter.getInstance();
        dictionary = new DefaultDataset();
    }

    public void create() {
        Set<String> unFilteredWordList = textAdapter.getAllWordsFromTexts(db.getTextsAll());
        unFilteredWordList.removeAll(db.getStopWords());

        List<Map<String, Double>> perExOccur = textAdapter.getOccur(db.getTextsByType(TextType.PerEx), unFilteredWordList);
        List<Map<String, Double>> promoOccur = textAdapter.getOccur(db.getTextsByType(TextType.Promo), unFilteredWordList);

        /*
         * The simplest incarnation of the DenseInstance constructor will only
         * take a double array as argument an will create an instance with given
         * values as attributes and no class value set. For unsupervised machine
         * learning techniques this is probably the most convenient constructor.
         */
        Instance perExInstance = null;
        Instance promoInstance = null;

        SpearmanRankCorrelation spearmanRankCorr = new SpearmanRankCorrelation();


        for (String word : unFilteredWordList) {
            List<Double> perExVecWord = textAdapter.getVectorByWord(perExOccur, word);
            List<Double> promoVecWord = textAdapter.getVectorByWord(promoOccur, word);

            perExInstance = new DenseInstance(perExVecWord.stream().mapToDouble(d -> d).toArray(), TextType.PerEx.toString());
            promoInstance = new DenseInstance(promoVecWord.stream().mapToDouble(d -> d).toArray(), TextType.Promo.toString());

            double dest = spearmanRankCorr.measure(perExInstance, promoInstance);

            if (dest > IRRELEVANT_WORD_THRESHOLD) {
                dictionary.add(perExInstance);
                dictionary.add(promoInstance);
            }

        }
    }

    Set<Instance> classfiy(Instance instance) {
        DistanceMeasure ed = new EuclideanDistance();
        return dictionary.kNearest(K_VALUE_KNN, instance, ed);
    }


    public Dataset getDictionary() {
        return dictionary;
    }
}
