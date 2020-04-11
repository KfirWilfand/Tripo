import controller.ObjectBuilder;
import controller.Settings;
import controller.utils.TextType;
import controller.utils.RowDataHelper;

import java.util.List;

public class CLIStarter {
    public static void main(String[] args) {
//        DictionaryBuilder dicBuilder = new DictionaryBuilder();
//        dicBuilder.create();
//        System.out.println(dicBuilder.getDictionary());

//        RowDataHelper.getInstance().writeTextsFromCsv(Settings.csvWebSitesFilePath);

        Object test = new ObjectBuilder().build();
//        System.out.println(test);
    }

}
