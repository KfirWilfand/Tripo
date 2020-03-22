import controller.algorithm.DictionaryBuilder;
import controller.algorithm.SentimentAnalyzer;

public class CLIStarter {
    public static void main(String[] args) {
//        DictionaryBuilder dicBuilder = new DictionaryBuilder();
//        dicBuilder.create();
//        System.out.println(dicBuilder.getDictionary());

        System.out.println(new SentimentAnalyzer().findSentiment("From Rungsted we headed south through Denmark to catch the ferry to Germany (Puttsgarden) and then drove to Bremen for the night."));
    }
}
