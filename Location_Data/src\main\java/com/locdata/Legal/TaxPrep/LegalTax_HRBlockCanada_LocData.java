package com.locdata.Legal.TaxPrep;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.locdata.ajax.request.response.builder.AjaxResponseBuilder;
import com.locdata.ingest.excel.ExcelReader;

//H & R Block Canada

// API - https://www.hrblock.ca/wp-admin/admin-ajax.php?lang=en&action=store_search&lat=53.52200970000001&lng=-113.52878670000001&max_results=1000&radius=200

public class LegalTax_HRBlockCanada_LocData {
	
	// All Postal Zip Codes of Canada  ca_postal_codes.xls

	 private static final String FILE_NAME = "./temp/ca_postal_codes.xls";
	 private static Set<HRCanadaEntity> hrDataSet = new HashSet<HRCanadaEntity>();
	 private volatile static int headcounter = 0;
	 
	 public static void main(String args[]) throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException{
		 
		 int sheetNumber = 0;
		 
		 Iterator<Row> iterator = ExcelReader.excelRead(FILE_NAME, sheetNumber);
		 
		 if(iterator == null) System.exit(0);
		 
		 while (iterator.hasNext()) {

	         Row currentRow = iterator.next();
	         Iterator<Cell> cellIterator = currentRow.iterator();
	         double latitude = 0;
	         double longitude = 0;
	         while (cellIterator.hasNext()) {

	             Cell currentCell = cellIterator.next();
	             
	             if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
	            	 
	            	 if(currentCell.getColumnIndex() == 3){
	            		// System.out.println(" latitude ---> " + currentCell.getNumericCellValue());
	            		 latitude = currentCell.getNumericCellValue();
	            	 }
	            	 if(currentCell.getColumnIndex() == 4){
	            		// System.out.println(" longitude -------> "+ currentCell.getNumericCellValue());
	            		 longitude = currentCell.getNumericCellValue();
	            	 }
	             }
	         }
	         if(latitude != 0.0 && longitude != 0.0){
	        	 
	        	 String sURL = "https://www.hrblock.ca/wp-admin/admin-ajax.php?lang=en&action=store_search&lat="+latitude+"&lng="+longitude+"&max_results=100&radius=200";
		         System.out.println(" URL  " + sURL);
		         HttpURLConnection requestBuilder = AjaxResponseBuilder.generateRequest(sURL, 10000);
		         
		         if(requestBuilder != null) {
		        	 requestBuilder.connect();
		        	 if(requestBuilder.getContent() != null){
			        	  JsonParser jp = new JsonParser(); //from gson
					         JsonElement root = jp.parse(new InputStreamReader((InputStream) requestBuilder.getContent())); //Convert the input stream to a json element

					         if (root instanceof JsonArray) {

					        	    JsonArray  jarray =  root.getAsJsonArray();
					        	    HRCanadaEntity hr = null;
					        	    for (Object o : jarray) {
					        	    	
					        	        JsonObject jsonLineItem = (JsonObject)o;
					        	        System.out.println(jsonLineItem.toString());
					        	        hr = new HRCanadaEntity();
					        	        hr.setAddress(jsonLineItem.get("address").toString());
					        	        hr.setStoreId(jsonLineItem.get("id").toString());
					        	        hr.setPhone(jsonLineItem.get("phone").toString());
					        	        hr.setCity(jsonLineItem.get("city").toString());
					        	        hrDataSet.add(hr);
					        	    }
					        	 }
					         headcounter++;
					         if( headcounter == 100) break;
			         }
		         }
	         }
		 }
		 System.out.println(" Size of the set ====> " + hrDataSet.size());
		 
		 hrDataSet.parallelStream().forEach((action) -> {
			 System.out.println(action.toString());
		 });
	 }
}