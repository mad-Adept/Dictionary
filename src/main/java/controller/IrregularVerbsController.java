package controller;


import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class IrregularVerbsController {

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
    private void initialize() {

        webView_1.getEngine().load(getClass().getClassLoader().getResource("html/50=50%.html").toExternalForm());
        webView_2.getEngine().load(getClass().getClassLoader().getResource("html/100=70%.html").toExternalForm());
        webView_3.getEngine().load(getClass().getClassLoader().getResource("html/150=90%.html").toExternalForm());
        webView_4.getEngine().load(getClass().getClassLoader().getResource("html/200=93%.html").toExternalForm());
        webView_5.getEngine().load(getClass().getClassLoader().getResource("html/300=97%.html").toExternalForm());
        webView_6.getEngine().load(getClass().getClassLoader().getResource("html/366=99%.html").toExternalForm());
        webView_7.getEngine().load(getClass().getClassLoader().getResource("html/700=100%.html").toExternalForm());

    }
}
