package com.locdata.realestate.commercialproperty.scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class RealEst_TangerOutlets_LocData {

static Set<String> esturl = new LinkedHashSet<String>();
	
	public static void main(String args[]) throws IOException {
		
		String parentUrl = "https://www.tangeroutlet.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.tangeroutlet.com/locations");

		CommonUtils.checkDoc(doc,RealEst_TangerOutlets_LocData.class);
			
		Elements t = doc.select("div#centerIndexList");
		
		for (Element r : t) {

			Elements re = r.select(".row");
			
			for (Element k : re) {
				
				for(Element kk : k.getElementsByTag("address")){
					System.out.println("Address --->  "+ kk.select(".centerLocation").text());
					//System.out.println(" Website ------> " + parentUrl.concat(kk.getElementsByTag("a").attr("href")));
					esturl.add(parentUrl.concat(kk.getElementsByTag("a").attr("href").concat("/stores")));
				}
				System.out.println("Parent Store Hours --------> " + k.getElementsByTag("dl").text());
			}
		}
		
		esturl.forEach((action) -> {
			
			if(!action.equalsIgnoreCase("https://www.tangeroutlet.com/stores")){
				
				System.out.println( "Website -------> " + action);
				
				Document document = ScraperLogic.Scraper.fetchHtmlContents(action);
				CommonUtils.checkDoc(doc,RealEst_TangerOutlets_LocData.class);

				System.out.println(document.text());
				
				Elements dt = document.select(".container");
				
				for (Element r : dt) {

					Elements re = r.select(".row.store-directory");
					
					for (Element k : re) {
						
						for(Element kk : k.select(".col-xs-12")){
							
							for(Element mnm : kk.select(".row")){
								System.out.println("store-brand --->  "+ mnm.select(".store-brand").text());
								System.out.println("store-number ------> " + mnm.select(".store-number").text());
								System.out.println("store-phone ------> " + mnm.select(".store-phone").text());
							}
						}
					}
				}
			}
		});
	}
}