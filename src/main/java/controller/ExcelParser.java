package controller;


import entity.Words;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.AlertWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ExcelParser {

    private AlertWindow aw = new AlertWindow();


    public ObservableList<Words> wordsMap(File file) {

        if (getFileExtension(file).equals("xls")) return parseXLS(file);
        else return parseXLSX(file);

        }



        private ObservableList<Words> parseXLS(File fileXLS){

            ObservableList<Words> localListWordsXLS = FXCollections.observableArrayList();

            HSSFWorkbook myExcelBook = null;

            try {
                myExcelBook = new HSSFWorkbook(new FileInputStream(fileXLS));
            } catch (IOException e) {
                e.printStackTrace();
            }

            HSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);

            Iterator<Row> iterRow = myExcelSheet.rowIterator();

            while (iterRow.hasNext()){

                HSSFRow row = (HSSFRow) iterRow.next();

                if (checkCell(row.getCell(0)) && checkCell(row.getCell(1))) {
                    if (checkEnglishWord(row.getCell(0)))
                        localListWordsXLS.add(new Words(row.getCell(0).toString(), row.getCell(1).toString()));

                    else if (checkEnglishWord(row.getCell(1)))
                        localListWordsXLS.add(new Words(row.getCell(1).toString(), row.getCell(0).toString()));

                    else aw.alertErrorFormat("Не верный формат записи слов в строке: " + row.getCell(0).toString()
                        + " " + row.getCell(1).toString());
                }
                else aw.alertErrorReadRow(row.getRowNum() + 1);
            }
            return localListWordsXLS;
        }


        private ObservableList<Words> parseXLSX(File fileXLSX){

            ObservableList<Words> localListWordsXLSX = FXCollections.observableArrayList();

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
                        localListWordsXLSX.add(new Words(row.getCell(0).toString(), row.getCell(1).toString()));

                    else if (checkEnglishWord(row.getCell(1)))
                        localListWordsXLSX.add(new Words(row.getCell(1).toString(), row.getCell(0).toString()));

                    else aw.alertErrorFormat("Не верный формат записи слов в строке: " + row.getCell(0).toString()
                                + " " + row.getCell(1).toString());
                }
                else aw.alertErrorReadRow(row.getRowNum() + 1);
            }
            return localListWordsXLSX;
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