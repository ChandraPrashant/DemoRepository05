package com.qa.utils;

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ReadExcelData extends ReadPropertyFile {

	public static String propath = "/src/main/resources/Global.properties";
	static FileInputStream fis = null;
	static XSSFWorkbook wb = null;
	static XSSFSheet sheet = null;
	static XSSFRow currentRow = null;
	protected static Properties prop = null;

	public ReadExcelData() throws Exception {
		
		prop = readPropertiesFile(System.getProperty("user.dir") + propath);

	}
	
	@Test
	public static void readExcelSheet() throws Exception {

		Properties prop = readPropertiesFile(System.getProperty("user.dir") + propath);

		fis = new FileInputStream(System.getProperty("user.dir") + prop.getProperty("filepath"));

		wb = new XSSFWorkbook(fis);

		sheet = wb.getSheet(prop.getProperty("sheetname"));

		int rowCount = sheet.getLastRowNum();

		int colCount = sheet.getRow(0).getLastCellNum();

		for (int i = 0; i < rowCount; i++) {

			currentRow = sheet.getRow(i);

			for (int j = 0; j < colCount; j++) {

				String value = currentRow.getCell(j).toString();
				System.out.print(" "+value + " ");
				
			}
			System.out.println("");
		}

 }

}
