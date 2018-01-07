package com.locdata.theatres.scraper;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Edwards_LocData {
	
	static Set<String> websiteUrls = new HashSet<String>();
	
public static void main(String args[]) {
		
		String root = "https://www.regmovies.com";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.regmovies.com/theaters");
		CommonUtils.checkDoc(doc, Theatres_Edwards_LocData.class);

		//Selecting the fourth table
		Elements t = doc.select("main.content-container");

		for(Element p : t){
			
			Elements theatreUrl = p.select(".grid.grid-2-col.grid-1-col-xs");
			
			theatreUrl.forEach((action) -> {
				Elements tags = action.select(".list-unstyled.state-venue-list a");
				
				for(Element r : tags){
					if(r.text().contains("Edwards")){
						websiteUrls.add(root.concat(r.attr("href")));
					}
				}
			});
		}
		
		websiteUrls.forEach((tbag) -> {
			
			Document document = ScraperLogic.Scraper.fetchHtmlContents(tbag);
			CommonUtils.checkDoc(document, Theatres_Edwards_LocData.class);
			Elements address = document.body().select(".info-cell .address");
			Elements phone = document.body().select(".info-cell .phone");
			System.out.println(address.text());
			System.out.println(" Phone --->  " + phone.text());
			
		});
	}	
}