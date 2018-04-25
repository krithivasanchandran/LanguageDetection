package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jsoup.nodes.Document;

import com.dataruvi.commons.unique.GenericUnique;
import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * Wheel Fun Rentals has over 100 locations nationwide, with more than 2.5 million 
 * guests hopping on-and-off recreational products each year. Locations include city, 
 * state, and national parks, beaches, marinas, retail spots, hotels, visitor centers, and more.
 * 
 */

public class WheelFunRentals {

	public static Set<GeoCodeEntityCarrierToExcelWriter> wheelFunRentalsKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws EncryptedDocumentException, InvalidFormatException, IOException,
			InterruptedException, InstantiationException, IllegalAccessException {
		
		String parentUrl = "https://wheelfunrentals.com/find-a-location/";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents(parentUrl);
		CommonUtils.checkDoc(doc, SixFlags.class);

		doc.select(".corp-find-loc__find-results").forEach((t) -> {
			t.select(".state-section").forEach((m) -> {
				m.select(".state-results").forEach((j) -> {
					j.select(".result-card").forEach((s) -> {
						final GeoCodeEntityCarrierToExcelWriter apiresponse = new GeoCodeEntityCarrierToExcelWriter();
						String storename = s.select(".result-card__title").text();
						apiresponse.setStorename(storename);
						String address = s.select(".result-card__address").text();
						apiresponse.setAddress(address);
						String phone = s.select(".result-card__phone").text();
						apiresponse.setPhoneNumber(phone);
						System.out.println("Store name "+ storename + " Address :: " + address + " Phone " + phone);
						   try {
								new GeoCodingApi("Wheel Fun Rentals"+apiresponse.getStorename()+apiresponse.getAddress(), apiresponse);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						   wheelFunRentalsKeyDataSet.add(apiresponse);
					});
				});
			});
		});
		
		try {
			SheetLocalWriter.writeXLSXFile("wheelFunEntertainment.xlsx", wheelFunRentalsKeyDataSet);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
}
