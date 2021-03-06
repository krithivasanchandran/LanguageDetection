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

public class Theatres_CinemarkTheatres_LocData {
	
	static List<String> theatreUrls = new ArrayList<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> cinemarkKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();
	final static List<String> storeName = new ArrayList<String>();

	public static void main(String args[]) throws IOException, InterruptedException {
	
			Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.fandango.com/movie-theaters/cinemark");
	
			CommonUtils.checkDoc(doc,Theatres_CinemarkTheatres_LocData.class);

			Elements e = doc.select("div#GlobalBody_singleChain");
			
			e.forEach((a) -> {
				Elements r = doc.select(".theatersname");
				
				r.forEach((link) -> {
					
					Elements url = link.select(".theaterlink");
					
					url.forEach((m) -> {
						System.out.println(m.text());
						storeName.add(m.text());
						System.out.println(" ----> " + m.select("a[href]").attr("href"));
						theatreUrls.add(m.select("a[href]").attr("href"));
					});
				});
			});
			
			if(theatreUrls.size() > 0){
				
				theatreUrls.forEach((root) -> {
				
					Document document = ScraperLogic.Scraper.fetchHtmlContents(root);
					
					CommonUtils.checkDoc(document,Theatres_CinemarkTheatres_LocData.class);
					
					final GeoCodeEntityCarrierToExcelWriter masterData = new GeoCodeEntityCarrierToExcelWriter();
					masterData.setStorename(storeName.iterator().hasNext() ? storeName.iterator().next() : "empty");
						Elements r = document.select(".tdp.js-tdp");
						
						r.forEach((link) -> {
							
							
							Elements url = link.select(".subnav__link-item.subnav__link-item--address");
							
							url.forEach((m) -> {
								masterData.setPhoneNumber(link.select(".subnav__link-item").last().text());
								try {
									new GeoCodingApi(m.text(),masterData);
								} catch (Exception e1) {
									System.out.println(" Exception Printed !!! ");
								}

								System.out.println(m.text());
								System.out.println(" Phone number ---> " + link.select(".subnav__link-item").last().text());
							});
						});
						cinemarkKeyDataSet.add(masterData);
					});
			}
			  SheetLocalWriter.writeXLSXFile("CinemarkTheatres.xlsx",cinemarkKeyDataSet);

	}
}	