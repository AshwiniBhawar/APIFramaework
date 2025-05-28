package com.qa.api.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qa.api.exceptions.APIExceptions;

public class ExcelUtil {
	
	private static String TEST_DATA_SHEET_PATH="./src/resources/java/testdata/APITestData.xlsx";
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	
	public static Object[][] readData(String sheetName) {
		
		Object data[][] = null;
		
		try {
			FileInputStream file= new FileInputStream(TEST_DATA_SHEET_PATH);
			
			workbook= new XSSFWorkbook(file);
			//workbook=WorkbookFactory.create(file);
			sheet=workbook.getSheet(sheetName);
			
			data=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			
			for(int row=0;row<sheet.getLastRowNum();row++) {
				
				for(int cell=0;cell<sheet.getRow(0).getLastCellNum();cell++) {
					
					data[row][cell] =sheet.getRow(row+1).getCell(cell).toString();
				}
			}
		
		} catch (FileNotFoundException e) {
			throw new APIExceptions("file not found at: "+TEST_DATA_SHEET_PATH);
		}
		catch (IOException e) {
			throw new APIExceptions("file access issue: "+TEST_DATA_SHEET_PATH);
		}
		
		return data;	
	}

}
