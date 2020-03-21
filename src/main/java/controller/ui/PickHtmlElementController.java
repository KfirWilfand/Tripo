package controller.ui;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.w3c.dom.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class PickHtmlElementController {

    @FXML
    private WebView wvMain;

    @FXML
    private TextField txfUrlAdress;

    @FXML
    private Button btnLoad;

    /** for communication to the Javascript engine. */
    JSObject javascriptConnector;

    /** for communication from the Javascript engine. */
    JavaConnector javaConnector = new JavaConnector();

    public void initialize() {
//        txfUrlAdress.textProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("textfield changed from " + oldValue + " to " + newValue);
//        });
    }

    @FXML
    void onLoadBtnClick(ActionEvent event) {
        String urlAddresss = txfUrlAdress.getText();
        WebEngine webEngine = wvMain.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.getLoadWorker().stateProperty()
                .addListener((obs, oldValue, newValue) -> {
//                    if (newValue == Worker.State.RUNNING) {

//                    }

                    System.out.println(newValue);
                    if (newValue == Worker.State.SUCCEEDED) {
                        System.out.println("finished loading");
//                        webEngine.executeScript(
//                                "document.getElementById('INDWrap').appendChild(document.createTextNode('World!'));"
//                        );
                        webEngine.executeScript("Object.keys(window).forEach(key => {\n" +
                                "let div = document.createElement('div');\n" +
                                "\n" +
                                "                                    if (/^onmouseover/.test(key)) {\n" +
                                "                                       window.addEventListener(key.slice(2), event => {\n" +
                                "                                        console.log(event.fromElement);\n" +
                                "                                        div.className = 'anotherClass';\n" +
                                "                                       div.style.position = 'absolute';\n" +
                                "                                        div.style.content = '';\n" +
                                "                                        div.style.height = `${event.fromElement.offsetHeight +'px'}`;\n" +
                                "                                        div.style.width = `${event.fromElement.offsetWidth +'px'}`;\n" +
                                "                                        div.style.top = `${event.fromElement.offsetTop + 'px'}`;\n" +
                                "//                                \"        div.style.right = `${100+ 'px'}`;\\n\" +\n" +
                                "//                                \"        div.style.bottom = `${100 + 'px'}`;\\n\" +\n" +
                                "                                        div.style.left = `${event.fromElement.offsetLeft + 'px'}`;\n" +
                                "                                       div.style.background = '#05f';\n" +
                                "                                        div.style.opacity = '0.25';\n" +
                                "\n" +
                                "                                        event.fromElement.appendChild(div);\n" +
                                "                                        });\n" +
                                "                                    if (/^onmouseout/.test(key)) {\n" +
                                "                                        window.addEventListener(key.slice(2), event => {\n" +
                                "                                        div.onmouseout='{div.style.display === \\\"none\\\"}';\n" +
                                "                                        });\n" +
                                "                                    }\n" +
                                "                                    }\n" +
                                "                                });\n" +
                                "\n" +
                                "                            ");

//                        try {
//                            TransformerFactory transformerFactory = TransformerFactory
//                                    .newInstance();
//                            Transformer transformer = transformerFactory.newTransformer();
//                            StringWriter stringWriter = new StringWriter();
//                            transformer.transform(new DOMSource(webEngine.getDocument()),
//                                    new StreamResult(stringWriter));
//                            String xml = stringWriter.getBuffer().toString();
//
//                            System.out.println(xml);
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                    }
                }); // addListener()



        webEngine.load(urlAddresss);

//        webEngine.reload();
        // set up the listener
//        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
//            if (Worker.State.SUCCEEDED == newValue) {
//                // set an interface object named 'javaConnector' in the web engine's page
//                JSObject window = (JSObject) webEngine.executeScript("window");
//                window.setMember("javaConnector", javaConnector);
//
//                // get the Javascript connector object.
//                javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");
//            }
//        });

//        System.out.println(javascriptConnector);
    }

    public class JavaConnector {
        /**
         * called when the JS side wants a String to be converted.
         *
         * @param value
         *         the String to convert
         */
        public void toLowerCase(String value) {
            if (null != value) {
                javascriptConnector.call("showResult", value.toLowerCase());
                System.out.println(value);
            }
        }
    }


}

