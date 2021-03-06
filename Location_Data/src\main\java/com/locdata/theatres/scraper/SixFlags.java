package com.locdata.theatres.scraper;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * Good Tricky One - Few sites to crawl, zip codes are little confusing; 
 * 13710 Central Avenue, Upper Marlboro, MD 20721. recognizes : 13710 as zip code 
 * Normal parsing happens here: 1001 Fairgrounds Dr. SW, Vallejo, CA 94589.
 * 1. Stores in the Six Flags
 * 2. To Do List : Still need to crawl the All dining
 */

public class SixFlags {

	static Set<String> SixFlagUSALocationsConditional = new HashSet<String>();
	static Set<String> locationDataSet = new HashSet<String>();
	static Set<String> addresspoint = new HashSet<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> sixFlagsKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) {

		String parentUrl = "https://www.sixflags.com";
		SixFlagUSALocationsConditional.add("CALIFORNIA");
		SixFlagUSALocationsConditional.add("GEORGIA");
		SixFlagUSALocationsConditional.add("ILLINOIS");
		SixFlagUSALocationsConditional.add("MARYLAND");
		SixFlagUSALocationsConditional.add("MASSACHUSETTS");
		SixFlagUSALocationsConditional.add("MISSOURI");
		SixFlagUSALocationsConditional.add("NEW JERSEY");
		SixFlagUSALocationsConditional.add("NEW YORK");
		SixFlagUSALocationsConditional.add("TEXAS");

		Document doc = ScraperLogic.Scraper.fetchHtmlContents(parentUrl);

		CommonUtils.checkDoc(doc, SixFlags.class);

		/*
		 * Retrieve Six Flags Location - USA but available for entire world
		 */

		Elements recco = doc.select("div#choose-park-menu-body");

		recco.select(".choose-your-park").forEach((t) -> {
			Elements outer = t.select("li");
			for (Element r : outer) {
				String q = r.attr("title");
				if (SixFlagUSALocationsConditional.contains(q)) {
					String url = r.select("ul>li a").attr("href");
					url = parentUrl.concat(url);
					locationDataSet.add(url);
				}
			}
		});

		locationDataSet.forEach((seed) -> {
			
			final GeoCodeEntityCarrierToExcelWriter apiresponse = new GeoCodeEntityCarrierToExcelWriter();
			String originalseed = seed;
		
			// Logic to Retrieve address
			String directionpath = "/plan-your-visit/directions";
			seed = seed.concat(directionpath);

			Document document = ScraperLogic.Scraper.fetchHtmlContents(seed);
			CommonUtils.checkDoc(document, SixFlags.class);

			System.out.println(" Now Crawling  " + seed);
			Elements e = document.select("div#zone-content");

			for (Element r : e) {
				Elements ele = r.select(".field-item.even.field-item-0.last-item");
				String rawText = ele.text();
				// Zip code Regex here
				String address = rawText.split("Our physical location is")[1];
				String addr;
				String zipcode = null;
				apiresponse.setStorename(seed);
				
				
				if (seed.equalsIgnoreCase("https://www.sixflags.com/fiestaTexas/plan-your-visit/directions") || 
						seed.equalsIgnoreCase("https://www.sixflags.com/america/plan-your-visit/directions")) {

					Matcher matcher = Pattern.compile("^\\d{5}-\\d{4}|\\d{5}|[A-Z]\\d[A-Z]\\d[A-Z]\\d$").matcher(address);
					
					if (matcher.find()) {
						zipcode = matcher.group();
					}
					System.out.println(" " + address.matches("^\\d{5}-\\d{4}|\\d{5}|[A-Z]\\d[A-Z]\\d[A-Z]\\d$"));
					addr = address.split("^\\d{5}-\\d{4}|\\d{5}|[A-Z]\\d[A-Z]\\d[A-Z]\\d$")[1];
					addr = zipcode.concat(addr);
				    try {
						new GeoCodingApi(addr, apiresponse);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				    sixFlagsKeyDataSet.add(apiresponse);
				} else {
					
					Matcher matcher = Pattern.compile("^\\d{5}-\\d{4}|\\d{5}|[A-Z]\\d[A-Z]\\d[A-Z]\\d$").matcher(address);
					if (matcher.find()) {
						zipcode = matcher.group();
					}
					addr = address.split("^\\d{5}-\\d{4}|\\d{5}|[A-Z]\\d[A-Z]\\d[A-Z]\\d$")[0];
					addr = addr.concat(zipcode);
					
					String allRestaurants = "/food-and-dining/all-park-restaurants";
					String restaurants = originalseed.concat(allRestaurants);
					Document d = ScraperLogic.Scraper.fetchHtmlContents(restaurants);
					CommonUtils.checkDoc(d, SixFlags.class);

					Element et = d.select(".item-list").last();
			
					Elements t = et.select("ul");
					Set<String> allrestaurs = new HashSet<String>();
						for(Element u : t){
							 for(Element k : u.select(".mobileTextInnerBlock.attractionShort")){
								System.out.println(" Joint restaurant  ----> " + k.select("h4").text());
								String foodchainName = k.select("h4").text();
								String locationinsideSixFlags = k.select(".venueLocation").text();
								System.out.println(" Location ------> " + locationinsideSixFlags);
								//Temporarly store restaurants in store hours column 
								foodchainName = foodchainName.concat(" ( "+locationinsideSixFlags+" ) " + "\n ");
								allrestaurs.add(foodchainName);
							 }
						}
						StringBuffer buffer = new StringBuffer();
						allrestaurs.forEach((l)->{buffer.append(l);});
						apiresponse.setStoreHours(buffer.toString());
				    try {
						new GeoCodingApi(addr, apiresponse);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				    sixFlagsKeyDataSet.add(apiresponse);
				}
				System.out.println(" Directions :: " + addr);
			}
			try {
				SheetLocalWriter.writeXLSXFile("SixFlagstersEntertainment.xlsx", sixFlagsKeyDataSet);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}
}
