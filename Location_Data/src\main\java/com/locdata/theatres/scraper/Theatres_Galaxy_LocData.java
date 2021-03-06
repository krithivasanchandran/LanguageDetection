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
 * 
 * Not able to scrape email id :  Contact Email ID ----->  <a href="/cdn-cgi/l/email-protection#eb82858d8486848599848eab8c8a878a93929f838e8a9f998e98c5888486">href</a>
 *
 *Default Contact No : 888-407-9874
 */

public class Theatres_Galaxy_LocData {
	
	static List<String> theatreUrls = new ArrayList<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> galaxyKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException, InterruptedException {

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.galaxytheatres.com/Browsing/Cinemas");

		CommonUtils.checkDoc(doc, Theatres_Galaxy_LocData.class);

		Elements e = doc.select("article#cinema-list");
		
		e.forEach((a) -> {
			
			Elements address = a.select(".contact-address");
			
			for(int i=0; i<address.size(); i++){
				
				Element m = address.get(i);
				final GeoCodeEntityCarrierToExcelWriter geo = new GeoCodeEntityCarrierToExcelWriter();
				String storeName = a.select(".cinema-title").text();
				String[] parser = storeName.split(",");
				geo.setStorename(parser[i]);
				System.out.println(" Store name -----------------> " + parser[m.siblingIndex()]);
				System.out.println(" Address ----->  " + m.text());
				geo.setAddress(m.text());
				try {
					new GeoCodingApi(geo.getAddress(), geo);
					geo.setPhoneNumber("888-407-9874");
					galaxyKeyDataSet.add(geo);
				} catch (Exception e1) {
					System.out.println(" Exception thrown continue ");
				}
			}
		});
		SheetLocalWriter.writeXLSXFile("GalaxyTheatre.xlsx",galaxyKeyDataSet);
	}
}
