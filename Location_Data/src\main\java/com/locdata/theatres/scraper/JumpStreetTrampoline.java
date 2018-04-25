package com.locdata.theatres.scraper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.utils.common.CommonUtils;

/*
 * Behind Cloud Flare - Hence copied the HTML to local and scraped. 
 * Src : ./temp/JumpStreet.html
 */

public class JumpStreetTrampoline {

	public static Set<GeoCodeEntityCarrierToExcelWriter> jumpStreetKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws EncryptedDocumentException, InvalidFormatException, IOException,
			InterruptedException, InstantiationException, IllegalAccessException {

		String input = "./temp/JumpStreet.html";
		File in = new File(input);
		Document doc = Jsoup.parse(in, null);
		CommonUtils.checkDoc(doc, JumpStreetTrampoline.class);
		
		doc.select("div#map_sidebar").forEach((t) -> {
			System.out.println(t.text());
			t.select(".results_wrapper").forEach((j) -> {
				String storename = j.select(".location_name").text();
				j.select(".results_row_center_column.location_secondary").forEach((s) -> {
					final GeoCodeEntityCarrierToExcelWriter apiresponse = new GeoCodeEntityCarrierToExcelWriter();
					String address = s.select(".slp_result_address.slp_result_street").text();
					address.concat(s.select(".slp_result_address.slp_result_street2").text());
					address.concat(s.select(".slp_result_address.slp_result_citystatezip").text());
					address.concat(s.select(".slp_result_address.slp_result_country").text());
					String phone = s.select(".slp_result_address.slp_result_phone").text();
					apiresponse.setStorename(storename);
					apiresponse.setAddress(address);
					apiresponse.setPhoneNumber(phone);
					//http://www.gotjump.com/texas/dallas/
					//Hours are the same at most locations
					apiresponse.setStoreHours("Sunday: 10am - 8pm Monday: 10am - 8pm Tuesday: 10am - 8pm Wednesday: 10am - 8pm Thursday: 10am - 8pm Friday: 10am - 10pm Saturday: 10am - 10pm"
							+ "");

					System.out.println("Store name " + storename + " Address :: " + address + " Phone " + phone);
					try {
						new GeoCodingApi(apiresponse.getAddress(), apiresponse);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					jumpStreetKeyDataSet.add(apiresponse);
				});
			});
		});

		try {
			SheetLocalWriter.writeXLSXFile("JumpStreetEntertainment.xlsx", jumpStreetKeyDataSet);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
