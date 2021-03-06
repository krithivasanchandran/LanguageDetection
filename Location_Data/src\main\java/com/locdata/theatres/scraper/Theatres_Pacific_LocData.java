package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * Small Theatre Locations (6) can be manually crawled
 * 
 * Manually try to copy paste the phone no. into the excel
 */

public class Theatres_Pacific_LocData {
	
	public static Set<GeoCodeEntityCarrierToExcelWriter> pacificKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();
	
public static void main(String args[]) throws IOException, InterruptedException {
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.pacifictheatres.com/all-locations/");

		CommonUtils.checkDoc(doc,Theatres_Pacific_LocData.class);

		Elements e = doc.select(".entry-content");
		StringBuffer b = new StringBuffer();
		
		e.forEach((a) -> {
			a.getElementsByTag("table").stream().forEach((t) -> {
				t.getElementsByTag("tr").forEach((p) -> {
					p.getElementsByTag("td").forEach((m) -> {
							b.append(m.tagName("div").text()+"$$$");
					});
				});
			});
		});
		
		//System.out.println(b.toString());
		String r = b.toString();
		String[] p = r.split("[$$$]");
		
		for(String t : p){
			
			final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();

			String address = t.split("Ticketing Info:")[0];
			g.setAddress(address);
			
			if(!g.getAddress().isEmpty()){
				try {
					new GeoCodingApi(g.getAddress(), g);
				} catch (Exception e1) {
				   System.out.println(e1.getMessage());
				}	
				pacificKeyDataSet.add(g);
			}
        }
	     SheetLocalWriter.writeXLSXFile("pacificTheatre.xlsx",pacificKeyDataSet);
}
}