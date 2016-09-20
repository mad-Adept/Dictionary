package controller;


import entity.Words;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;
import utils.AlertWindow;

import java.util.ArrayList;

public class EditController {

    private ObservableList<Words> list;
    private AlertWindow alertWindow = new AlertWindow();
    private Words selectedWords;
    private TextField inputSearch;
    private ArrayList<Words> saveList;
    private ArrayList<Words> searchList;

    @FXML
    private AnchorPane rootEdit;
    @FXML
    private TableView<Words> tableWords;
    @FXML
    private TableColumn<Words, String> enColumn;
    @FXML
    private TableColumn<Words, String> ruColumn;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;

    @FXML
    private void initialize() {

        enColumn.setCellValueFactory(new PropertyValueFactory<Words, String>("enWord"));
        ruColumn.setCellValueFactory(new PropertyValueFactory<Words, String>("ruWord"));

        tableWords.setItems(getWordList());

        inputSearch = TextFields.createClearableTextField();
        rootEdit.getChildren().addAll(inputSearch);
        inputSearch.setPrefWidth(250);
        inputSearch.setLayoutX(22);
        inputSearch.setLayoutY(40);
        AnchorPane.setRightAnchor(inputSearch, 150d);
        AnchorPane.setLeftAnchor(inputSearch, 22d);

        TextFields.bindAutoCompletion(inputSearch, list);

        tableWords.setEditable(true);

        enColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        enColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Words, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Words, String> event) {
                event.getTableView().getItems().get(
                        event.getTablePosition().getRow()).setEnWord(event.getNewValue());

                saveList = new ArrayList<>(list);
            }
        });

        ruColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ruColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Words, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Words, String> event) {
                event.getTableView().getItems().get(
                        event.getTablePosition().getRow()).setRuWord(event.getNewValue());

                saveList = new ArrayList<>(list);
            }
        });

        saveList = new ArrayList<>(list);
    }



    private ObservableList<Words> getWordList(){

        list = new MainController().getListWords();

        if (list.isEmpty() || list == null){
            tableWords.setPlaceholder(new Text("Net contenta"));
        }

        return list;
    }

    public void searchWords(ActionEvent actionEvent) {

        if (!inputSearch.getText().trim().equals("")){

            list.clear();
            searchList = searchCollacations(inputSearch.getText());
            if (!searchList.isEmpty()) list.addAll(searchList);
            else tableWords.setPlaceholder(new Text("Net contenta"));

            Notifications notifications = Notifications.create()
                    .title("Attention")
                    .text("Что бы вернуть полный список нажмите кнопку ПОиска с пустым полем.")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);
            notifications.showConfirm();
        } else {

            list.clear();
            if (saveList != null && !saveList.isEmpty()) list.addAll(saveList);
        }
    }

    public void addWords(ActionEvent actionEvent) {

        if (checkCorrectWords(textField1.getText()) && checkCorrectWords(textField2.getText())){
            if (checkEnglishWord(textField1.getText())){

                list.add(new Words(textField1.getText(), textField2.getText()));
            }
            else if (checkEnglishWord(textField2.getText())){

                list.add(new Words(textField2.getText(), textField1.getText()));
            }
            else alertWindow.alertFieldAdd();
        }
        else alertWindow.alertFieldAdd();

        saveList = new ArrayList<>(list);
    }

    public void deleteWords(ActionEvent actionEvent) {

        selectedWords = (Words) tableWords.getSelectionModel().getSelectedItem();

        if (selectedWords != null){

            list.remove(selectedWords);
        }
        else alertWindow.alertErrorDelete();

        saveList = new ArrayList<>(list);
    }

    private boolean checkCorrectWords(String words){

        if (words.isEmpty() || words == null) return false;

        if (words.toString().matches("^[A-Za-zА-Яа-я,; ]+$")) return true;
        else return false;
    }

    private boolean checkEnglishWord(String word){

        if (word.isEmpty() || word == null) return false;

        if (word.matches("^[A-Za-z,; ]+$")) return true;
        else return false;
    }

    private ArrayList<Words> searchCollacations(String collacation){

        ArrayList<Words> returnList = new ArrayList<>();

        for (Words iterWords : saveList){

            if (iterWords.toString().trim().contains(collacation.trim())){
                returnList.add(iterWords);
            }
        }
        return returnList;
    }
}


/*
        fadeOut = new FadeTransition(Duration.seconds(3), attention);
        fadeOut.setFromValue(0.0);
        fadeOut.setToValue(1.0);
        fadeOut.setCycleCount(-1);
        fadeOut.setAutoReverse(true);
        fadeOut.play();
*/
