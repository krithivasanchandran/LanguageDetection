package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * 
 * Not able to scrape email id :  Contact Email ID ----->  <a href="/cdn-cgi/l/email-protection#eb82858d8486848599848eab8c8a878a93929f838e8a9f998e98c5888486">href</a>
 */

public class Theatres_Galaxy_LocData {
	
	static List<String> theatreUrls = new ArrayList<String>();

	public static void main(String args[]) throws IOException, InterruptedException {

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.galaxytheatres.com/Browsing/Cinemas");

		CommonUtils.checkDoc(doc, Theatres_Galaxy_LocData.class);

		Elements e = doc.select("article#cinema-list");

		e.forEach((a) -> {

			Elements address = a.select(".contact-address");

			address.forEach((m) -> {
				System.out.println(" Address ----->  " + m.text());
			});
			
			Elements contact = a.select(".contact-other");
			
			contact.forEach((m) -> {
				System.out.println(" Contact Phone ----->  " + m.select(".contact-phone").text());
				System.out.println(" Contact Email ID ----->  " + m.select(".contact-email a").attr("href"));
			});
		});
	}

}
