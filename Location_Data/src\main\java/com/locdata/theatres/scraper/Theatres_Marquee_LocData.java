package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Marquee_LocData {

	static List<String> theatreUrls = new ArrayList<String>();
	
	public static void main(String args[]) throws IOException, InterruptedException {
		
		String root = "http://www.marqueecinemas.com/";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.marqueecinemas.com/locations");

		CommonUtils.checkDoc(doc,Theatres_Marquee_LocData.class);

		Elements e = doc.select("div#content");
		
		e.forEach((a) -> {
		
			Elements r = a.select("a");
			
			r.forEach((link) -> {
				
				System.out.println("Website ---> " + root.concat(link.attr("href")));
				theatreUrls.add(root.concat(link.attr("href")));
			});
		});
		
		theatreUrls.parallelStream().forEach((action) -> {

			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

			CommonUtils.checkDoc(document,Theatres_Marquee_LocData.class);

			Elements p = document.select("div#theater_info");
			
			p.forEach((a) -> {
				
				Elements r = a.select(".address");
				
					System.out.println("Address ---> " + r.text());
			});

		});
}
}