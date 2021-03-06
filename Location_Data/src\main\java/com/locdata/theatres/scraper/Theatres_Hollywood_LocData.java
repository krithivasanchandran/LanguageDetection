package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * Hollywood theatres is being acquired by regal cinemas . Some of the theatres is been shutdown
 * 
 * Important !!! Few theatres are closed --> check for closed text in the URL 
 */

public class Theatres_Hollywood_LocData {
	
	static List<String> theatreUrls = new ArrayList<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> hollywoodKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException, InterruptedException {
	
			Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.fandango.com/movie-theaters/regal");
	
			CommonUtils.checkDoc(doc,Theatres_Hollywood_LocData.class);

			Elements e = doc.select("div#GlobalBody_singleChain");
			
			e.forEach((a) -> {
				Elements r = doc.select(".theatersname");
				
				r.forEach((link) -> {
					
					Elements url = link.select(".theaterlink");
					
					url.forEach((m) -> {
						if(m.select("a[href]").text().contains("Hollywood")){
							
							System.out.println("Text====>  " + m.select("a[href]").text());
							System.out.println(" ----> " + m.select("a[href]").attr("href"));
							theatreUrls.add(m.select("a[href]").attr("href"));
						}
					});
				});
			});
			
			if(theatreUrls.size() > 0){
				theatreUrls.forEach((root) -> {
					
					Document document = ScraperLogic.Scraper.fetchHtmlContents(root);
					
					CommonUtils.checkDoc(document,Theatres_Hollywood_LocData.class);
					
					final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();

					String storeName = root.substring(root.indexOf(".com/",0)+".com/".length(), root.indexOf("_"));
				     g.setStorename(storeName);
				     
						Elements r = document.select(".tdp.js-tdp");
						
						r.forEach((link) -> {
							
							Elements url = link.select(".subnav__link-item.subnav__link-item--address");
							
							url.forEach((m) -> {
								System.out.println(m.text());
								g.setAddress(m.text());
								System.out.println(" Phone number ---> " + link.select(".subnav__link-item").last().text());
								g.setPhoneNumber(link.select(".subnav__link-item").last().text());
							});
						});
						try {
							new GeoCodingApi(g.getAddress(), g);
							hollywoodKeyDataSet.add(g);
						} catch (Exception e1) {
							System.out.println(e1.getMessage());
						}
					});
				SheetLocalWriter.writeXLSXFile("HollywoodTheatre.xlsx",hollywoodKeyDataSet);
			}
	}
}