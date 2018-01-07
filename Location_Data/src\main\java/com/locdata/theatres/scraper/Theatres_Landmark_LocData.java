package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Landmark_LocData {

	static List<String> theatreUrls = new ArrayList<String>();

	public static void main(String args[]) throws IOException, InterruptedException {
	
			Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.landmarktheatres.com/see-all-locations");
	
			CommonUtils.checkDoc(doc,Theatres_Landmark_LocData.class);

			Elements e = doc.select(".gridRow.section.content");
			
			e.forEach((a) -> {
			
				Elements r = doc.select(".gridRow.sectionInner");
				
				r.forEach((link) -> {
					
					Elements url = link.select(".gridCol-l-6.gridCol-m-6.gridCol-s-12");
							
					for(Element t : url){
						for(Element v :  t.select("ul li a")){
							
							System.out.println("Website====>  " + v.text());
							theatreUrls.add(v.text());
						}
					}
				});
			});
			
				Document document = ScraperLogic.Scraper.fetchHtmlContents("https://www.landmarktheatres.com/LocationFindResults?region=");
					CommonUtils.checkDoc(document,Theatres_Landmark_LocData.class);
					
					Elements t = document.select(".gridRow.finder.content");
					
					for(Element b : t){
						
						for(Element v : t.select(".gridRow.finder-results-cinemas")){
							
							System.out.println(" Address ----->" + v.select(".finder-results-cinema-info").text());
							System.out.println(" Website =======> "+ "https://www.landmarktheatres.com" +v.select("a[href]").attr("href"));
						}
					}
	}
}