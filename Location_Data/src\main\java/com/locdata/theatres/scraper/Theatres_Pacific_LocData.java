package com.locdata.theatres.scraper;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * Small Theatre Locations (6) can be manually crawled
 */

public class Theatres_Pacific_LocData {
	
public static void main(String args[]) throws IOException, InterruptedException {
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.pacifictheatres.com/all-locations/");

		CommonUtils.checkDoc(doc,Theatres_Pacific_LocData.class);

		Elements e = doc.select(".entry-content");
		
		e.forEach((a) -> {
				
			for(Element peter : a.getElementsByTag("table")){
				
				for(Element x : peter.select("tr td div")){
					if(x.text().isEmpty()) continue;
					System.out.println("Website ---> " + x.select("a").attr("href"));
					System.out.println("Address " + x.text());
				}
			}
			});
}

}
