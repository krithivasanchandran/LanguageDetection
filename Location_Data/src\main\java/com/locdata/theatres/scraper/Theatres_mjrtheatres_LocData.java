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

public class Theatres_mjrtheatres_LocData {
	
	public static Set<GeoCodeEntityCarrierToExcelWriter> mjrKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException, InterruptedException {
		
		String root = "http://www.metrotheatres.com";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.mjrtheatres.com/locations");

		CommonUtils.checkDoc(doc,Theatres_mjrtheatres_LocData.class);

		Elements e = doc.select("main#main-wrapper");
		
		e.forEach((a) -> {
				
			for(Element peter : a.select("a")){
				System.out.println("Website ---> " + root.concat(peter.attr("href")));
				String p =root.concat(peter.attr("href"));
				
				final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();
				
				String app = p.split("location/")[1];
				int storeNumber = Integer.parseInt(app.split("/")[0]);
				String storeName = app.split("/")[1];
				g.setStorename(storeName);
				g.setStorenumber(storeNumber);
				
				System.out.println("Address " + peter.getElementsByTag("figcaption").text());
				String rawtext = peter.getElementsByTag("figcaption").text();
				
				Pattern pattern = Pattern.compile("\\(\\d{3}\\)\\s\\d{3}-\\d{4}");
				Matcher matcher = pattern.matcher(rawtext);

				if (matcher.find()) {
						System.out.println(" Phone number ----------> " + matcher.group(0));
						g.setPhoneNumber(matcher.group(0));
						System.out.println(" Address =----> " + rawtext.split(matcher.group(0))[0]);
						g.setAddress(rawtext.split(matcher.group(0))[0]);
						
						try {
							new GeoCodingApi(g.getAddress(), g);
						} catch (Exception e1) {
						   System.out.println(e1.getMessage());
						}
				}
				mjrKeyDataSet.add(g);
			}
			
			});
		SheetLocalWriter.writeXLSXFile("mjrTheatre.xlsx",mjrKeyDataSet);
}
}