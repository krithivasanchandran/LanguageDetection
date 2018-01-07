package com.locdata.theatres.scraper;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_mjrtheatres_LocData {

	public static void main(String args[]) throws IOException, InterruptedException {
		
		String root = "http://www.metrotheatres.com";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.mjrtheatres.com/locations");

		CommonUtils.checkDoc(doc,Theatres_mjrtheatres_LocData.class);

		Elements e = doc.select("main#main-wrapper");
		
		e.forEach((a) -> {
				
			for(Element peter : a.select("a")){
				System.out.println("Website ---> " + root.concat(peter.attr("href")));
				System.out.println("Address " + peter.getElementsByTag("figcaption").text());
			}
				
			});
}
}