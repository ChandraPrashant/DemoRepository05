package com.test.utility;

import java.util.ArrayList;
import java.util.Properties;
import com.qa.utils.ReadExcelData;
import com.qa.utils.ReadPropertyFile;

public class TestUtil {

	static ReadExcelData reader;
	static Properties prop = null;
	static String proppath = "/src/main/resources/Global.properties";

	public static ArrayList<Object[]> getDataFromExcel() throws Exception {

		prop = ReadPropertyFile.readPropertiesFile(System.getProperty("user.dir") + proppath);

		ArrayList<Object[]> myData = new ArrayList<Object[]>();

		try {
			reader = new ReadExcelData(System.getProperty("user.dir") + prop.getProperty("filepath") );
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int rowNum = 2; rowNum <= reader.getRowCount(prop.getProperty("freecrmsheetname")); rowNum++) {

			String username = reader.getCellData(prop.getProperty("freecrmsheetname"), "UserName", rowNum);
			String password = reader.getCellData(prop.getProperty("freecrmsheetname"), "PassWord", rowNum);

			Object ob[] = { username, password };
			myData.add(ob);
		}

		return myData;
	}

}
