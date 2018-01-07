package com.locdata.theatres.scraper;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_VueCinemas_LocData {
	
	static Set<String> loc_data = new HashSet<String>();

	public static void main(String args[]){
		
		String root = "http://www.cinema-finder.co.uk/cat/vue-cinemas/";
		final Set<String> toVisit = new HashSet<String>(){{
			add("http://www.cinema-finder.co.uk/cat/vue-cinemas/");
			add("http://www.cinema-finder.co.uk/cat/vue-cinemas/page/2/");
			add("http://www.cinema-finder.co.uk/cat/vue-cinemas/page/3/");
		}};
		
		toVisit.stream().forEach((itr) -> {
			
			Document doc = ScraperLogic.Scraper.fetchHtmlContents(itr);
			CommonUtils.checkDoc(doc,Theatres_VueCinemas_LocData.class);
			
			Elements s = doc.select("div#main");
			
			s.forEach((a) -> {
				
				Elements t = a.select(".category-items.clearfix");
				
				t.parallelStream().forEach((theatrelist) -> {
					Elements k = theatrelist.select("ul li h3 > a");
					
					for(Element q : k){
							System.out.println(q.attr("href"));
							loc_data.add(q.attr("href"));
					}
				});
			});
		});
		
		loc_data.forEach((action) ->
		{
			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);
			CommonUtils.checkDoc(document,Theatres_VueCinemas_LocData.class);
			Elements t = document.body().select("div#main");
			
			t.forEach((theatrelist) -> {
				
				Elements k = theatrelist.select("div#content");
				
				for(Element q : k){
					 
					     Element inner = q.select(".item-address").tagName("dd").first();
						 System.out.println(" Address ------> " +inner.text());
						 //System.out.println(" Phone number ------- > " + q.select(".item-address").tagName("dd").get(1));
				}
				});
		});
	}
}