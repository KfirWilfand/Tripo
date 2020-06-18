package view;

import controller.ui.AdminPanelController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewAdminPanel extends Application {
    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./layouts/admin_panel.fxml"));

//        AdminPanelController controller = new AdminPanelController();
//        loader.setController(controller);
//
//        try {
//            Parent root = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("./layouts/admin_panel.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            this.primaryStage = primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
