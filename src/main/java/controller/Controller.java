package controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.AlertWindow;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.util.*;


public class Controller {

    final Random random = new Random();
    static private TreeMap<String, String> wordsMap = new TreeMap<>();
    private Stage editDialogStage;
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Stage mainStage;


    @FXML
    private Button fButton;

    @FXML
    private Label labelMessage;

    @FXML
    private void initialize() {
        try {

        fxmlLoader.setLocation(getClass().getResource("/views/EditWindow.fxml"));
        fxmlEdit = fxmlLoader.load();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void firstButton(ActionEvent actionEvent) {
        System.out.println("Hello world!");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel", "xls", "xlsx");
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\Anton_Nikonov\\Desktop");
        fileChooser.setFileFilter(filter);
        int ret = fileChooser.showDialog(null, "Open file");

        if (ret == JFileChooser.APPROVE_OPTION) {
            try {
                wordsMap.putAll(new ExcelParser().wordsMap(fileChooser.getSelectedFile()));
                System.out.println(wordsMap.firstKey());
                System.out.println(wordsMap);

                labelMessage.setText(wordsMap.firstKey());

            } catch (Exception e) {
                new AlertWindow().alertErrorReadFiles();
                e.printStackTrace();
            }

        }
    }

    public void menuEdit(ActionEvent actionEvent) {

        if (editDialogStage==null) {
            editDialogStage = new Stage();
            editDialogStage.setTitle("Редактирование записи");
            editDialogStage.setMinHeight(150);
            editDialogStage.setMinWidth(300);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(mainStage);
        }

        editDialogStage.show();

        editDialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Okno zakrito!");
            }
        });

    }

    public void secondButton(ActionEvent actionEvent) {
        System.out.println("Hello world 2!");

        labelMessage.setText(randomValueMap(wordsMap));

    }

    private String randomValueMap(TreeMap<String, String> treeMap){

        ArrayList<String> randomList = new ArrayList<>(treeMap.keySet());

        return randomList.get(random.nextInt(randomList.size()));
    }

    public void actionClose(ActionEvent ae){

        Node source = (Node) ae.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();

    }


}
