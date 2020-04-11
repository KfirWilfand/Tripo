package controller;

public class Settings {
    //# CSV files
    public static final String csvWebSitesFilePath = "./csv/texts_urls_by_type.csv";

    //# JS files
    public static final String jsInjectionCaptureByUserFilePath = "./csv/webJsInjection.csv";
    public static final String jsInjectionAutoCaptureFilePath = "./csv/texts_urls_by_type.csv";

    //# Logs
    public static final String siteFailLoadLogFileName = "site_fail_load.log";

    //# Algorithms
    public static int avgSpearmanRankCorrelationThreshold = 20;

    //# Text Object Weight
    public static int dictionaryObjWeight = 1;
    public static int veryNegativeCountWordsWeight = 1;
    public static int negativeCountWordsWeight = 1;
    public static int naturalCountWordsWeight = 1;
    public static int positiveCountWordsWeight = 1;
    public static int veryPositiveCountWordsWeight = 1;
    public static int veryNegativeCountSentencesWeight = 1;
    public static int negativeCountSentencesWeight = 1;
    public static int naturalCountSentencesWeight = 1;
    public static int positiveCountSentencesWeight = 1;
    public static int veryPositiveCountSentencesWeight = 1;
}
