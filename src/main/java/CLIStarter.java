import controller.MongoDbController;
import controller.ObjectsBuilder;
import controller.Settings;
import controller.utils.*;
import model.Text;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CLIStarter {
    public static void main(String[] args) {

//        createTrainingSet();
//        MongoDbController db = MongoDbController.getInstance();
//        List<Text> texts = db.getTextsAll();
//        TextAdapter textAdapter = TextAdapter.getInstance();
//        List<Text> perExTexts = textAdapter.getTextsByType(texts, TextType.PersonalExperience);
//        List<Text> promoTexts = textAdapter.getTextsByType(texts, TextType.Promotion);
//
//        DictionaryBuilder dictionaryBuilder = new DictionaryBuilder();
//        dictionaryBuilder.init(texts, perExTexts, promoTexts);
//        Dictionary dictionary = dictionaryBuilder.getDictionary();
        List<String> a = Helper.getInstance().loadDictionaryWordsCsv();
System.out.println(a);
    }

    private static void createDirectories() {
        List<File> dirs = new ArrayList<>();

        dirs.add(new File(Settings.outputDirName));
        dirs.add(new File(Settings.sentimentDirName));
        dirs.add(new File(Settings.dictionaryDirName));
        dirs.add(new File(Settings.dictionaryWordDirName));

        for (File file : dirs) {
            if (!file.isDirectory()) {
                Logger.debug("Directory created: " + file.getPath());
                file.mkdirs();
            }
        }

    }

    private static void loadDataFromSites() {
        RowDataHelper.getInstance().writeTextsFromCsv(Settings.csvWebSitesFilePath);
    }

    private static void createTrainingSet() {
        Dataset data = new DefaultDataset();
        MongoDbController db = MongoDbController.getInstance();
        List<Text> texts = db.getTextsAll();
        TextAdapter textAdapter = TextAdapter.getInstance();
        List<Text> perExTexts = textAdapter.getTextsByType(texts, TextTypeEnum.PersonalExperience);
        List<Text> promoTexts = textAdapter.getTextsByType(texts, TextTypeEnum.Promotion);

        List<Instance> objects = new ObjectsBuilder().build(texts,perExTexts,promoTexts);
        data.addAll(objects);
        Helper.getInstance().writeDataToCsv(data);
    }

    private static Dataset loadTrainingSet() {
        Dataset data = null;
        try {
            data = Helper.getInstance().loadDataFromCsv();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
