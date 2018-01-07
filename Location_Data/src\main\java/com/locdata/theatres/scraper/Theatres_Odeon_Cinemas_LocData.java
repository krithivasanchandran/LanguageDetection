package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Odeon_Cinemas_LocData {
	
	static List<String> theatreUrls = new ArrayList<String>();
	
	public static void main(String args[]) throws IOException, InterruptedException {
		
		String root = "http://www.odeon.co.uk/cinemas/";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.odeon.co.uk/cinemas/");

		CommonUtils.checkDoc(doc, Theatres_Odeon_Cinemas_LocData.class);

		Elements e = doc.select("div#wrapper");
		
		e.forEach((a) -> {
				
			for(Element peter : a.select("select#your-cinema")){
				
				for(Element x : peter.getElementsByTag("option")){
					if(!(x.text().trim().equalsIgnoreCase("Select a cinema") || x.text().trim().equalsIgnoreCase("Select your cinema"))){
						
							String str = x.text().trim();
							
							if(str.contains("Dudley")){
								
								System.out.println(" String match successfull ========> ");
								str = "Dudley";
								System.out.println("replaced value String match successfull ========> " + str);

							}
							
							str = str.replace("(New)","");
							str = str.replace("- ODEON Luxe","");
							str = str.replace(" - The Lounge","");
							str = str.replace(" ","");
							
							System.out.println("Website ---> " + str);
							System.out.println(" Value ----> " + x.attr("value"));
							theatreUrls.add(root.concat(str+"/"+x.attr("value")));
						}
				}
			}
			});
		
		theatreUrls.forEach((action) -> {
			System.out.println(" ===========> " + action);
			
			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

			CommonUtils.checkDoc(document, Theatres_Odeon_Cinemas_LocData.class);
			
			for(Element element : document.getElementsByTag("div")){
				String el = element.attr("id");
				
				if(el.contains("gethere")){
						System.out.println("Address ---> " + element.select(".description").text());
				}
			}
});
}}