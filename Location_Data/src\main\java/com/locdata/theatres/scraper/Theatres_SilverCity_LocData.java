package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * Still needs work in it
 */

public class Theatres_SilverCity_LocData {

	public static Set<GeoCodeEntityCarrierToExcelWriter> silverCityKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException, InterruptedException {

		List<String> theatreNames = new ArrayList<String>() {
			{
				add("SilverCity Windsor Cinemas");
				add("SilverCity Thunder Bay Cinemas");
				add("SilverCity Sudbury Cinemas");
				add("SilverCity Richmond Hill Cinemas (UltraAVX)");
				add("Scotiabank Theatre Ottawa");
				add("SilverCity London Cinemas");
				add("SilverCity Newmarket Cinemas and XSCAPE Entertainment Centre");
				add("SilverCity Burlington Cinemas");
				add("SilverCity Brampton Cinemas");
				add("SilverCity St. Vital Cinemas");
				add("SilverCity CrossIron Mills Cinemas & XSCAPE Entertainment Centre");
				add("SilverCity Victoria Cinemas");
				add("SilverCity Riverport Cinemas");
				add("SilverCity Mission Cinemas");
			}
		};

		for (int i = 0; i < theatreNames.size(); i++) {

			String url = theatreNames.get(i);
			String url_1 = url.replaceAll("\\s", "+");
			Document document = ScraperLogic.Scraper.fetchHtmlContents(
					"https://www.cineplex.com/Theatres/TheatreListings?LocationURL=" + url_1 + "&Range=50");

			CommonUtils.checkDoc(document, Theatres_SilverCity_LocData.class);

			final GeoCodeEntityCarrierToExcelWriter silverCityMasterData = new GeoCodeEntityCarrierToExcelWriter();

			silverCityMasterData.setStorename(url);

			Elements t = document.body().select("div#Wrapper");

			t.forEach((theatrelist) -> {

				Elements k = theatrelist.select(".showtime-theatre");

				for (Element q : k) {

					System.out.println(" Address ------> " + q.select(".theatre-address").text());
					silverCityMasterData.setAddress(q.select(".theatre-address").text());
					System.out.println("  $$$$ " + q.getElementsByAttributeValue("itemprop", "telephone").text());
					silverCityMasterData.setPhoneNumber(q.getElementsByAttributeValue("itemprop", "telephone").text());

					try {
						new GeoCodingApi(silverCityMasterData.getAddress(), silverCityMasterData);
					} catch (Exception e1) {
						System.out.println(" Exception Printed !!! ");
					}
				}
			});
			silverCityKeyDataSet.add(silverCityMasterData);
		}
		SheetLocalWriter.writeXLSXFile("SilverCityTheatres.xlsx", silverCityKeyDataSet);
	}
}
