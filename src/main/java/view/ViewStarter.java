package view;

import controller.MongoDbController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewStarter extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("./layouts/pick_html_element.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
//            new MongoDbController().connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
