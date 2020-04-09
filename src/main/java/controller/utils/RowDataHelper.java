package controller.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import controller.MongoDbController;
import model.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.riversun.promise.Func;
import org.riversun.promise.Promise;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RowDataHelper {
    // static variable single_instance of type Singleton
    private static RowDataHelper single_instance = null;

    // static method to create instance of Singleton class
    public static RowDataHelper getInstance() {
        if (single_instance == null)
            single_instance = new RowDataHelper();

        return single_instance;
    }

    public void writeTextsFromCsv(String csvWebSitesFilePath) {
        List<String> linksListPerEx = RowDataHelper.getInstance().readSitesListByType(TextType.PerEx, csvWebSitesFilePath);
        List<String> linksListPromo = RowDataHelper.getInstance().readSitesListByType(TextType.Promo, csvWebSitesFilePath);

        for (String link : linksListPromo) {
            RowDataHelper.getInstance().writeTextFromSitesToDb(link, TextType.PerEx);
        }

        for (String link : linksListPerEx) {
            RowDataHelper.getInstance().writeTextFromSitesToDb(link, TextType.PerEx);
        }
    }

    private List<String> readSitesListByType(TextType type, String csvWebSitesFilePath) {
        List<String> linksList = new ArrayList<String>();
        URL csvFile = getClass().getClassLoader().getResource(csvWebSitesFilePath);

        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(csvFile.getPath())));

            String[] record;
            int colNumByType = -1;

            if ((record = csvReader.readNext()) != null) {
                if (record[0].equals(type.toString())) {
                    colNumByType = 0;
                } else if (record[1] == type.toString()) {
                    colNumByType = 1;
                }
            }

            if (colNumByType == -1)
                throw new CsvValidationException("CSV File format is wrong! Can't read the columns types");

            while ((record = csvReader.readNext()) != null) {
                linksList.add(record[colNumByType]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return linksList;
    }

    private void writeTextFromSitesToDb(String link, TextType type) {

        Func getSiteContent = (action, data) -> {
            new Thread(() -> {
                try {
                    Document doc = Jsoup.connect(link).get();
                    String title = doc.title();
                    List<Element> elements = doc.body().select(":not(body,html,title,meta,link,img,script,input,form,a,button)");

                    Element maxElement = new Element("div");

                    for (Element element : elements) {
                        if (maxElement.ownText().length() < element.ownText().length()) {
                            maxElement = element;
                        }
                    }

                    String attributeContent = "";
                    for (Attribute attribute : maxElement.attributes().asList()) {
                        attributeContent += (attribute.getValue() + " ");
                    }


                    action.resolve(new Text(link, maxElement.text(), attributeContent, type));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        };

        Func writeToDb = (action, data) -> {
            MongoDbController.getInstance().addText((Text) data);
            action.resolve();
        };

        Promise.resolve()
                .then(new Promise(getSiteContent))
                .then(new Promise(writeToDb))
                .start();
    }
}
