package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Metropolitan_LocData {
	
	public static void main(String args[]) throws IOException, InterruptedException {
		
		String root = "http://www.metrotheatres.com";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.metrotheatres.com/locations");

		CommonUtils.checkDoc(doc,Theatres_Metropolitan_LocData.class);

		Elements e = doc.select("div#locations");
		
		e.forEach((a) -> {
		
			Elements r = a.select(".detail");
			
			r.forEach((link) -> {
				
				System.out.println("Website ---> " + root.concat(link.select("p a").attr("href")));
				
				System.out.println("Address " + link.select(".address").text());
			});
		});
		
}
	
}
