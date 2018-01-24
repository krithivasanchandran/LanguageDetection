package com.locdata.theatres.scraper;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Edwards_LocData {
	
	static Set<String> websiteUrls = new HashSet<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> edwardsKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

public static void main(String args[]) {
		
		String root = "https://www.regmovies.com";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.regmovies.com/theaters");
		CommonUtils.checkDoc(doc, Theatres_Edwards_LocData.class);

		//Selecting the fourth table
		Elements t = doc.select("main.content-container");

		for(Element p : t){
			
			Elements theatreUrl = p.select(".grid.grid-2-col.grid-1-col-xs");
			
			theatreUrl.forEach((action) -> {
				Elements tags = action.select(".list-unstyled.state-venue-list a");
				
				for(Element r : tags){
					if(r.text().contains("Edwards")){
						websiteUrls.add(root.concat(r.attr("href")));
					}
				}
			});
		}
		
		websiteUrls.forEach((tbag) -> {
			
			Document document = ScraperLogic.Scraper.fetchHtmlContents(tbag);
			CommonUtils.checkDoc(document, Theatres_Edwards_LocData.class);
			Elements address = document.body().select(".info-cell .address");
			
			try {
				String addr = address.text().replace("view on google maps", "");
				GeoCodingApi geo = new GeoCodingApi(addr,edwardsKeyDataSet);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Elements phone = document.body().select(".info-cell .phone");
			System.out.println(address.text());
			System.out.println(" Phone --->  " + phone.text());
			
			
			
		});
		try {
			SheetLocalWriter.writeXLSXFile("EdwardsTheatre.xlsx",edwardsKeyDataSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}