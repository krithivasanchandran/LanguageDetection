package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Harkins_LocData {
	
	static List<String> theatreUrls = new ArrayList<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> harkinsKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException {

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.harkins.com/locations");
		
		CommonUtils.checkDoc(doc,Theatres_Harkins_LocData.class);

		doc.select(".theatre.cols").stream().forEach((h) -> {
			
			final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();

			String storename = h.select(".underlined.tooltip-trigger").text();
			System.out.println(" Storename ----------> " + storename);
			g.setStorename(storename);
			System.out.println(" Address -------------> " + h.select(".address").text());
			g.setAddress(h.select(".address").text());
			System.out.println(" Address -------------> " + h.select(".phone").text());
			g.setPhoneNumber(h.select(".phone").text());
			
			try{
				new GeoCodingApi(g.getAddress(), g);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			harkinsKeyDataSet.add(g);
		});
		SheetLocalWriter.writeXLSXFile("HarkinsTheatre.xlsx",harkinsKeyDataSet);
	}
}