package com.atguigu.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class TestPOI {
    @Test
    /**
     * 直接遍历整张excel表获取数据
     */
    public void testReadExcel() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("D:\\hello.xlsx");
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                String cellValue = cell.getStringCellValue();
                System.out.println("cellValue = " + cellValue);
            }
        }
        workbook.close();
    }

    @Test
    /**
     * 先获取excel表的最后有数据的行数
     * 再进行遍历取出数据
     */
    public void testReadExcelLastRow() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("D:\\hello.xlsx");
        XSSFSheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                String stringCellValue = row.getCell(j).getStringCellValue();
                System.out.println("stringCellValue = " + stringCellValue);


            }
        }
    }

    @Test
    public void testImportExcel() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("bestwei");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("年龄");
        row.createCell(2).setCellValue("地址");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("小米");
        row1.createCell(1).setCellValue(99);
        row1.createCell(2).setCellValue("上海");

        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("小米1");
        row2.createCell(1).setCellValue(919);
        row2.createCell(2).setCellValue("上海1");

        FileOutputStream out = new FileOutputStream("D:\\testhello.xlsx");
        workbook.write(out);
        workbook.close();
    }
}
