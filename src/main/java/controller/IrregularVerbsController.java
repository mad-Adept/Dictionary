package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import utils.PropertyParser;


public class IrregularVerbsController {

    private PropertyParser propertyParser = new PropertyParser();


    @FXML
    private WebView webView_1;
    @FXML
    private WebView webView_2;
    @FXML
    private WebView webView_3;
    @FXML
    private WebView webView_4;
    @FXML
    private WebView webView_5;
    @FXML
    private WebView webView_6;
    @FXML
    private WebView webView_7;
    @FXML
    private CheckBox check_1_50;
    @FXML
    private CheckBox check_2_50;
    @FXML
    private CheckBox check_3_50;

    @FXML
    private void initialize() {

        webView_1.getEngine().load(getClass().getClassLoader().getResource("html/50=50%.html").toExternalForm());
        webView_2.getEngine().load(getClass().getClassLoader().getResource("html/100=70%.html").toExternalForm());
        webView_3.getEngine().load(getClass().getClassLoader().getResource("html/150=90%.html").toExternalForm());
        webView_4.getEngine().load(getClass().getClassLoader().getResource("html/200=93%.html").toExternalForm());
        webView_5.getEngine().load(getClass().getClassLoader().getResource("html/300=97%.html").toExternalForm());
        webView_6.getEngine().load(getClass().getClassLoader().getResource("html/366=99%.html").toExternalForm());
        webView_7.getEngine().load(getClass().getClassLoader().getResource("html/700=100%.html").toExternalForm());

    }


    public void button_50(ActionEvent actionEvent) {

        if (check_1_50.isSelected()){
            propertyParser.readProperty("property/100=70%/1 form.property");
        }

        if (check_2_50.isSelected()){
            propertyParser.readProperty("property/100=70%/2 form.property");
        }

        if (check_3_50.isSelected()){
            propertyParser.readProperty("property/100=70%/3 form.property");
        }

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }
}
