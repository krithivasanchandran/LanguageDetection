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

public class Theatres_Marcus_LocData {

	public static Set<GeoCodeEntityCarrierToExcelWriter> marcusKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException, InterruptedException {

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.marcustheatres.com/theatre-locations");

		CommonUtils.checkDoc(doc, Theatres_Marcus_LocData.class);

		Elements e = doc.select(".main-col");

		e.forEach((a) -> {

			Elements r = doc.select(".theatres.clearfix");

			r.forEach((link) -> {

				Elements url = link.select(".theatre-listing");

				for (Element t : url) {
					final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();

					for (Element v : t.select(".theatre-listing__info")) {

						String storeName = v.select(".theatre-name").text();
						System.out.println(" Store name  ::: ::: " + storeName);

						String raw = v.select(".theatre-listing__info--address").text().replace("Showtimes", "");
						Pattern pattern = Pattern.compile("\\(\\d{3}\\)\\s\\d{3}-\\d{4}");
						Matcher matcher = pattern.matcher(raw);

						if (matcher.find()) {
								System.out.println(" Phone number ----------> " + matcher.group(0));
								System.out.println( " Phone number 2 ---------> " + raw.substring(raw.indexOf(matcher.group(0))+matcher.group(0).length(), raw.length()));
								g.setPhoneNumber(matcher.group(0)+"," + raw.substring(raw.indexOf(matcher.group(0))+matcher.group(0).length(), raw.length()));
								

							System.out.println(" Address =----> " + raw.substring(0, raw.indexOf(matcher.group(0),0)));
							g.setStorename(storeName);
							g.setAddress(raw.substring(0, raw.indexOf(matcher.group(0),0)));
							
						}
					}
					try {
						new GeoCodingApi(g.getAddress(), g);
					} catch (Exception e1) {
					   System.out.println(e1.getMessage());
					}
					marcusKeyDataSet.add(g);
				}
				
			});
		});
		SheetLocalWriter.writeXLSXFile("MarcusTheatre.xlsx",marcusKeyDataSet);
	}
}
