package controller.ui;

import controller.Settings;
import controller.algorithm.SentimentBuilder;
import controller.utils.ClassificationProcessEnum;
import controller.utils.Helper;
import controller.utils.Logger;
import controller.utils.TextAdapter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import model.Sentiment;
import model.Text;
import model.TextObject;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.normalize.InstanceNormalizeMidrange;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectClassificationProcess {

    @FXML
    private Pane mainPane;

    @FXML
    private Button btnCancel;

    @FXML
    private ProgressBar progBar;

    @FXML
    private Line line1;

    @FXML
    private RadioButton rbTextCuptured;

    @FXML
    private RadioButton rbLoadDicWords;

    @FXML
    private RadioButton rbCalcWordsOccur;

    @FXML
    private RadioButton rbCreateDictionary;

    @FXML
    private RadioButton rbAnalizeSentiment;

    @FXML
    private RadioButton rbCreatingObject;

    @FXML
    private RadioButton rbNormailzeData;

    @FXML
    private RadioButton rbKnnClassification;

    @FXML
    private Line line2;

    @FXML
    private Line line3;

    @FXML
    private Line line7;

    @FXML
    private Line line6;

    @FXML
    private Line line5;

    @FXML
    private Line line4;

    @FXML
    private ProgressIndicator pbi1;

    @FXML
    private ProgressIndicator pbi2;

    @FXML
    private ProgressIndicator pbi3;

    @FXML
    private ProgressIndicator pbi4;

    @FXML
    private ProgressIndicator pbi5;

    @FXML
    private ProgressIndicator pbi6;

    @FXML
    private ProgressIndicator pbi7;

    @FXML
    private ProgressIndicator pbi8;
    private BlinkRunnable blinkRunnable;
    private Thread thread;

    @FXML
    void onCancelBtnClick(ActionEvent event) {
        try {
            thread.interrupt();
        } catch (Exception e) {
            Logger.warning("Thread stop by the user!");
        }

        Helper.getInstance().layoutSwitcher(mainPane, "pick_html_element.fxml");
    }

    public void start(Text text) {
        thread = classificationProcessThread(text);
        thread.start();
    }

    public Thread classificationProcessThread(Text text) {
        Runnable runnable = () -> {

            updateUi(ClassificationProcessEnum.TextCuptured);

            updateUi(ClassificationProcessEnum.LoadDicWords);
            List<String> dictionaryWords = Helper.getInstance().loadDictionaryWordsCsv();

            TextAdapter textAdapter = TextAdapter.getInstance();

            updateUi(ClassificationProcessEnum.CalcWordsOccur);
            Map<String, Integer> textOccurMap = textAdapter.calcOccur(text, dictionaryWords);

            updateUi(ClassificationProcessEnum.CreateDictionary);
            List<Integer> textOccursLst = new ArrayList<>();
            for (String word : dictionaryWords) {
                textOccursLst.add(textOccurMap.get(word));
            }

            updateUi(ClassificationProcessEnum.AnalizeSentiment);
            SentimentBuilder sentimentBuilder = new SentimentBuilder();
            Sentiment textSentiment = sentimentBuilder.getTextSentiment(text);

            TextObject TextObject = new TextObject(textOccursLst, textSentiment);

            DefaultDataset data = null;
            try {
                data = (DefaultDataset) FileHandler.loadDataset(new File(Settings.outputDirName + "/" + Settings.datasetFileName), 0);
            } catch (IOException e) {
                e.printStackTrace();
            }

            KNearestNeighbors knn = new KNearestNeighbors(Settings.knnKvalue);
            knn.buildClassifier(data);

            updateUi(ClassificationProcessEnum.CreatingObject);
            Instance instance = TextObject.getMLInstance();

            updateUi(ClassificationProcessEnum.NormailzeData);
            InstanceNormalizeMidrange instanceNormalizeMidrange = new InstanceNormalizeMidrange();
            instanceNormalizeMidrange.filter(instance);
            instanceNormalizeMidrange.filter(data);

            updateUi(ClassificationProcessEnum.KnnClassification);
            Object predictedClassValue = knn.classify(instance);

            updateUi(ClassificationProcessEnum.Finish);
            System.out.println(predictedClassValue);


        };
        return new Thread(runnable);
    }

    public void updateUi(ClassificationProcessEnum state) {
        switch (state) {
            case TextCuptured:
                progBar.setProgress(0.11);
                progBar.getParent();
                pbi1.setProgress(100);
            case LoadDicWords:
                rbLoadDicWords.setDisable(false);
                rbLoadDicWords.setSelected(true);
                line1.setVisible(true);
                progBar.setProgress(0.22);
                pbi1.setProgress(100);
                pbi2.setVisible(true);
                break;
            case CalcWordsOccur:
                rbCalcWordsOccur.setDisable(false);
                rbCalcWordsOccur.setSelected(true);
                line2.setVisible(true);
                progBar.setProgress(0.33);
                pbi2.setProgress(100);
                pbi3.setVisible(true);
                break;
            case CreateDictionary:
                rbCreateDictionary.setDisable(false);
                rbCreateDictionary.setSelected(true);
                line3.setVisible(true);
                progBar.setProgress(0.44);
                pbi3.setProgress(100);
                pbi4.setVisible(true);
                break;
            case AnalizeSentiment:
                rbAnalizeSentiment.setDisable(false);
                rbAnalizeSentiment.setSelected(true);
                line4.setVisible(true);
                progBar.setProgress(0.55);
                pbi4.setProgress(100);
                blinkRunnable = new BlinkRunnable(rbAnalizeSentiment);
                blinkRunnable.run();
                pbi5.setVisible(true);
                break;
            case CreatingObject:
                blinkRunnable.shutdown();
                rbCreatingObject.setDisable(false);
                rbCreatingObject.setSelected(true);
                line5.setVisible(true);
                progBar.setProgress(0.66);
                pbi5.setProgress(100);
                pbi6.setVisible(true);
                break;
            case NormailzeData:
                rbNormailzeData.setDisable(false);
                rbNormailzeData.setSelected(true);
                line6.setVisible(true);
                progBar.setProgress(0.77);
                pbi6.setProgress(100);
                pbi7.setVisible(true);
                break;
            case KnnClassification:
                line7.setVisible(true);
                rbKnnClassification.setDisable(false);
                rbKnnClassification.setSelected(true);
                progBar.setProgress(0.88);
                pbi7.setProgress(100);
                pbi8.setVisible(true);
                break;
            case Finish:
                progBar.setProgress(1);
                break;
        }
    }


    public class BlinkRunnable implements Runnable {
        private volatile boolean shutdown = false;
        RadioButton rb;

        BlinkRunnable(RadioButton rb) {
            this.rb = rb;
        }

        public void run() {
            while (!shutdown) {
                try {
                    rb.setSelected(!rb.isSelected());
                    Thread.sleep(650);
                } catch (InterruptedException e) {
                    Logger.warning("Thread interrupted by the user!");
                }
            }
        }

        public void shutdown() {
            shutdown = true;
        }
    }
}
