package controller;


import entity.Words;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
import utils.AlertWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.StringTokenizer;


public class MainController {

    final Random random = new Random();

    static private ObservableList<Words> listWords = FXCollections.observableArrayList();
    private ArrayList<Words> checkedList;

    private Stage editDialogStage;
    private Stage irregularVerbsDialogStage;
    private Stage guessStage;
    private Parent fxmlEdit;
    private Parent fxmlIrregularVerbs;
    private Parent fxmlGuess;
    private FXMLLoader fxmlLoaderEdit = new FXMLLoader();
    private FXMLLoader fxmlLoaderIrregularVerbs = new FXMLLoader();
    private FXMLLoader fxmlLoaderGuess = new FXMLLoader();
    private Stage mainStage;
    private Words randomWords;
    private TextField inputWords;
    private AlertWindow alertWindow = new AlertWindow();
    private boolean emptyFlag;
    private boolean dictionaryTranslateFlag = true;

    @FXML
    private AnchorPane root;
    @FXML
    private Button mainButton;
    @FXML
    private Button dictionaryButton;
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
        inputWords.setPrefWidth(190);
        inputWords.setLayoutX(105);
        inputWords.setLayoutY(170);
        AnchorPane.setRightAnchor(inputWords, 95d);
        AnchorPane.setLeftAnchor(inputWords, 95d);

        if (mainButton.getText().equals("Start!")) dictionaryButton.setDisable(true);

        try {
        fxmlLoaderEdit.setLocation(getClass().getResource("/views/EditWindow.fxml"));
        fxmlEdit = fxmlLoaderEdit.load();
        fxmlLoaderIrregularVerbs.setLocation(getClass().getResource("/views/IrregularVerbs.fxml"));
        fxmlIrregularVerbs = fxmlLoaderIrregularVerbs.load();
        fxmlLoaderGuess.setLocation(getClass().getResource("/views/GuessWindow.fxml"));
        fxmlGuess = fxmlLoaderGuess.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }



    public void mButton(ActionEvent actionEvent) {
        System.out.println("Hello world!");
        emptyFlag = true;

        if (!dictionaryTranslateFlag){
            mainButton.setText("Next");
            dictionaryTranslateFlag = true;
        }

        if (mainButton.getText().equals("Start!")){
            selectFile();
            checkedList = new ArrayList<>(listWords);
            dictionaryButton.setDisable(false);
        }

        if (checkedList == null || checkedList.isEmpty()){

            ButtonType buttonTypeOne = new ButtonType("Продолжить");
            ButtonType buttonTypeTwo = new ButtonType("Загрузить");

            ArrayList<ButtonType> buttonList = new ArrayList<ButtonType>(){{add(buttonTypeOne);add(buttonTypeTwo);}};
            Optional<ButtonType> result = alertWindow.alertListPassed(buttonList);

            if (result.get() == buttonTypeOne){

                checkedList = new ArrayList<>(listWords);

            } else if (result.get() == buttonTypeTwo) {

                selectFile();
                checkedList = new ArrayList<>(listWords);

            }
        }

        if (mainButton.getText().equals("Next") || mainButton.getText().equals("Start!")) {
            inputWords.clear();
            inputWords.setPromptText("Поле для ввода слова");
            emptyFlag = false;
            randomWords = randomValueMap(checkedList);
            if (dictionaryButton.getText().equals("EN-->RU")) labelMessage.setText(randomWords.getEnWord());
            else labelMessage.setText(randomWords.getRuWord());
            labelMessage.setStyle("-fx-text-fill: #9E9E9E");
            mainButton.setText("Check");
        }

        if (!inputWords.getText().trim().equals("")) {

            if (checkedWords(inputWords.getText(), randomWords)) {
                checkedList.remove(randomWords);
                labelMessage.setText("Right!");
                labelMessage.setStyle("-fx-text-fill: green");
            } else {
                labelMessage.setText("False!");
                labelMessage.setStyle("-fx-text-fill: red");
            }
            mainButton.setText("Next");
        } else if(emptyFlag) alertWindow.alertFieldEmpty();

    }

    public void dButton(ActionEvent actionEvent) {
        if (dictionaryTranslateFlag) dictionaryTranslateFlag = false;
        if (dictionaryButton.getText().equals("EN-->RU")) dictionaryButton.setText("RU-->EN");
        else dictionaryButton.setText("EN-->RU");
        mainButton.fire();
    }

    public void gButton(ActionEvent actionEvent) {

        if (guessStage==null) {
            guessStage = new Stage();
            guessStage.setTitle("Выбери правильный вариант");
            guessStage.setMinHeight(150);
            guessStage.setMinWidth(300);
            guessStage.setScene(new Scene(fxmlGuess));
           // guessStage.initModality(Modality.WINDOW_MODAL);
            //guessStage.initOwner(mainStage);
        }

        mainStage.hide();

        guessStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                mainStage.show();
            }
        });

        guessStage.show();

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

    private boolean checkedWords(String inputWords, Words randomWord){

        StringTokenizer st;

        if (dictionaryButton.getText().equals("EN-->RU")) st = new StringTokenizer(randomWord.getRuWord(),",;");
        else st = new StringTokenizer(randomWord.getEnWord(),",;");

        while (st.hasMoreTokens()){
            if (st.nextToken().toLowerCase().trim().equals(inputWords.toLowerCase().trim())) return true;
        }
        return false;
    }


    private void selectFile(){

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel", "xls", "xlsx");
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\Anton_Nikonov\\Desktop");
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

    private Words randomValueMap(ArrayList<Words> list) {

        if (list != null && !list.isEmpty()) return list.get(random.nextInt(list.size()));
        else {
            alertWindow.alertErrorReadFiles();
        }
        return null;
    }


    public ObservableList<Words> getListWords(){
        return listWords;
    }

}

/*
        public void actionClose(ActionEvent ae){

        Node source = (Node) ae.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();

    }

*/