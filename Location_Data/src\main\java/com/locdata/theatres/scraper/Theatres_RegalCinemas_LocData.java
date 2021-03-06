package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * Total Theatres - 570 theatres - Including Edwards , Regal and UA entertainment
 * 
 */

public class Theatres_RegalCinemas_LocData {
	
	static List<String> theatreUrls = new ArrayList<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> regalCinemasKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();
	
	public static void main(String args[]) throws IOException, InterruptedException {
		
		String root = "https://www.regmovies.com";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.regmovies.com/theater-list");

		CommonUtils.checkDoc(doc, Theatres_RegalCinemas_LocData.class);

		Elements e = doc.select(".content-container.venue-content-container");
		
		e.forEach((a) -> {
				
			for(Element peter : a.select(".grid.grid-2-col.grid-1-col-xs")){
				
				for(Element x : peter.select(".list-unstyled.state-venue-list")){
						
					for(Element inner : x.select("ul li a")){
						System.out.println("Website ---> " + inner.attr("href"));
						System.out.println(" Value ----> " + inner.text());
						theatreUrls.add(root.concat(inner.attr("href")));
					}
				}
			}
			});
		
	theatreUrls.forEach((action) -> {
		 
		final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();

		    if(action.contains("theaters/")){
		    	String raw = action.split("theaters/")[1];
		    	System.out.println("Store name ::::  "+ raw.split("/")[0]);
		    	System.out.println("Store number ::::  "+ raw.split("/")[1]);
		    	g.setStorename(raw.split("/")[0]);
		    	g.setStorenumber(Integer.parseInt(raw.split("/")[1]));
		    }
		
			System.out.println(" ===========> " + action);
			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);
			CommonUtils.checkDoc(document, Theatres_RegalCinemas_LocData.class);
			Elements address = document.body().select(".info-cell .address");
			Elements phone = document.body().select(".info-cell .phone");
			
			System.out.println(address.text());
			String addr = address.text().replace("view on google maps","");
			g.setAddress(addr);
			g.setPhoneNumber(phone.text());
			System.out.println(" Phone --->  " + phone.text());
			try {
				new GeoCodingApi(g.getAddress(), g);
			} catch (Exception e1) {
			   System.out.println(e1.getMessage());
			}
			regalCinemasKeyDataSet.add(g);
});
	SheetLocalWriter.writeXLSXFile("regalCinemasTheatre.xlsx",regalCinemasKeyDataSet);
}
}