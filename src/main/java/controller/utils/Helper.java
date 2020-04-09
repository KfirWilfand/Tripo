package controller.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Stream;

public class Helper {
    // static variable single_instance of type Singleton
    private static Helper single_instance = null;

    // static method to create instance of Singleton class
    public static Helper getInstance() {
        if (single_instance == null)
            single_instance = new Helper();

        return single_instance;
    }

    // private constructor restricted to this class itself
    private Helper() {

    }

    public String fileToString(URL filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath.getPath()), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    public FXMLLoader layoutSwitcher(Pane parent, String layout) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent newLoadedPane = fxmlLoader.load(getClass().getClassLoader().getResource("./layouts/" + layout).openStream());

            parent.getChildren().setAll(newLoadedPane);
            return fxmlLoader;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double calculateAverage(Collection<Double> marks) {
        Double sum = 0.0;
        if(!marks.isEmpty()) {
            for (Double mark : marks) {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }
}
