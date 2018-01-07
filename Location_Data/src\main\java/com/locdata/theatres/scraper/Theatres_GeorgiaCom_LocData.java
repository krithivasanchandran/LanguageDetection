package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_GeorgiaCom_LocData {

	static List<String> theatreUrls = new ArrayList<String>();

	public static void main(String args[]) throws IOException {

		String parentUrl = "https://www.gtcmovies.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.gtcmovies.com/");
		
		CommonUtils.checkDoc(doc,Theatres_GeorgiaCom_LocData.class);

			Elements ele = doc.select(".gridRow.section");

			ele.forEach((location) -> {

				Elements t = location.getElementsByTag("a");

				t.forEach((f) -> {

					if(f.attr("href").length() > 3){
						System.out.println(
								" -->href >>>>  " + parentUrl.concat(f.attr("href")));
						theatreUrls.add(parentUrl.concat(f.attr("href")));
					}
				});
			});
			
	 	theatreUrls.parallelStream().forEach((action) -> {
    	  
    	  Document document = ScraperLogic.Scraper.fetchHtmlContents(action.concat("/contact-us-by-email-phone-mail"));
  		
  		  CommonUtils.checkDoc(document,Theatres_GeorgiaCom_LocData.class);
  		  
	
				Elements t = document.select(".gridRow.section");
				
				t.forEach((pandu) -> {
					
					Elements inner = pandu.select(".gridCol-l-6.gridCol-m-6.gridCol-s-12.maintext");
	
					for(Element m : inner){
						
						System.out.println(
								" -->Phone Theatre Office >>>>  " +m.select("p").get(6).text());
						
						System.out.println(" -->Mail (Theatre) >>>>  " +m.select("p").get(8).text());
					}
			});
      });
	}
}