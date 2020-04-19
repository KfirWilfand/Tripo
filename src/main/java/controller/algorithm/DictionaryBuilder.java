package controller.algorithm;

import controller.MongoDbController;
import controller.Settings;
import controller.utils.TextAdapter;
import controller.utils.TextType;
import model.Text;
import model.Dictionary;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.SpearmanRankCorrelation;
import org.apache.commons.collections.ListUtils;


import java.util.*;

public class DictionaryBuilder {
    private TextAdapter textAdapter;
    private Dictionary dictionary;

    public DictionaryBuilder() {
        textAdapter = TextAdapter.getInstance();
    }

    public void init(List<Text> texts, List<Text> perExTexts, List<Text> promoTexts) {
        List<String> dictionaryWords = new ArrayList<>(textAdapter.getAllWordsFromTexts(texts));
//        dictionaryWords.removeAll(db.getStopWords());
//        Map<String, Map<String, Double>> occurMap = textAdapter.getOccur(texts, dictionaryWords);

        Map<String, Map<String, Double>> perExOccur = textAdapter.getOccur(perExTexts, dictionaryWords);
        Map<String, Map<String, Double>> promoOccur = textAdapter.getOccur(promoTexts, dictionaryWords);

        /*
         * The simplest incarnation of the DenseInstance constructor will only
         * take a double array as argument an will create an instance with given
         * values as attributes and no class value set. For unsupervised machine
         * learning techniques this is probably the most convenient constructor.
         */
        SpearmanRankCorrelation spearmanRankCorr = new SpearmanRankCorrelation();

        Map<String, Double> destMap = new HashMap<>();
        for (String word : dictionaryWords) {

            List<Double> perExVecWord = textAdapter.getVectorByWord(perExOccur, word);
            Instance perExInstance = new DenseInstance(perExVecWord.stream().mapToDouble(d -> d).toArray(), TextType.PersonalExperience.toString());

            List<Double> promoVecWord = textAdapter.getVectorByWord(promoOccur, word);
            Instance promoInstance = new DenseInstance(promoVecWord.stream().mapToDouble(d -> d).toArray(), TextType.Promotion.toString());

            double dest = spearmanRankCorr.measure(perExInstance, promoInstance);
            destMap.put(word, dest);
        }

        List<Double> destList = new ArrayList<>(destMap.values());
        Collections.sort(destList);

        double sum = destList.stream().mapToDouble(Double::doubleValue).sum();
        double destThreshold = sum * (Settings.avgSpearmanRankCorrelationThreshold / 100);

        for (String key : destMap.keySet()) {
            if (destMap.get(key) < destThreshold) {
                dictionaryWords.remove(key);
                perExOccur.remove(key);
                promoOccur.remove(key);
            }
        }

        this.dictionary = new Dictionary(perExOccur,promoOccur,dictionaryWords);
    }

    public Dictionary getDictionary() {
        return dictionary;
    }
}
