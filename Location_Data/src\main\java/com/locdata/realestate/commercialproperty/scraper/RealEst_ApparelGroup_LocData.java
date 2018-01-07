package com.locdata.realestate.commercialproperty.scraper;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;


public class RealEst_ApparelGroup_LocData {

	//Not working
	
public static void main(String args[]) throws IOException {
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.appareluae.com/");
		System.out.println(doc.text());
		CommonUtils.checkDoc(doc,RealEst_ApparelGroup_LocData.class);
			
		Elements t = doc.select("div#lisloc_list_view_base");
		System.out.println(t.text());

		for (Element r : t) {
			

			Elements re = r.select(".list-mall-wrapper");

			for (Element k : re) {
				
				for(Element kk : k.select(".list-mall-wrap")){
					System.out.println("Address --->  "+ kk.select(".list-mall-details").text());
				}
			}
		}
}
}