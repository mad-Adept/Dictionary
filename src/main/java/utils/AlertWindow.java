package utils;


import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Optional;

public class AlertWindow {


    public void alertErrorReadFiles(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert Title");
        alert.setHeaderText("Ошибка чтения Excel файла.");
        alert.setContentText("Сохраните документ в рассширении *.xlsx и " +
                "запустите программу вновь.");
        alert.setWidth(200);
        alert.setHeight(200);
        alert.showAndWait();
        System.exit(0);
    }

    public void alertSizeList(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert Title");
        alert.setHeaderText("Ошибка чтения Excel файла.");
        alert.setContentText("В текущем документе слишком мало слов для работы в режиме Угадывания!");
        alert.setWidth(200);
        alert.setHeight(200);
        alert.showAndWait();
    }

    public void alertFieldEmpty(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert Title");
        alert.setHeaderText(null);
        alert.setContentText("Заполните поле для ввода слова.");
        alert.setWidth(200);
        alert.setHeight(200);
        alert.showAndWait();
    }

    public Optional<ButtonType> alertListPassed(ArrayList<ButtonType> buttonList){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert Title");
        alert.setHeaderText("Поздравляю!");
        alert.setContentText("Лист слов успешно пройден, вы можете продолжить текущий или загрузить новый!");
        alert.setWidth(200);
        alert.setHeight(200);

        alert.getButtonTypes().setAll(buttonList);

        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    public void alertErrorFormat(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert Title");
        alert.setHeaderText("Look, an Alert dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void alertErrorReadRow(Integer rowNumber) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert Title");
        alert.setHeaderText("Look, an Alert dialog");
        alert.setContentText("Error in " + rowNumber);

        Platform.runLater(() ->
        {
            javafx.scene.control.Hyperlink detailsButton = (javafx.scene.control.Hyperlink) alert.getDialogPane().lookup(".details-button");

            alert.getDialogPane().expandedProperty().addListener(
                    (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
                    {
                        detailsButton.setText(newValue ? "My less text" : "My more text");
                    });

            // trigger listeners
            alert.getDialogPane().setExpanded(true);
            alert.getDialogPane().setExpanded(false);
        });

        Image image = new Image(getClass().getClassLoader().getResource("images/re.PNG").toString());
        ImageView im = new ImageView(image);
        im.setSmooth(true);
        im.setBlendMode(BlendMode.DARKEN);
        im.setCursor(Cursor.TEXT);


/*        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(10);
        dropShadow.setOffsetY(10);
        im.setEffect(dropShadow);
        im.setOpacity(0.5); */

        Label lab = new Label("Не выерный формат записи в Excel файле. Нужно примерно так:");


        GridPane gp = new GridPane();
        gp.setMaxWidth(Double.MAX_VALUE);
        gp.add(lab, 0, 0);
        gp.add(im, 0, 1);

        alert.getDialogPane().setExpandableContent(gp);
        alert.showAndWait();
       // System.exit(0);

    }
}
