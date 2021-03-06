package com.locdata.theatres.scraper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.theatres.entity.EntityTheatre;
import com.locdata.utils.common.CommonUtils;

public class Theatres_DraftHouse_AggData {

	static List<String> theatres = new ArrayList<String>();
	static List<EntityTheatre> address = new ArrayList<EntityTheatre>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> draftHouseKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException, InterruptedException {

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://drafthouse.com/austin/theaters");

		CommonUtils.checkDoc(doc,Theatres_DraftHouse_AggData.class);

		Elements link = doc.select(".MarketMenu-links");
		Elements eli = link.select("a[href]");
		System.out.println(link.size());

		for (Element l : eli) {
			System.out.println(l.attr("href"));
			theatres.add(l.attr("href"));
		}

		ListIterator<String> itr = theatres.listIterator();

		while (itr.hasNext()) {

			String theatre = itr.next();
			
			Document document = ScraperLogic.Scraper.fetchHtmlContents(theatre);

			CommonUtils.checkDoc(document,Theatres_DraftHouse_AggData.class);
			
			final GeoCodeEntityCarrierToExcelWriter draftHouseMasterData = new GeoCodeEntityCarrierToExcelWriter();

			Elements element = document.select(".Footer-nav");
			Element rambo = element.select(".medium-3.columns").first();
			String storeName = rambo.select(".Footer-theater").text();
			System.out.println(" Store Name  ==================> " + storeName);
			draftHouseMasterData.setStorename(storeName);
			Elements rt = rambo.select(".Footer-theaterInfo");

			for (Element re : rt) {
				EntityTheatre entityTheatre = new EntityTheatre();

				System.out.println(re.firstElementSibling().text());
				entityTheatre.setCountyName(re.firstElementSibling().text());

				Element er = re.lastElementSibling();
				Elements rw = er.select("a[href]");
				for (Element t : rw) {
					if (t.text().matches("([0-9]*)-([0-9]*)-([0-9]*)")) {
						System.out.println(" Phone Number ------------ > " + t.text());
						draftHouseMasterData.setPhoneNumber(t.text());
						entityTheatre.setPhoneNumber(t.text());
					} else {
						draftHouseMasterData.setAddress(t.text());
						entityTheatre.setAddress(t.text());
					}
				}
				//address.add(entityTheatre);
			}
			new GeoCodingApi(draftHouseMasterData.getAddress(), draftHouseMasterData);
			draftHouseKeyDataSet.add(draftHouseMasterData);
		}

		 SheetLocalWriter.writeXLSXFile("DraftHouseTheatres.xlsx",draftHouseKeyDataSet);
	}
}
