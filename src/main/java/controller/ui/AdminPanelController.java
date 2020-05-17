package controller.ui;

import controller.MongoDbController;
import controller.ObjectsBuilder;
import controller.Settings;
import controller.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.Text;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.*;

public class AdminPanelController {


    @FXML
    void createTrainingSet(ActionEvent event) {
        createDirectories();
        Dataset data = new DefaultDataset();
        MongoDbController db = MongoDbController.getInstance();
        List<Text> texts = db.getTextsAll();
        TextAdapter textAdapter = TextAdapter.getInstance();
        List<Text> perExTexts = textAdapter.getTextsByType(texts, TextTypeEnum.PersonalExperience);
        List<Text> promoTexts = textAdapter.getTextsByType(texts, TextTypeEnum.Promotion);

        List<Instance> objects = new ObjectsBuilder().build(texts, perExTexts, promoTexts);
        data.addAll(objects);
        Helper.getInstance().writeDataToCsv(data);
    }

    @FXML
    void createTrainingSetSentimentOnly(ActionEvent event) {
    }

    @FXML
    void createTrainingSetDictionaryOnly(ActionEvent event) {

    }

    @FXML
    Dataset loadTrainingSet(ActionEvent event) {
        Dataset data = null;
        try {
            data = Helper.getInstance().loadDataFromCsv();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @FXML
    void deleteTrainingSet(ActionEvent event) {
        (new File(Settings.outputDirName + "/" + Settings.datasetFileName)).delete();
    }

    @FXML
    void loadDataFromSites(ActionEvent event) {
        RowDataHelper.getInstance().writeTextsFromCsv(Settings.csvWebSitesFilePath);
    }

    @FXML
    void deleteTempFiles(ActionEvent event) {
        try {
            FileUtils.deleteDirectory(new File(Settings.dictionaryWordDirName));
            FileUtils.deleteDirectory(new File(Settings.sentimentDirName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createDirectories() {
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
}

