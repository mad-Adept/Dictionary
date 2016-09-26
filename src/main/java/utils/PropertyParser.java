package utils;

import controller.MainController;
import entity.Words;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

public class PropertyParser {

    private ObservableList<Words> list;

    private Properties prop = new Properties();

    private InputStream input = null;

    private OutputStream output = null;

    private InputStreamReader inR = null;

    private AlertWindow alertWindow = new AlertWindow();

    private MainController main = new MainController();


    public void readProperty(String path){

        try {
            input = (getClass().getClassLoader().getResourceAsStream(path));

            if (input == null){
                alertWindow.alertReadProperty();
            }
            inR = new InputStreamReader(input, "UTF-8");
            prop.load(inR);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            alertWindow.alertReadProperty();

        } catch (IOException e) {
            e.printStackTrace();
            alertWindow.alertReadProperty();
        }

        list = main.getListWords();
        list.clear();
        Enumeration<?> e = prop.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = prop.getProperty(key);
            System.out.println("Key : " + key + ", Value : " + value);

            list.add(new Words(key, value));
        }
    }
}
