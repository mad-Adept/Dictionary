package controller;

import entity.Words;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import utils.AlertWindow;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

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


    @FXML
    private Label labelMess;
    @FXML
    private Button buttonChoose1;
    @FXML
    private Button buttonChoose2;
    @FXML
    private Button buttonChoose3;


    @FXML
    private void initialize() {

        listWords = new MainController().getListWords();
        arreyListWords = new ArrayList<>(listWords);
        setTextButtonAndLabel();
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
        setTextButtonAndLabel();
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
        setTextButtonAndLabel();
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
        setTextButtonAndLabel();
    }

    private Words randomValueMap(ObservableList<Words> list) {

        if (list != null && !list.isEmpty()) return list.get(random.nextInt(list.size()));
        else {
            alertWindow.alertErrorReadFiles();
        }
        return null;
    }

    private void setTextButtonAndLabel(){

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {

        word1 = randomValueMap(listWords);
        buttonChoose1.setText(word1.getRuWord());

            while (true) {

                word2 = randomValueMap(listWords);
                if (word2.getRuWord().equals(buttonChoose1.getText())) continue;
                else {
                    buttonChoose2.setText(word2.getRuWord());
                    break;
                }
            }

            while (true) {

                word3 = randomValueMap(listWords);
                if (word3.getRuWord().equals(buttonChoose1.getText())
                        || word3.getRuWord().equals(buttonChoose2.getText())) continue;
                else {
                    buttonChoose3.setText(word3.getRuWord());
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

            case 0: labelMess.setText(word1.getEnWord());
                rightWord = word1;
                break;

            case 1: labelMess.setText(word2.getEnWord());
                rightWord = word2;
                break;

            case 2: labelMess.setText(word3.getEnWord());
                rightWord = word3;
                break;
        }
    }

    private boolean checkChoose(Button button){

        if (rightWord.getRuWord().equals(button.getText())) return true;
        else return false;
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

}
