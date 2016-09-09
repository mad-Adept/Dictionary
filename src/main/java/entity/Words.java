package entity;

import javafx.beans.property.SimpleStringProperty;

public class Words {

    private SimpleStringProperty enWord = new SimpleStringProperty("");
    private SimpleStringProperty ruWord = new SimpleStringProperty("");

    public Words() {
    }

    public Words(String enWord, String ruWord) {
        this.enWord = new SimpleStringProperty(enWord);
        this.ruWord = new SimpleStringProperty(ruWord);
    }

    public String getEnWord() {
        return enWord.get();
    }

    public SimpleStringProperty enWordProperty() {
        return enWord;
    }

    public void setEnWord(String enWord) {
        this.enWord.set(enWord);
    }

    public String getRuWord() {
        return ruWord.get();
    }

    public SimpleStringProperty ruWordProperty() {
        return ruWord;
    }

    public void setRuWord(String ruWord) {
        this.ruWord.set(ruWord);
    }

    @Override
    public String toString() {
        return "Words{" +
                "enWord=" + enWord +
                ", ruWord=" + ruWord +
                '}';
    }
}
