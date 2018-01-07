package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_GoodRich_LocData {
	
	public static void main(String args[]) throws IOException {

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://store.goodrichqualitytheaters.com/browsing/Cinemas");
		
		CommonUtils.checkDoc(doc,Theatres_GoodRich_LocData.class);

			Elements ele = doc.select("article#cinema-list");

			ele.forEach((location) -> {

				Elements t = location.select(".item-details");

				t.forEach((f) -> {
					
					for(Element pinto : f.select(".item-details-inner")){
						
						System.out.println(pinto.getElementsByTag("a").attr("href"));
						System.out.println("addresss ----> " +pinto.select(".contact-address").text() );
						System.out.println("phone -----------> " + pinto.select(".contact-phone").text());
					}

				});
			});
	}
}
