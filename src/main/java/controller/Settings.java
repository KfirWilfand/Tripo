package controller;

public class Settings {
    //###### File Paths

    //# CSV files
    public static final String csvWebSitesFilePath = "./csv/texts_urls_by_type.csv";
    //# JS files
    public static final String jsInjectionCaptureByUserFilePath = "./csv/webJsInjection.csv";
    public static final String jsInjectionAutoCaptureFilePath = "./csv/texts_urls_by_type.csv";
    public static int avgSpearmanRankCorrelationThreshold = 20;

    public static final String siteFailLoadLogFilePath = "output/log/site_fail_load.log";
}
