import controller.algorithm.DictionaryBuilder;

public class CLIStarter {
    public static void main(String[] args) {
        DictionaryBuilder dicBuilder = new DictionaryBuilder();
        dicBuilder.create();

        System.out.println(dicBuilder.getDictionary());
    }
}
