package controller;

import java.io.File;

public class Settings {
    //# CSV files
    public static final String csvWebSitesFilePath = "./csv/texts_urls_by_type.csv";
    public static final String dictionarySpearmanDistanceFileName = "dictionarySpearmanDistance.csv";
    public static final String sentimentFileName = "sentiment.csv";

    //# JS files
    public static final String jsInjectionCaptureByUserFilePath = "./csv/webJsInjection.csv";
    public static final String jsInjectionAutoCaptureFilePath = "./csv/texts_urls_by_type.csv";

    //# Logs
    public static final String siteFailLoadLogFileName = "site_fail_load.log";

    //# Dataset
    public static final String datasetFileName = "dataset.csv";

    //# Algorithms
    public static double avgSpearmanRankCorrelationThreshold = 20;
    public static int knnKvalue = 3;

    //# Text Object Weight
    public static double dictionaryObjWeight = 1.0;
    public static double veryNegativeCountWordsWeight = 1.0;
    public static double negativeCountWordsWeight = 1.0;
    public static double naturalCountWordsWeight = 1.0;
    public static double positiveCountWordsWeight = 1.0;
    public static double veryPositiveCountWordsWeight = 1.0;
    public static double veryNegativeCountSentencesWeight = 1.0;
    public static double negativeCountSentencesWeight = 1.0;
    public static double naturalCountSentencesWeight = 1.0;
    public static double positiveCountSentencesWeight = 1.0;
    public static double veryPositiveCountSentencesWeight = 1.0;

    public static double dictionaryWordWeight = 1.0;

    //#Directories
    public static String sentimentPath = "./output/sentiment/";
    public static String outputDirName = "output";
    public static String sentimentDirName = outputDirName + "/sentiment";
    public static String dictionaryDirName = outputDirName + "/dictionary";
    public static String dictionaryWordDirName = dictionaryDirName + "/words";;
    public static int sizeOfLineInTextArea = 135;
    public static String dictionaryWordsFileName = "dictionaryWords.csv";
}
