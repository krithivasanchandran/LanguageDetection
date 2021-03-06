package com.langdetect.compvision.screenshot;


import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.internal.LightWeightWidget;

/*
 * Training Data_1 - 50 English Version websites Screenshot
 * Training Data_2 - 50 Other Language Version websites Screenshot
 * 
 * Seed Urls List - 20,000 websites
 * 
 */

public class ScreenshotService {

	public static void main(String[] args) throws Exception {
		
		final String FILE_NAME = "/Users/krichandran/Desktop/20k_websites_for_evaluation.xlsx";
  		 
  		int sheetNumber = 0;
  		 
  		Iterator<Row> iterator = ScreenshotService.excelRead(FILE_NAME, sheetNumber);
  		
  		Set<String> workingUrls = new LinkedHashSet<String>();
  		 
  		 
  		 while (iterator.hasNext()) {

  	         Row currentRow = iterator.next();
  	         Iterator<Cell> cellIterator = currentRow.iterator();
  	         String validUrl = null;
  	         if(workingUrls.size() == 201) break;
  	         while (cellIterator.hasNext()) {

  	             Cell currentCell = cellIterator.next();
  	             
  	             if (currentCell.getCellTypeEnum() == CellType.STRING) {
  	            	 
  	            	 if(currentCell.getColumnIndex() == 0){
  	            		String p = currentCell.getStringCellValue();
  	            		if(!p.endsWith(".com")){
  	            			validUrl = p;
  	            		}
  	            	 }
  	            	 
  	            	if(currentCell.getColumnIndex() == 3){
  	            		if(validUrl!= null && currentCell.getStringCellValue().equalsIgnoreCase("OK") && workingUrls.size() < 201){
  	            			workingUrls.add(validUrl);
  	            		}else break;
  	            	 }
  	             }
  	         }
  		 }
  		 
  		 System.out.println(" Size of the list is ::::: :::: " + workingUrls.size());
  		 short c = 1;
  		 for(final String s : workingUrls){
  	  		 System.out.println(" URls in the list ----->" + s);
  	  		 
  	  		 if(c != 37 || c != 38 ){
  	  			 
  	  			 if(c < 90 || c > 110){
  	  		 
		        // #1 Create Browser instance
		        Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
		        BrowserView view = new BrowserView(browser);

		        // #2 Set the required view size
		        browser.setSize(1280, 1024);

		        // Wait until Chromium resizes view
		        Thread.sleep(500);

		        // #3 Load web page and wait until web page is loaded completely
		        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
		            @Override
		            public void invoke(Browser browser) {
		                browser.loadURL(s);
		            }
		        });

		        // Wait until Chromium renders web page content
		        Thread.sleep(1000);

		        // #4 Get java.awt.Image of the loaded web page.
		        LightWeightWidget lightWeightWidget = (LightWeightWidget) view.getComponent(0);
		        Image image = lightWeightWidget.getImage();
		        ImageIO.write((RenderedImage) image, "PNG", new File("/Users/krichandran/Desktop/DatasetLanguageDetection/labeldata"+c+".png"));

		        // Dispose Browser instance
		        browser.dispose();
  	  		 }
  	  		 }
		        c++;
		        
  		 }
	  }
	
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
