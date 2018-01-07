package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * Filter out - Odeon cinemas from the Wikipedia Links
 */

public class Theatres_CineplexEntertainment_LocData {
	
	static Set<String> loc_data = new HashSet<String>();
	
	public static void main(String args[]) throws IOException, InterruptedException {

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://en.wikipedia.org/wiki/List_of_Cineplex_Entertainment_movie_theatres");

		CommonUtils.checkDoc(doc,Theatres_CineplexEntertainment_LocData.class);
		
		Element content = doc.getElementById("mw-content-text");
		
		Elements s = content.getElementsByTag("table");
		
		s.forEach((a) -> {
			
			Elements t = a.getElementsByTag("tbody");
			
			t.parallelStream().forEach((theatrelist) -> {
				Elements k = theatrelist.getElementsByTag("tr");
				
				for(Element q : k){
					if(!q.getElementsByTag("td").text().contains("[edit]") && !q.getElementsByTag("td").text().contains("Odeon")){
						
						if(q.select("td").size() > 0){
							
							loc_data.add(q.select("td").first().text());
						}
					}
				}
			});
		});
		
		loc_data.forEach((action) ->
		{
			Document document = ScraperLogic.Scraper.fetchHtmlContents("https://www.cineplex.com/Theatres/TheatreListings?LocationURL="+action+"&Range=50");

			CommonUtils.checkDoc(document,Theatres_CineplexEntertainment_LocData.class);
		
			Elements t = document.body().select("div#Wrapper");
			
			t.forEach((theatrelist) -> {
				
				Elements k = theatrelist.select(".showtime-theatre");
				
				for(Element q : k){
								
								System.out.println(" Address ------> " + q.select(".theatre-address").text());
								System.out.println("  $$$$ "  + q.getElementsByAttributeValue("itemprop", "telephone").text());
					}
				});
		});
	}
}