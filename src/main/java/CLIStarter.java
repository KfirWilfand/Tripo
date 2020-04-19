import controller.ObjectsBuilder;
import controller.Settings;
import controller.utils.Helper;
import controller.utils.RowDataHelper;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.tools.data.FileHandler;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class CLIStarter {
    public static void main(String[] args) {
//        loadDataFromSites();
        createTrainingSet();
        Dataset data = loadTrainingSet();
        System.out.println(data);
    }

    private static void loadDataFromSites(){
        RowDataHelper.getInstance().writeTextsFromCsv(Settings.csvWebSitesFilePath);
    }

    private static void createTrainingSet(){
        Dataset data = new DefaultDataset();

        List<Instance> objects = new ObjectsBuilder().build();
        data.addAll(objects);
        Helper.getInstance().writeDataToCsv(data);
    }

    private static Dataset loadTrainingSet(){
        Dataset data = null;
        try {
            data = Helper.getInstance().loadDataFromCsv();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
