package controller;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.naming.LinkRef;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Controller {

    @FXML
    private Button fButton;

    public void firstButton(ActionEvent actionEvent) {
        System.out.println("Hello world!");


        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel", "xls", "xlsx");
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\Anton\\Desktop");
        fileChooser.setFileFilter(filter);
        int ret = fileChooser.showDialog(null, "Open file");

        if (ret == JFileChooser.APPROVE_OPTION) {
            try {
                System.out.println(new ExcelParser().wordsMap(fileChooser.getSelectedFile()));
            } catch (Exception e) {
                //errorReadfiles();
                e.printStackTrace();
            }

        }
    }

    private void errorReadfiles(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert Title");
        alert.setHeaderText("Look, an Alert dialog");
        alert.setContentText("errererh");
        alert.showAndWait();
    }
}