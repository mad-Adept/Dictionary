package controller;

import entity.Words;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import utils.AlertWindow;

import java.util.*;

public class GuessController {

    private Random random = new Random();
    private AlertWindow alertWindow = new AlertWindow();
    static private ObservableList<Words> listWords;
    private ArrayList<Words> arreyListWords;
    private Words word1 = new Words();
    private Words word2 = new Words();
    private Words word3 = new Words();
    private Words rightWord = new Words();
    private int randomValueForLabel;
    private boolean flagPushButton = false;
    private Timer timer = new java.util.Timer();


    @FXML
    private Label labelMess;
    @FXML
    private Button buttonChoose1;
    @FXML
    private Button buttonChoose2;
    @FXML
    private Button buttonChoose3;
    @FXML
    private Button dictionaryButton;


    @FXML
    private void initialize() {

        listWords = new MainController().getListWords();
        arreyListWords = new ArrayList<>(listWords);
        setTextButtonAndLabel();

        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (flagPushButton){
                            setTextButtonAndLabel();
                            flagPushButton = false;
                        }
                    }
                });
            }
        }, 0, 2000);
    }

    public void choose1(ActionEvent actionEvent) {

        if (checkChoose(buttonChoose1)){
            labelMess.setText("Right!");
            labelMess.setStyle("-fx-text-fill: green");
            arreyListWords.remove(rightWord);

        }
        else {
            labelMess.setText("False!");
            labelMess.setStyle("-fx-text-fill: red");
        }

        flagPushButton = true;
    }

    public void choose2(ActionEvent actionEvent) {

        if (checkChoose(buttonChoose2)){
            labelMess.setText("Right!");
            labelMess.setStyle("-fx-text-fill: green");
            arreyListWords.remove(rightWord);
        }
        else {
            labelMess.setText("False!");
            labelMess.setStyle("-fx-text-fill: red");
        }

        flagPushButton = true;
    }

    public void choose3(ActionEvent actionEvent) {

        if (checkChoose(buttonChoose3)){
            labelMess.setText("Right!");
            labelMess.setStyle("-fx-text-fill: green");
            arreyListWords.remove(rightWord);
        }
        else {
            labelMess.setText("False!");
            labelMess.setStyle("-fx-text-fill: red");
        }

        flagPushButton = true;
    }

    public void dButton(ActionEvent actionEvent) {

        if (dictionaryButton.getText().equals("EN-->RU")) dictionaryButton.setText("RU-->EN");
        else dictionaryButton.setText("EN-->RU");
        setTextButtonAndLabel();
    }

    private Words randomValueMap(ObservableList<Words> list) {

        if (list != null && !list.isEmpty()) return list.get(random.nextInt(list.size()));
        else alertWindow.alertErrorReadFiles();
        return null;
    }

    private void setTextButtonAndLabel(){

        while (true) {

        word1 = randomValueMap(listWords);

        buttonChoose1.setText(dictionaryString(word1, false));

            while (true) {

                word2 = randomValueMap(listWords);
                if (dictionaryString(word2, false).equals(buttonChoose1.getText())) continue;
                else {
                    buttonChoose2.setText(dictionaryString(word2, false));
                    break;
                }
            }

            while (true) {

                word3 = randomValueMap(listWords);
                if (dictionaryString(word3, false).equals(buttonChoose1.getText())
                        || dictionaryString(word3, false).equals(buttonChoose2.getText())) continue;
                else {
                    buttonChoose3.setText(dictionaryString(word3, false));
                    break;
                }
            }

            if (checkValueList(word1) || checkValueList(word2) || checkValueList(word3)) {
                setTextLabel();
                break;
            }

        }

    }

    private void setTextLabel(){

        checkEmptyArrayListWords();

        randomValueForLabel = random.nextInt(3);
        labelMess.setStyle("-fx-text-fill: #9E9E9E");

        switch (randomValueForLabel){

            case 0: labelMess.setText(dictionaryString(word1, true));
                rightWord = word1;
                break;

            case 1: labelMess.setText(dictionaryString(word2, true));
                rightWord = word2;
                break;

            case 2: labelMess.setText(dictionaryString(word3, true));
                rightWord = word3;
                break;
        }
    }

    private boolean checkChoose(Button button){

        if (dictionaryButton.getText().equals("EN-->RU")) {
            if (rightWord.getRuWord().equals(button.getText())) return true;
        }
        if (dictionaryButton.getText().equals("RU-->EN")){
            if (rightWord.getEnWord().equals(button.getText())) return true;
        }

        return false;
    }

    private boolean checkValueList(Words word) {

        for (int i = 0; i < arreyListWords.size(); i++) {

            if (arreyListWords.get(i).equals(word)) return true;
        }
        return false;
    }

    private void checkEmptyArrayListWords(){

        if (arreyListWords == null || arreyListWords.isEmpty()){

            ButtonType buttonTypeOne = new ButtonType("Продолжить");
            ButtonType buttonTypeTwo = new ButtonType("Загрузить");

            ArrayList<ButtonType> buttonList = new ArrayList<ButtonType>(){{add(buttonTypeOne);add(buttonTypeTwo);}};
            Optional<ButtonType> result = alertWindow.alertListPassed(buttonList);

            if (result.get() == buttonTypeOne){

                arreyListWords = new ArrayList<>(listWords);

            } else if (result.get() == buttonTypeTwo) {

                new MainController().selectFile();
                arreyListWords = new ArrayList<>(listWords);

            }
        }
    }

    private String dictionaryString(Words words, boolean reverse){
       if (reverse) {
           if (dictionaryButton.getText().equals("EN-->RU")) return words.getEnWord();
           else return words.getRuWord();
       } else {
           if (dictionaryButton.getText().equals("EN-->RU")) return words.getRuWord();
           else return words.getEnWord();
       }
    }

    public Timer getTimer(){
        return timer;
    }
}
