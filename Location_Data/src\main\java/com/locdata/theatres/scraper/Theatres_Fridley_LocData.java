package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Fridley_LocData {

	static List<String> theatreUrls = new ArrayList<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> fridleyKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException, InterruptedException {

		String root = "http://www.fridleytheatres.com/";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.fridleytheatres.com/locations");

		CommonUtils.checkDoc(doc, Theatres_Fridley_LocData.class);

		Elements e = doc.select("div#locations-list");

		e.forEach((a) -> {

			Elements url = a.getElementsByTag("figcaption");

			url.forEach((m) -> {
				System.out.println("Website  ----> " + m.select("a[href]").attr("href"));
				theatreUrls.add(root.concat(m.select("a[href]").attr("href")));
			});
		});

		theatreUrls.stream().forEach((action) -> {
			final GeoCodeEntityCarrierToExcelWriter obj = new GeoCodeEntityCarrierToExcelWriter();

			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);
			Pattern pattern = Pattern.compile("\\d{4,5}");
			Matcher matcher = pattern.matcher(action);
			if (matcher.find()) {
				String storeNumber = matcher.group(0);
				System.out.println(" Store number ---------> " + storeNumber);
				String[] parser = action.split("/location/".concat(storeNumber));
				System.out.println(" Intensify ----------->" + parser[1].replace("#showtimes-container", "").replace("/",""));
				String storeName = parser[1].replace("#showtimes-container", "").replace("/","");
				obj.setStorename(storeName);
				obj.setStorenumber(Integer.parseInt(storeNumber));
			}

			CommonUtils.checkDoc(document, Theatres_Fridley_LocData.class);

			Elements el = document.select("div#theater-info");

			el.forEach((a) -> {

				Elements url = a.select("section#theater-tabs");

				url.forEach((m) -> {
					System.out.println("phone  ----> " + m.select("div#info-right").text());
					obj.setPhoneNumber(m.select("div#info-right").text());
					
					System.out.println(" Address ------> " + m.select("div#info-left").text());
					obj.setAddress(m.select("div#info-left").text());
				});
			});
			try {
				new GeoCodingApi(obj.getAddress(), obj);
			} catch (Exception e1) {
				System.out.println("Exception Raised !!!");
			}
			fridleyKeyDataSet.add(obj);
		});
		SheetLocalWriter.writeXLSXFile("FridleyTheatre.xlsx",fridleyKeyDataSet);
	}
}
