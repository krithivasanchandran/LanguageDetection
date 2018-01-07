package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Starplex_LocData {
	
public static void main(String args[]) throws IOException, InterruptedException {
		
		Set<String> theatreurls = new HashSet<String>();
	
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.movieticketsandshowtimes.com/starplex-cinemas/movietheaterchain/");

		CommonUtils.checkDoc(doc, Theatres_Starplex_LocData.class);

		Elements e = doc.select("div#wrapper");
		
		e.forEach((a) -> {
				
			for(Element peter : a.select("div#movie-chain-list-main")){
				
				for(Element x : peter.select("a")){
					System.out.println("Website ---> " + x.attr("href"));
					theatreurls.add(x.attr("href"));
				}
			}
			});
		
		theatreurls.forEach((action) -> {

			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

			CommonUtils.checkDoc(document, Theatres_Starplex_LocData.class);

			Elements element = document.select("div#wrapper");
			
			element.forEach((a) -> {
				
				for(Element peter : a.select("div#street")){
					
					for(Element x : peter.select("span#phone")){
						System.out.println("Phone ---> " + x.text());
					}
					
					System.out.println("Website =========> " + peter.getElementsByTag("span").first().text());
				}
				});
			
		});
		}
   }