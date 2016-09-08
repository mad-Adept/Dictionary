package controller;


import entity.Words;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.AlertWindow;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.util.*;


public class MainController {

    final Random random = new Random();

    static private ObservableList<Words> listWords = FXCollections.observableArrayList();

    private Stage editDialogStage;
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Stage mainStage;
    private EditController editController;

    @FXML
    private Button fButton;

    @FXML
    private Label labelMessage;

    @FXML
    private MenuItem editMenu;

    @FXML
    private TableView<Words> tableWords;

    @FXML
    private TableColumn<Words, String> enColumn;

    @FXML
    private TableColumn<Words, String> ruColumn;


    @FXML
    private void initialize() {

        try {
        fxmlLoader.setLocation(getClass().getResource("/views/EditWindow.fxml"));
        fxmlEdit = fxmlLoader.load();
        editController = fxmlLoader.getController();

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
                listWords.addAll(new ExcelParser().wordsMap(fileChooser.getSelectedFile()));
                System.out.println(listWords.get(0));
                System.out.println(listWords);

                //labelMessage.setText(wordsMap.firstKey());

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

        //labelMessage.setText(randomValueMap(wordsMap));

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

    public ObservableList<Words> getListWords(){
        return listWords;
    }

}
