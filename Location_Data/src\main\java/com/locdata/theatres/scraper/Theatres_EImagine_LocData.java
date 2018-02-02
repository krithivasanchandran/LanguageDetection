package com.locdata.theatres.scraper;

import java.io.IOException;
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

/*  Not scraped - logically
 * http://www.emagine-entertainment.com/locations/emagine-delano/
 * 
 * Address:     Physical Address: 4423 US Highway 12 Delano, MN 55328
				Mailing Address: 803 Babcock Blvd. West Delano MN, 55328
				Emagine Delano is located on Babcock Rd. at 4423 US Highway 12 Delano, MN 55328.
 * 
 */
public class Theatres_EImagine_LocData {
	
	static Set<String> websiteUrls = new HashSet<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> eImagineKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();
	
    static final Set<String> toVisit = new HashSet<String>(){{
		add("http://www.emagine-entertainment.com/michigan/");
		add("http://www.emagine-entertainment.com/minnesota/");
		add("http://www.emagine-entertainment.com/illinois/");
	}};
	
	public static void main(String args[]) throws IOException {
			
			toVisit.parallelStream().forEach((action) -> {
				
				Document doc = ScraperLogic.Scraper.fetchHtmlContents(action);
				CommonUtils.checkDoc(doc, Theatres_EImagine_LocData.class);

				Elements t = doc.body().select("div#theatre-listings");
				
				for(Element p : t){
					
						Elements tags = p.getElementsByTag("a");
						
						for(Element r : tags){
						    	System.out.println(r.attr("href"));
								websiteUrls.add(r.attr("href"));
						}
				}
			});
			
		websiteUrls.forEach((q) -> {
			
			Document doc = ScraperLogic.Scraper.fetchHtmlContents(q);
			CommonUtils.checkDoc(doc, Theatres_EImagine_LocData.class);
			final GeoCodeEntityCarrierToExcelWriter obj = new GeoCodeEntityCarrierToExcelWriter();
			Elements t = doc.body().select("div#theatre-single-content-box");
			
			for(Element p : t){
				
					Element phone = p.getElementsByTag("p").first();
					System.out.println(" phone ----> " + phone.text());
					obj.setPhoneNumber(phone.text());
					Element address = p.getElementsByTag("p").get(1);
					System.out.println(" address ======> "+ address.text());
					obj.setAddress(address.text());
			}
			try {
				new GeoCodingApi(obj.getAddress(), obj);
			} catch (Exception e) {
				System.out.println(" GeoCoding API !!!!! ");
			}
			eImagineKeyDataSet.add(obj);
		});
		SheetLocalWriter.writeXLSXFile("EImagineTheatre.xlsx",eImagineKeyDataSet);
   }
}
