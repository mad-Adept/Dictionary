package controller;


import entity.Words;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import utils.AlertWindow;

public class EditController {

    private FadeTransition fadeOut;
    private ObservableList<Words> list;
    private AlertWindow alertWindow = new AlertWindow();

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

/*
        fadeOut = new FadeTransition(Duration.seconds(3), attention);
        fadeOut.setFromValue(0.0);
        fadeOut.setToValue(1.0);
        fadeOut.setCycleCount(-1);
        fadeOut.setAutoReverse(true);
        fadeOut.play();
*/

        tableWords.setEditable(true);

        enColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        enColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Words, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Words, String> event) {
                event.getTableView().getItems().get(
                        event.getTablePosition().getRow()).setEnWord(event.getNewValue());

                System.out.println(event.getTableView().getItems());
            }
        });

        ruColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ruColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Words, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Words, String> event) {
                event.getTableView().getItems().get(
                        event.getTablePosition().getRow()).setRuWord(event.getNewValue());

                System.out.println(event.getTableView().getItems());
            }
        });
    }



    private ObservableList<Words> getWordList(){

        list = new MainController().getListWords();

        if (list.isEmpty() || list == null){
            tableWords.setPlaceholder(new Text("Net contenta"));
        }

        return list;
    }

    public FadeTransition getfadeOut(){
        return fadeOut;
    }

    public void searchWords(ActionEvent actionEvent) {

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

    }

    public void deleteWords(ActionEvent actionEvent) {

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
}
