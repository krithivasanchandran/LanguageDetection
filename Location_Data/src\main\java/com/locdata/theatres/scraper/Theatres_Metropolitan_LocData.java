package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Metropolitan_LocData {
	public static Set<GeoCodeEntityCarrierToExcelWriter> metropolitanDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException, InterruptedException {
		
		String root = "http://www.metrotheatres.com";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.metrotheatres.com/locations");

		CommonUtils.checkDoc(doc,Theatres_Metropolitan_LocData.class);

		Elements e = doc.select("div#locations");
		
		e.forEach((a) -> {
		
			Elements r = a.select(".detail");
			
			r.forEach((link) -> {
				
				System.out.println("Website ---> " + root.concat(link.select("p a").attr("href")));
				String raw = root.concat(link.select("p a").attr("href"));
				
				final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();
				
				String app = raw.split("location/")[1];
				int storeNumber = Integer.parseInt(app.split("/")[0]);
				String storeName = app.split("/")[1];
				g.setStorename(storeName);
				g.setStorenumber(storeNumber);

				Pattern pattern = Pattern.compile("\\(\\d{3}\\)\\s\\d{3}-\\d{4}");
				Matcher matcher = pattern.matcher(link.select(".address").text());

				if (matcher.find()) {
						System.out.println(" Phone number ----------> " + matcher.group(0));
						g.setPhoneNumber(matcher.group(0));
						System.out.println(" Address =----> " + link.select(".address").text().split(matcher.group(0))[0]);
						g.setAddress(link.select(".address").text().substring(0, link.select(".address").text().indexOf(matcher.group(0),0)));
						
						try {
							new GeoCodingApi(g.getAddress(), g);
						} catch (Exception e1) {
						   System.out.println(e1.getMessage());
						}
				}
				metropolitanDataSet.add(g);
			});
		});
		SheetLocalWriter.writeXLSXFile("MetroPolitanTheatre.xlsx",metropolitanDataSet);
}
	
}
