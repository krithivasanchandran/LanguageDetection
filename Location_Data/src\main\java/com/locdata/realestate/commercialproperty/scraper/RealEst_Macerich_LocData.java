package com.locdata.realestate.commercialproperty.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class RealEst_Macerich_LocData {
	
static Set<String> esturl = new HashSet<String>();
	
	public static void main(String args[]) throws IOException {
		
		String parentUrl = "http://www.macerich.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.macerich.com/Leasing/Find");

		CommonUtils.checkDoc(doc,RealEst_Macerich_LocData.class);
			
		Elements t = doc.select("section.property-list");
		
		for (Element r : t) {

			Elements re = r.select(".uk-panel");
			
			for (Element k : re) {
				System.out.println(parentUrl.concat(k.getElementsByTag("a").attr("href")));
				esturl.add(parentUrl.concat(k.getElementsByTag("a").attr("href")));
			}
		}
		
		if(esturl.size() > 0){
			esturl.forEach((action) -> {
				
				Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

				CommonUtils.checkDoc(document,RealEst_GeneralGrowth_LocData.class);

				Elements r = document.select(".address-container");
				
				for(Element ti : r){
					
					System.out.println("Address --------> " + ti.getElementsByTag("p").text());
				}
				
				Element contact = document.select(".contact-name").first();
				System.out.println( "Name --> " + contact.text());
				Element phone = document.select(".contact-phone").first();
				System.out.println( "Phone --> " + phone.text());

				Element email = document.select(".contact-email").first();
				System.out.println("email --------> " + email.text());
			});
		}
	}
}