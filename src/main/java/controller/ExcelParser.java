package controller;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ExcelParser {

    private Map<String, String> mapWords;



    public Map<String, String> wordsMap(File file) {

        if (getFileExtension(file).equals("xls")) mapWords = parseXLS(file);
        else mapWords = parseXLSX(file);

            return mapWords;
        }



        private Map<String, String> parseXLS(File fileXLS){

            Map<String, String> localMapWordsXLS = new HashMap<>();

            HSSFWorkbook myExcelBook = null;

            try {
                myExcelBook = new HSSFWorkbook(new FileInputStream(fileXLS));
            } catch (IOException e) {
                e.printStackTrace();
            }

            HSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);

            Iterator<Row> iterRow = myExcelSheet.rowIterator();

            // = myExcelSheet.getRow(0);

            while (iterRow.hasNext()){

                HSSFRow row = (HSSFRow) iterRow.next();


                if (checkCell(row.getCell(0)) && checkCell(row.getCell(1))) {
                    if (checkEnglishWord(row.getCell(0)))
                        localMapWordsXLS.put(row.getCell(0).toString(), row.getCell(1).toString());

                    else if (checkEnglishWord(row.getCell(1)))
                        localMapWordsXLS.put(row.getCell(1).toString(), row.getCell(0).toString());

                    else alertMessageRead();
                }
                else alertMessageRead();
            }
            return localMapWordsXLS;
        }


        private Map<String, String> parseXLSX(File fileXLSX){

            Map<String, String> localMapWordsXLSX = new HashMap<>();

            XSSFWorkbook myExcelBook = null;

            try {
                myExcelBook = new XSSFWorkbook(new FileInputStream(fileXLSX));
            } catch (IOException e) {
                e.printStackTrace();
            }

            XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);

            Iterator<Row> iterRow = myExcelSheet.rowIterator();

            while (iterRow.hasNext()){

                XSSFRow row = (XSSFRow) iterRow.next();
                if (checkCell(row.getCell(0)) && checkCell(row.getCell(1))) {
                    if (checkEnglishWord(row.getCell(0)))
                        localMapWordsXLSX.put(row.getCell(0).toString(), row.getCell(1).toString());

                    else if (checkEnglishWord(row.getCell(1)))
                        localMapWordsXLSX.put(row.getCell(1).toString(), row.getCell(0).toString());

                    else alertMessageRead();
                }
                else alertMessageRead();
            }
            return localMapWordsXLSX;
        }

        private String getFileExtension(File file) {

            String fileName = file.getName();
            // если в имени файла есть точка и она не является первым символом в названии файла
            if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
                // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
                return fileName.substring(fileName.lastIndexOf(".")+1);
                // в противном случае возвращаем заглушку, то есть расширение не найдено
            else return "";
        }

        private boolean checkCell(Cell cell){

            if (cell == null) return false;

            if (cell.toString().matches("^[A-Za-zА-Яа-я,; ]+$")) return true;
            else return false;
        }

        private boolean checkEnglishWord(Cell cell){
        if (cell.toString().matches("^[A-Za-z]+$")) return true;
        else return false;
        }

        private void alertMessageRead(){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert Title");
            alert.setHeaderText("Look, an Alert dialog");
            alert.setContentText("errererh");

            Platform.runLater(() ->
            {
                javafx.scene.control.Hyperlink detailsButton = (javafx.scene.control.Hyperlink) alert.getDialogPane().lookup( ".details-button" );

                alert.getDialogPane().expandedProperty().addListener(
                        (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue ) ->
                        {
                            detailsButton.setText( newValue ? "My less text" : "My more text" );
                        });

                // trigger listeners
                alert.getDialogPane().setExpanded( true );
                alert.getDialogPane().setExpanded( false );
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

            GridPane gp = new GridPane();
            gp.setMaxWidth(Double.MAX_VALUE);
            gp.add(im, 0, 1);

            alert.getDialogPane().setExpandableContent(gp);
            alert.showAndWait();
        }

    }




/*if (word.matches("^[A-Za-z]+$")) {
    System.out.println("Слово " + word + " состоит только из английских букв");
}
char c;
if (((c >= 'a')&&(c <= 'z')) || ((c >= 'A')&&(c <= 'Z'))){
    // символ принадлежит к английскому алфавиту
}

        if(row.getCell(1).getCellType() == HSSFCell.CELL_TYPE_STRING){
            String name = row.getCell(1).getStringCellValue();
            System.out.println("name : " + name);
        }
*/