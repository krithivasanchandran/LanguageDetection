package com.locdata.google.api.sheetsWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;

public class SheetLocalWriter {
	
	private static final String rootFilePath = "/Users/krichandran/Desktop/MasterData/Theatres/";
	public static void writeXLSXFile(String fileName,Set<GeoCodeEntityCarrierToExcelWriter> masterData) throws IOException {
		
		String excelFileName = rootFilePath.concat(fileName);//name of excel file
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate localDate = LocalDate.now();
		String sheetName = dtf.format(localDate);//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName);
		List<GeoCodeEntityCarrierToExcelWriter> masterlist = new ArrayList<GeoCodeEntityCarrierToExcelWriter>(masterData);
		for(GeoCodeEntityCarrierToExcelWriter w: masterlist){
			System.out.println("Copied Cell contents are  " + w.getAddress());
			System.out.println(w.getCountry());
			System.out.println("Set size are :::: " + masterlist.size());
		}
		
			XSSFRow row_1 = sheet.createRow(0);
			for (int c=0;c < 14; c++ )
			{
				XSSFCell cell = row_1.createCell(c);
				
				switch(c) {
				case 0:
					cell.setCellValue("Store ID");
				
				case 1:
					cell.setCellValue("Store Name");
					break;
					
				case 2: 
					cell.setCellValue("Address");
					break;
				
				case 3:
					cell.setCellValue("City");
					break;
				
				case 4:
					cell.setCellValue("State");
					break;
				
				case 5: 
					cell.setCellValue("Zip Code");
					break;
				
				case 6:
					cell.setCellValue("Phone Number");
					break;
					
				case 7:
					cell.setCellValue("Latitude");
					break;
				
				case 8:
					cell.setCellValue("Longitude");
					break;
				
				case 9:
					cell.setCellValue("Geo Accuracy");
					break;
				
				case 10:
					cell.setCellValue("Country");
					break;
				
				case 11:
					cell.setCellValue("Country Code");
					break;
			
				case 12:
					cell.setCellValue("County");
					break;
			}
			}
			
		
		//iterating r number of rows
		for (int r=1;r < masterlist.size()+1; r++ )
		{
			XSSFRow row = sheet.createRow(r);

			//iterating c number of columns
			for (int c=0;c < 14; c++ )
			{
				
				XSSFCell cell = row.createCell(c);
				
				switch(c) {
				
					case 0: 
						cell.setCellValue(masterlist.get(r-1).getStorenumber());
						break;
						
					case 1: 
						cell.setCellValue(masterlist.get(r-1).getStorename());
						break;
					
					case 2:
						cell.setCellValue(masterlist.get(r-1).getAddress());
						break;
					
					case 3:
						cell.setCellValue(masterlist.get(r-1).getCity());
						break;
					
					case 4: 
						cell.setCellValue(masterlist.get(r-1).getState());
						break;
					
					case 5:
						cell.setCellValue(masterlist.get(r-1).getZipcode());
						break;
						
					case 6:
						cell.setCellValue(masterlist.get(r-1).getPhoneNumber());
						break;
					
					case 7:
						cell.setCellValue(masterlist.get(r-1).getLatitude());
						break;
					
					case 8:
						cell.setCellValue(masterlist.get(r-1).getLongitude());
						break;
					
					case 9:
						cell.setCellValue(masterlist.get(r-1).getLocationtype());
						break;
					
					case 10:
						cell.setCellValue(masterlist.get(r-1).getCountry());
						break;
				
					case 11:
						cell.setCellValue(masterlist.get(r-1).getCountryCode());
						break;
					
					case 12: 
						cell.setCellValue(masterlist.get(r-1).getCounty());
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
