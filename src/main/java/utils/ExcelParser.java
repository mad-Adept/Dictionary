package utils;


import entity.Words;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.AlertWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;


public class ExcelParser {

    private AlertWindow aw = new AlertWindow();


    public ObservableList<Words> wordsMap(File file) {

        if (getFileExtension(file.getName()).equals("xls")) return parseXLS(file);
        else return parseXLSX(file);

        }


    public void saveInExcel(File file, ObservableList<Words> listWords) throws IOException {


        if (getFileExtension(file.getName()).equals("xls")) {

            HSSFWorkbook book = new HSSFWorkbook();
            HSSFSheet sheet = book.createSheet();

            for (int iter = 0; iter < listWords.size(); iter++) {

                HSSFRow row = sheet.createRow(iter);

                row.createCell(0).setCellValue(listWords.get(iter).getEnWord());
                row.createCell(1).setCellValue(listWords.get(iter).getRuWord());
            }

            sheet.autoSizeColumn(1);
            // Записываем всё в файл
            book.write(new FileOutputStream(file));
            book.close();
        } else {

            XSSFWorkbook book = new XSSFWorkbook();
            XSSFSheet sheet = book.createSheet();

            for (int iter = 0; iter < listWords.size(); iter++) {

                XSSFRow row = sheet.createRow(iter);

                row.createCell(0).setCellValue(listWords.get(iter).getEnWord());
                row.createCell(1).setCellValue(listWords.get(iter).getRuWord());
            }

            sheet.autoSizeColumn(1);
            // Записываем всё в файл
            book.write(new FileOutputStream(file));
            book.close();

        }
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

        private String getFileExtension(String fileName) {

            // если в имени файла есть точка и она не является первым символом в названии файла
            if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
                // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
                return fileName.substring(fileName.lastIndexOf(".")+1);
                // в противном случае возвращаем заглушку, то есть расширение не найдено
            else return "";
        }

        private boolean checkCell(Cell cell){

            if (cell == null) return false;

            if (cell.toString().matches("^[A-Za-zА-Яа-я,;()/. ]+$")) return true;
            else return false;
        }

        private boolean checkEnglishWord(Cell cell){
        if (cell.toString().matches("^[A-Za-z,;()/. ]+$")) return true;
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