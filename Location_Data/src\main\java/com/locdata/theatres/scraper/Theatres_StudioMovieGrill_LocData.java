package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_StudioMovieGrill_LocData {

public static void main(String args[]) throws IOException, InterruptedException {
		
		Set<String> theatreurls = new HashSet<String>();
		
		String root = "https://www.studiomoviegrill.com/";
	
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.studiomoviegrill.com/Locations");

		CommonUtils.checkDoc(doc, Theatres_Starplex_LocData.class);

		Elements e = doc.select("ul#location-list");
		
		e.forEach((a) -> {
				
			for(Element peter : a.select(".location-box")){
				
				for(Element x : peter.select("h3 a")){
					System.out.println("Website ---> " + root.concat(x.attr("href")));
					theatreurls.add(root.concat(x.attr("href")));
				}
				

				for(Element x : peter.select(".info > a")){
					System.out.println("Address and Phone ---> " + x.text());
				}
			}
			});
		}
}
