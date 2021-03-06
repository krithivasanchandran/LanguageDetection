package com.locdata.theatres.scraper;

import java.io.IOException;
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

public class Theatres_GoodRich_LocData {
	
	public static Set<GeoCodeEntityCarrierToExcelWriter> goodRichKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException {

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://store.goodrichqualitytheaters.com/browsing/Cinemas");
		
		CommonUtils.checkDoc(doc,Theatres_GoodRich_LocData.class);

			Elements ele = doc.select("article#cinema-list");

			ele.forEach((location) -> {


				Elements t = location.select(".item-details");

				t.forEach((f) -> {
					
					for(Element pinto : f.select(".item-details-inner")){
						final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();

						Pattern pattern = Pattern.compile("\\d{3}");
						Matcher matcher = pattern.matcher(pinto.getElementsByTag("a").attr("href"));
						
						if (matcher.find()) {
							g.setStorenumber(Integer.parseInt(matcher.group(0)));
						}
						
						System.out.println(pinto.getElementsByTag("a").attr("href"));
						System.out.println("addresss ----> " +pinto.select(".contact-address").text() );
						g.setAddress(pinto.select(".contact-address").text());
						System.out.println("phone -----------> " + pinto.select(".contact-phone").text());
						g.setPhoneNumber(pinto.select(".contact-phone").text());
						try {
							new GeoCodingApi(g.getAddress(), g);
						} catch (Exception e) {
							System.out.println(" Throwing Exception Here !!!");
						}
						goodRichKeyDataSet.add(g);
					}
				});
			});
			SheetLocalWriter.writeXLSXFile("GoodRichTheatre.xlsx",goodRichKeyDataSet);
	}
}
