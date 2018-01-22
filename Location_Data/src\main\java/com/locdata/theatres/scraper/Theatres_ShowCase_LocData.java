package com.locdata.theatres.scraper;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_ShowCase_LocData {

public static void main(String args[]) throws IOException, InterruptedException {
		
		String root = "https://www.showcasecinemas.com";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.showcasecinemas.com/theatres");

		CommonUtils.checkDoc(doc, Theatres_ShowCase_LocData.class);

		Elements e = doc.select("div#block-system-main");
		
		e.forEach((a) -> {
				
			for(Element peter : a.select(".theatres-overview")){
				
				for(Element x : peter.select(".row.locations-wrap")){
					System.out.println("Website ---> " + root.concat(x.select("a").attr("href")));
					System.out.println("Address " + x.getElementsByAttributeValue("itemprop", "address").text());
				}
			}
			});
}
}
