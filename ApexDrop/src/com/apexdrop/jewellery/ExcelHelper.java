package com.apexdrop.jewellery;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelHelper {
	
	private static final String rootFilePath = "/Users/krichandran/Desktop/MasterData/Apex/";
	public static void writeXLSXFile(String fileName,List<BusinessCard> masterData) throws IOException {
		
		String excelFileName = rootFilePath.concat(fileName);//name of excel file
		String sheetName = "Data.com";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName);
		
			XSSFRow row_1 = sheet.createRow(0);
			for (int c=0;c < 10; c++ )
			{
				XSSFCell cell = row_1.createCell(c);
				
				switch(c) {
				case 0:
					cell.setCellValue("Person Name");
				
				case 1:
					cell.setCellValue("Designation");
					break;
					
				case 2: 
					cell.setCellValue("Business Website");
					break;
				
				case 3:
					cell.setCellValue("Business Name");
					break;
				
				case 4:
					cell.setCellValue("Business Address");
					break;
					
				case 5:
					cell.setCellValue("Company Headquarters Address");
					break;
				
				case 6: 
					cell.setCellValue("Company phone");
					break;
				
				case 7: 
					cell.setCellValue("Industries");
					break;
				
				case 8:
					cell.setCellValue("Employees");
					break;
				
				case 9:
					cell.setCellValue("Company Revenue");
					break;
			}
			}
			
			
		//iterating r number of rows
		for (int r=1;r < masterData.size()+1; r++ )
		{
			XSSFRow row = sheet.createRow(r);

			//iterating c number of columns
			for (int c=0;c < 10; c++ )
			{
				
				XSSFCell cell = row.createCell(c);
				
				switch(c) {
				
					case 0: 
						cell.setCellValue(masterData.get(r-1).getPersonName());
						break;
						
					case 1: 
						cell.setCellValue(masterData.get(r-1).getDesignation());
						break;
					
					case 2:
						cell.setCellValue(masterData.get(r-1).getWebsite());
						break;
					
					case 3:
						cell.setCellValue(masterData.get(r-1).getBusinessName());
						break;
					
					case 4: 
						cell.setCellValue(masterData.get(r-1).getAddress());
						break;
					
					case 5:
						cell.setCellValue(masterData.get(r-1).getHeadquartersAddress());
						break;
					
					case 6:
						cell.setCellValue(masterData.get(r-1).getPhone());
						break;
					
					case 7: 
						cell.setCellValue(masterData.get(r-1).getIndustries());
						break;
					
					case 8: 
						cell.setCellValue(masterData.get(r-1).getEmployees());
						break;
					
					case 9:
						cell.setCellValue(masterData.get(r-1).getCompanyrevenue());
						break;
				}
			}
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
}
