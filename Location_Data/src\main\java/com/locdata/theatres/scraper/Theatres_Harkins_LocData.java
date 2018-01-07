package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Harkins_LocData {
	
	static List<String> theatreUrls = new ArrayList<String>();

	public static void main(String args[]) throws IOException {

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.harkins.com/locations");
		
		CommonUtils.checkDoc(doc,Theatres_Harkins_LocData.class);

			Elements ele = doc.select("section.layout.locations-page.unpadded");

			
			ele.forEach((location) -> {
				Elements t = location.select(".region");

				t.forEach((f) -> {
					
					for(Element pinto : f.select(".theatre-list.list.cols.small-gap")){
						
						System.out.println("https://www.harkins.com/"+pinto.getElementsByTag("a").first().attr("href"));
						for(Element addr : pinto.select(".address")){
							System.out.println("addresss ----> " +addr.text());
						}
						for(Element ph : pinto.select(".phone"))  System.out.println("phone -----------> " + ph.text());
					}

				});
			});
	}
}