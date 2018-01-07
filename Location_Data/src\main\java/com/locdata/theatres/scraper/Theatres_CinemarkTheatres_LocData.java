package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_CinemarkTheatres_LocData {
	
	static List<String> theatreUrls = new ArrayList<String>();

	public static void main(String args[]) throws IOException, InterruptedException {
	
			Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.fandango.com/movie-theaters/cinemark");
	
			CommonUtils.checkDoc(doc,Theatres_CinemarkTheatres_LocData.class);

			Elements e = doc.select("div#GlobalBody_singleChain");
			
			e.forEach((a) -> {
				Elements r = doc.select(".theatersname");
				
				r.forEach((link) -> {
					
					Elements url = link.select(".theaterlink");
					
					url.forEach((m) -> {
						System.out.println(m.text());
						System.out.println(" ----> " + m.select("a[href]").attr("href"));
						theatreUrls.add(m.select("a[href]").attr("href"));
					});
				});
			});
			
			if(theatreUrls.size() > 0){
				theatreUrls.forEach((root) -> {
					
					Document document = ScraperLogic.Scraper.fetchHtmlContents(root);
					
					CommonUtils.checkDoc(document,Theatres_CinemarkTheatres_LocData.class);
										
						Elements r = document.select(".tdp.js-tdp");
						
						r.forEach((link) -> {
							
							Elements url = link.select(".subnav__link-item.subnav__link-item--address");
							
							url.forEach((m) -> {
								System.out.println(m.text());
								System.out.println(" Phone number ---> " + link.select(".subnav__link-item").last().text());
							});
						});
					});
			}
	}
}	