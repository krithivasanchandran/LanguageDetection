package com.locdata.theatres.scraper;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Cinepolis_LocData {
	
	static Set<String> loc_data = new HashSet<String>();
	
	public static void main(String args[]){
		
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
			
			Elements t = document.body().select("div#page_wrapper");
			
			t.forEach((theatrelist) -> {
				
				Elements k = theatrelist.select("div#Body_T535F2F93004_Col00");
				
				for(Element q : k){
					 
					     Element inner = q.select(".section_header.overlay").tagName("a").first();
					     String address = inner.text();
					     System.out.println(" Address ------> " +address);
				}
				});
		});
		
		// loc_data.forEach((action) -> System.out.println(action));
	}
}
