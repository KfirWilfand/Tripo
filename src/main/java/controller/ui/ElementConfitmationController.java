package controller.ui;

import controller.MongoDbController;
import controller.utils.Helper;
import controller.utils.Logger;
import controller.utils.TextType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.Text;

public class ElementConfitmationController {
    @FXML
    private Pane mainPane;

    @FXML
    private TextField txfUrlAdress;

    @FXML
    private Button btnInsetObject;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txfAttributes;

    @FXML
    private TextArea txaContent;

    @FXML
    void onCancelBtnClick(ActionEvent event) {
        Helper.getInstance().layoutSwitcher(mainPane, "pick_html_element.fxml");
    }

    @FXML
    void onInsetObjectBtnClick(ActionEvent event) {
        String link = txfUrlAdress.getText();
        String content = txaContent.getText();
        String attributes = txfAttributes.getText();

        Logger.warning((new Text(link, content, attributes, TextType.PerEx).getJsonFormat()));

//        MongoDbController.getInstance().addText(new Text(link, content, attributes));
    }

    @FXML
    void txfHtmlAttr(ActionEvent event) {

    }

    @FXML
    void txfWebLink(ActionEvent event) {

    }


    public void updateUi(Text text) {
        txfUrlAdress.setText(text.getLink());
        txfAttributes.setText(text.getAttributes());
        txaContent.setText(text.getContent());
    }
}
