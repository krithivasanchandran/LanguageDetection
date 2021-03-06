package com.locdata.realestate.commercialproperty.scraper; 

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

//143 Retail locations 

public class RealEst_GeneralGrowth_LocData {
	
	static Set<String> esturl = new HashSet<String>();
	
	public static void main(String args[]) throws IOException {
		
		String parentUrl = "https://www.ggp.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.ggp.com/properties.html?intcmp=home;find-a-property;browse-our-properties");

		CommonUtils.checkDoc(doc,RealEst_GeneralGrowth_LocData.class);
			
		Elements t = doc.select("div#propResults");

		for (Element r : t) {

			Elements re = r.select(".ggp-property.col.l3.m3.s6");

			for (Element k : re) {
				System.out.println(parentUrl.concat(k.select("a").attr("href")));
				System.out.println(" Address" + k.select("div").first().text());
				esturl.add(parentUrl.concat(k.select("a").attr("href")));
			}
		}
		
		if(esturl.size() > 0){
			esturl.forEach((action) -> {
				
				Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

				CommonUtils.checkDoc(document,RealEst_GeneralGrowth_LocData.class);

				Element r = document.select(".contact-card").first();
				
					System.out.println("Name --------> " + r.getElementsByTag("div").first().text());
					System.out.println("Phone and email --------> " + r.select("a").text());
			});
		}
	}
}