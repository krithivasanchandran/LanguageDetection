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

public class Theatres_Cinepolis_LocData {
	
	static Set<String> loc_data = new HashSet<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> cinepolisKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException{
		
		String root = "https://www.cinepolisusa.com";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.cinepolisusa.com/locations");
		CommonUtils.checkDoc(doc,Theatres_Cinepolis_LocData.class);
		
		Elements s = doc.select("section.light_section.vcenter");
		
		s.forEach((a) -> {
			
			Elements t = a.select("div.container");
			
			t.parallelStream().forEach((theatrelist) -> {
				Elements k = theatrelist.select("h2 > a");
				
				for(Element q : k){
						loc_data.add(root.concat(q.attr("href")));
				}
			});
		});
		
		loc_data.forEach((action) ->
		{
			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

			CommonUtils.checkDoc(document,Theatres_Cinepolis_LocData.class);
			
			final GeoCodeEntityCarrierToExcelWriter cinePolisMasterData = new GeoCodeEntityCarrierToExcelWriter();
			cinePolisMasterData.setStorename(action);
			
			Elements t = document.body().select("div#page_wrapper");
			
			t.forEach((theatrelist) -> {
				
				Elements k = theatrelist.select("div#Body_T535F2F93004_Col00");
				
				for(Element q : k){
					 
					     Elements inner = q.select(".section_header.overlay");
					     inner.forEach((t1) -> {
					    		String address = t1.getElementsByTag("p").tagName("a").text(); 
					    		Pattern pattern = Pattern.compile("\\(\\d{3}\\)\\s\\d{3}-\\d{4}");
								Matcher matcher = pattern.matcher(address);
								if (matcher.find()) {
									int len = matcher.group(0).length();
									cinePolisMasterData.setPhoneNumber(matcher.group(0));
									address = address.substring(0,len);
									try {
										new GeoCodingApi(address,cinePolisMasterData);
									} catch (Exception e) {
									 System.out.println("Exception Thrown !!! ");
									}
								}
						     System.out.println(" Address ------> " +address);
					     });
				}
				});
			cinepolisKeyDataSet.add(cinePolisMasterData);
		});
		
		  SheetLocalWriter.writeXLSXFile("CinepolisTheatres.xlsx",cinepolisKeyDataSet);
	}
}
