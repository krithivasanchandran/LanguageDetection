package com.locdata.ingest.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/*
 * Default sheet number - 1
 * Excel file name
 * 
 */

public class ExcelReader {
	
	 public static Iterator<Row> excelRead(String FILE_NAME, int sheetNumber) throws EncryptedDocumentException, InvalidFormatException {

	        try {

	            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
	            Workbook wb = WorkbookFactory.create(excelFile);
	            Sheet datatypeSheet = wb.getSheetAt(sheetNumber);
	            Iterator<Row> iterator = datatypeSheet.iterator();

	            return iterator;
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			return null;
	    }
}
