package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_MovieTavern_LocData {
	
	public static Set<GeoCodeEntityCarrierToExcelWriter> tavernKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException, InterruptedException {
		
		String root = "https://www.movietavern.com";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.movietavern.com/locations/");

		CommonUtils.checkDoc(doc,Theatres_MovieTavern_LocData.class);

		Elements e = doc.select("div#main-content");
		
		e.forEach((a) -> {
				
			for(Element peter : a.select(".directory-list.cf")){
				
				for(Element x : peter.select(".bottom")){
					System.out.println("Website ---> " + root.concat(x.select("a").attr("href")));
					String raw;
					if(x.select("a").attr("href").startsWith("https://www.movietavern.com")){
						raw = x.select("a").attr("href");
					}
					
					raw = root.concat( (x.select("a").attr("href")));
					
					final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();
					
					String app = raw.split("locations/")[1];
					String storeName = app.split("/")[0];
					g.setStorename(storeName);
					
					//EXTON 110 Bartlett Avenue Exton, PA 19341 Main: (484) 870-5875
					
					String rawtext = x.tagName("br").text();
					
					Pattern pattern = Pattern.compile("\\(\\d{3}\\)\\s\\d{3}-\\d{4}");
					Matcher matcher = pattern.matcher(rawtext);

					if (matcher.find()) {
							System.out.println(" Phone number ----------> " + matcher.group(0));
							g.setPhoneNumber(matcher.group(0));
							System.out.println(" Address =----> " + rawtext.substring(0,rawtext.indexOf(matcher.group(0))));
							g.setAddress(rawtext.substring(0,rawtext.indexOf(matcher.group(0))));
							
							try {
								new GeoCodingApi(g.getAddress(), g);
							} catch (Exception e1) {
							   System.out.println(e1.getMessage());
							}
					}
					tavernKeyDataSet.add(g);
				}
			}
			});
		SheetLocalWriter.writeXLSXFile("TavernTheatre.xlsx",tavernKeyDataSet);
}
}
