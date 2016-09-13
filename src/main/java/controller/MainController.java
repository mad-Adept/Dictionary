package controller;


import entity.Words;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.tools.Borders;
import utils.AlertWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class MainController {

    final Random random = new Random();

    static private ObservableList<Words> listWords = FXCollections.observableArrayList();
    private ObservableList<Words> checkedList = FXCollections.observableArrayList();

    private Stage editDialogStage;
    private Stage irregularVerbsDialogStage;
    private Parent fxmlEdit;
    private Parent fxmlIrregularVerbs;
    private FXMLLoader fxmlLoaderEdit = new FXMLLoader();
    private FXMLLoader fxmlLoaderIrregularVerbs = new FXMLLoader();
    private Stage mainStage;
    private Words randomWords;
    private TextField inputWords;
    private AlertWindow alertWindow = new AlertWindow();
    private boolean emptyFlag = true;


    @FXML
    private AnchorPane root;

    @FXML
    private Button mainButton;

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

        inputWords = TextFields.createClearableTextField();
        root.getChildren().addAll(inputWords);
        inputWords.setLayoutX(120);
        inputWords.setLayoutY(140);

        try {
        fxmlLoaderEdit.setLocation(getClass().getResource("/views/EditWindow.fxml"));
        fxmlEdit = fxmlLoaderEdit.load();
        fxmlLoaderIrregularVerbs.setLocation(getClass().getResource("/views/IrregularVerbs.fxml"));
        fxmlIrregularVerbs = fxmlLoaderIrregularVerbs.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }



    public void mButton(ActionEvent actionEvent) {
        System.out.println("Hello world!");

        if (mainButton.getText().equals("Start!")) selectFiles();

        if (checkedList == null || checkedList.isEmpty()) checkedList = listWords;

/*        while (inputWords.getText().trim().equals("")){
            System.out.println("vvedite v pole");
        }*/

        if (inputWords.equals(randomWords.getRuWord())) {
            checkedList.remove(randomWords);
            labelMessage.setText("Right!");
        } else labelMessage.setText("False!");

        if (labelMessage.getText().equals("Right!") || labelMessage.getText().equals("False!")
                || mainButton.getText().equals("Start!")) {
            randomWords = randomValueMap(checkedList);
            labelMessage.setText(randomWords.getEnWord());
            mainButton.setText("Check");
        }
    }


    private void selectFiles(){

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel", "xls", "xlsx");
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\Anton\\Desktop");
        fileChooser.setFileFilter(filter);
        int ret = fileChooser.showDialog(null, "Open file");

        if (ret == JFileChooser.APPROVE_OPTION) {
            try {
                listWords.addAll(new ExcelParser().wordsMap(fileChooser.getSelectedFile()));

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

        Notifications notifications = Notifications.create()
                .title("Attention")
                .text("Не забудьте нажать Enter после редактирования поля")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT);
        notifications.showConfirm();

        editDialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Okno zakrito!");
            }
        });

    }

    public void secondButton(ActionEvent actionEvent) {



    }

    private Words randomValueMap(ObservableList<Words> list) {

        if (list != null && !list.isEmpty()) return list.get(random.nextInt(list.size()));
        else {
            alertWindow.alertErrorReadFiles();
        }
        return null;
    }


    public ObservableList<Words> getListWords(){
        return listWords;
    }

    public void IrregVerbs(ActionEvent actionEvent) {

        if (irregularVerbsDialogStage==null) {
            irregularVerbsDialogStage = new Stage();
            irregularVerbsDialogStage.setTitle("jgjhgjg");
            irregularVerbsDialogStage.setMinHeight(150);
            irregularVerbsDialogStage.setMinWidth(300);
            irregularVerbsDialogStage.setScene(new Scene(fxmlIrregularVerbs));
           // irregularVerbsDialogStage.initModality(Modality.WINDOW_MODAL);
            irregularVerbsDialogStage.initOwner(mainStage);
        }

        irregularVerbsDialogStage.show();
    }
}

/*
    private boolean checkedWords(String inputWords, ObservableList<Words> list){

        for (Words iterWord : list){

            StringTokenizer st = new StringTokenizer(iterWord.getRuWord()," ,;");
            while (st.hasMoreTokens()){
                if (st.nextToken().equals(inputWords)) return true;
            }

        }
        return false;
    }

        public void actionClose(ActionEvent ae){

        Node source = (Node) ae.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();

    }

*/