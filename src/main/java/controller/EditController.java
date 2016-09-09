package controller;


import entity.Words;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;

public class EditController {

    private FadeTransition fadeOut;

    @FXML
    private TableView<Words> tableWords;

    @FXML
    private TableColumn<Words, String> enColumn;

    @FXML
    private TableColumn<Words, String> ruColumn;

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
    }



    private ObservableList<Words> getWordList(){

        ObservableList<Words> list;

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

    }

    public void deleteWords(ActionEvent actionEvent) {

    }
}
