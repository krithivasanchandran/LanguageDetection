package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * Url: https://www.daveandbusters.com/locations
 * Multi Location Events : https://www.daveandbusters.com/party-and-event-venues/multi-location-events
 *
 */

public class DaveBustersEntertainment {

	static Set<String> daveBustersLocations = new HashSet<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> daveBustersKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException {

		AtomicInteger counter = new AtomicInteger(0);
		AtomicInteger ptagcounter = new AtomicInteger(0);

		String parentUrl = "https://www.daveandbusters.com/locations";

		String rootSeed = "https://www.daveandbusters.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents(parentUrl);

		CommonUtils.checkDoc(doc, DaveBustersEntertainment.class);

		Elements html = doc.select("div#dnb-locations-list");

		html.stream().forEach((t) -> {
			String contentHeader = t.select(".dnb-locations-list-header").text();
			System.out.println(" Location Header  :::: ::: :::: =>" + contentHeader);

			t.select(".row").forEach((m) -> {
				m.select(".col-md-3").forEach((l) -> {
					l.select(".location-list-item").forEach((q) -> {
						Elements o = q.select("ul > li");
						for (Element i : o) {
							String href = i.select("a").attr("href");
							href = rootSeed.concat(href);
							System.out.println(" Href value is going to be  ====> " + href);
							daveBustersLocations.add(href);
							counter.addAndGet(1);
						}
					});
				});
			});
		});
		System.out.println("Total Locations count is :: " + counter.get());

		daveBustersLocations.stream().forEach((q) -> {

			System.out.println(" Now Crawling Current Seed URL :: ==> " + q);
			final GeoCodeEntityCarrierToExcelWriter responsebean = new GeoCodeEntityCarrierToExcelWriter();
			final StringBuilder addressBuilder = new StringBuilder();

			Document document = ScraperLogic.Scraper.fetchHtmlContents(q);
			CommonUtils.checkDoc(document, DaveBustersEntertainment.class);

			document.select(".dnb-main-container").forEach((yelp) -> {
				yelp.select("div#location-info").forEach((v) -> {
					v.select(".row").forEach((s) -> {
						s.select(".col-xs-12.col-sm-6.location").forEach((o) -> {
							String addr = o.tagName("p").text();
							if(addr.contains("Directions")){
								addr = addr.replace("Directions", "");
							}
							responsebean.setAddress(addr);
							System.out.println("Address ::::  ---------> " + addr);
						});

						// Hours of Operations and Phone number
						Elements hourshtml = s.select(".col-xs-12.col-sm-6.schedule");

						hourshtml.stream().forEach((prat) -> {
							if (prat.tagName("p").text().contains("Sun") || prat.tagName("p").text().contains("Fri")) {
								System.out.println(prat.tagName("p").text());
								// Need to set store hours here
							}
						});

						String phoneNumber = hourshtml.select("a#click-to-call").text();
						if (!phoneNumber.isEmpty()) {
							System.out.println("Phone number ======?> " + phoneNumber);
							responsebean.setPhoneNumber(phoneNumber);
						}

						hourshtml.select(".dnb-planning-contact").forEach((email) -> {
							String emailID = email.attr("href");
							System.out.println(" EMAIL ID ::: ORGANIZER " + emailID);
						});
					});
				});
			});

			try {
				Thread.sleep(2000);
			    new GeoCodingApi(responsebean.getAddress(), responsebean);
				addressBuilder.setLength(0);
				daveBustersKeyDataSet.add(responsebean);

			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		SheetLocalWriter.writeXLSXFile("DaveBustersEntertainment.xlsx", daveBustersKeyDataSet);

	}
}