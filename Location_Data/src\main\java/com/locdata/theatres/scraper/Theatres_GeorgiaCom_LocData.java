package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

public class Theatres_GeorgiaCom_LocData {

	static List<String> theatreUrls = new ArrayList<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> georgiaKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException {

		String parentUrl = "https://www.gtcmovies.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.gtcmovies.com/theatres");
		
		CommonUtils.checkDoc(doc,Theatres_GeorgiaCom_LocData.class);
		
		
		doc.select(".theatreList.fc").stream().forEach((p) -> {
			
			p.select(".gridCol-l-4.gridCol-m-4.gridCol-s-12.cinemaBlock").stream().forEach((f) -> {
				
				final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();
				
				String storeName = f.select(".cinemaItemTitle").text();
				
				String address = f.select(".cinemaItemContent").text();
				
				Pattern pattern = Pattern.compile("\\(\\d{3}\\)\\s\\d{3}-\\d{4}");
				Matcher matcher = pattern.matcher(address);
				
				if (matcher.find()) {
					g.setPhoneNumber(matcher.group(0));
					address = address.split("matcher.group(0)")[0];
					g.setAddress(address);
				}
				g.setStorename(storeName);
				georgiaKeyDataSet.add(g);
				try {
					new GeoCodingApi(g.getAddress(), g);
				} catch (Exception e) {
					System.out.println(" Exception Thrown here in GeoCoding API ");
				}
				georgiaKeyDataSet.add(g);
				System.out.println(" Store name ------> " + storeName + " Address -----> " + address);
				
			});
		});
		SheetLocalWriter.writeXLSXFile("GeorgiaTheatre.xlsx",georgiaKeyDataSet);
	}
}