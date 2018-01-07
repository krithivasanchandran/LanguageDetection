package com.locdata.theatres.scraper;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_MovieTavern_LocData {

	public static void main(String args[]) throws IOException, InterruptedException {
		
		String root = "https://www.movietavern.com";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.movietavern.com/locations/");

		CommonUtils.checkDoc(doc,Theatres_MovieTavern_LocData.class);

		Elements e = doc.select("div#main-content");
		
		e.forEach((a) -> {
				
			for(Element peter : a.select(".directory-list.cf")){
				
				for(Element x : peter.select(".bottom")){
					System.out.println("Website ---> " + root.concat(x.select("a").attr("href")));
					System.out.println("Address " + x.tagName("br").text());
				}
			}
			});
}
}
