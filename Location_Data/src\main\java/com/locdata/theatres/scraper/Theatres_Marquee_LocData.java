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

public class Theatres_Marquee_LocData {

	static List<String> theatreUrls = new ArrayList<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> marqueeDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();
	
	public static void main(String args[]) throws IOException, InterruptedException {
		
		String root = "http://www.marqueecinemas.com/";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.marqueecinemas.com/locations");

		CommonUtils.checkDoc(doc,Theatres_Marquee_LocData.class);

		Elements e = doc.select("div#content");
		
		e.forEach((a) -> {
		
			Elements r = a.select("a");
			
			r.forEach((link) -> {
				
				System.out.println("Website ---> " + root.concat(link.attr("href")));
				theatreUrls.add(root.concat(link.attr("href")));
			});
		});
		
		theatreUrls.stream().forEach((action) -> {

			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

			CommonUtils.checkDoc(document,Theatres_Marquee_LocData.class);
			
			final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();
				
			String app = action.split("location/")[1];
			int storeNumber = Integer.parseInt(app.split("/")[0]);
			String storeName = app.split("/")[1];
			g.setStorename(storeName);
			g.setStorenumber(storeNumber);

			Elements p = document.select("div#theater_info");
			
			p.forEach((a) -> {
				
				Elements r = a.select(".address");
				
				String address = r.text().split("Movieline:")[0];
				String phone  = r.text().split("Movieline:")[1];
				g.setAddress(address);
				g.setPhoneNumber(phone);
				
				try {
					new GeoCodingApi(g.getAddress(), g);
				} catch (Exception e1) {
				   System.out.println(e1.getMessage());
				}
			});
			marqueeDataSet.add(g);
		});
		SheetLocalWriter.writeXLSXFile("marqueeTheatre.xlsx",marqueeDataSet);
}
}