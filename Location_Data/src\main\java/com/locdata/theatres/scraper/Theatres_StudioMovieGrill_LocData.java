package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_StudioMovieGrill_LocData {

	public static Set<GeoCodeEntityCarrierToExcelWriter> studioMovieKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException, InterruptedException {

		String root = "https://www.studiomoviegrill.com/";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.studiomoviegrill.com/Locations");

		CommonUtils.checkDoc(doc, Theatres_Starplex_LocData.class);

		Elements e = doc.select("ul#location-list");

		e.forEach((a) -> {

			for (Element peter : a.select(".location-box")) {

				String storename = null;
				for (Element x : peter.select("h3 a")) {
					System.out.println("Website ---> " + root.concat(x.attr("href")));
					storename = x.attr("href");
					storename = storename.substring(storename.lastIndexOf("/") + 1, storename.length());
				}
				System.out.println("storename =-------> " + storename);
				final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();
				g.setStorename(storename);

				peter.select(".location-box").forEach((i) -> {
					i.select(".info").forEach((r) -> {
						String address = r.select("a").first().text();
						String phone = r.select("a[href=tel:]").text();

						System.out.println("Address --------> " + address);
						System.out.println("Phone -------->" + phone);

						g.setAddress(address);
						g.setPhoneNumber(phone);
					});
				});
				try {
					new GeoCodingApi(g.getAddress(), g);
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
				studioMovieKeyDataSet.add(g);
			}
		});
		SheetLocalWriter.writeXLSXFile("studioMovieTheatre.xlsx", studioMovieKeyDataSet);
	}
}
