package controller.algorithm;

import controller.utils.TextAdapter;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import model.Sentiment;
import model.Text;

import java.util.*;

public class SentimentBuilder {
    private final TextAdapter textAdapter;

    public SentimentBuilder() {
        textAdapter = TextAdapter.getInstance();
    }

    public Map<String, Sentiment> create(List<Text> texts) {
        Map<String, Sentiment> textSentiments = new HashMap<>();

        for (Text text : texts) {
//            new Thread(() -> {
                Sentiment sentiment = new Sentiment();
                Set<String> words = textAdapter.textToWord(text.getContent());
                Set<String> sentences = textAdapter.textToWordSentences(text.getContent());

                for (String word : words) {

                    int sentimentVal = this.findSentiment(word);
                    switch (sentimentVal) {
                        case -2:
                            sentiment.incVeryNegativeCountWords();
                            break;
                        case -1:
                            sentiment.incNegativeCountWords();
                            break;
                        case 0:
                            sentiment.incNaturalCountWords();
                            break;
                        case 1:
                            sentiment.incPositiveCountWords();
                            break;
                        case 2:
                            sentiment.incVeryPositiveCountWords();
                            break;
                    }
                }

                for (String sentence : sentences) {

                    int sentimentVal = this.findSentiment(sentence);
                    switch (sentimentVal) {
                        case -2:
                            sentiment.incVeryNegativeCountSentences();
                            break;
                        case -1:
                            sentiment.incNegativeCountSentences();
                            break;
                        case 0:
                            sentiment.incNaturalCountSentences();
                            break;
                        case 1:
                            sentiment.incPositiveCountSentences();
                            break;
                        case 2:
                            sentiment.incVeryPositiveCountSentences();
                            break;
                    }
                }

                textSentiments.put(text.getId(), sentiment);
//            }).start();
        }

        return textSentiments;
    }

    public int findSentiment(String line) {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        int mainSentiment = 0;
        if (line != null && line.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(line);

            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }


            }
        }
        return mainSentiment;
    }
}
