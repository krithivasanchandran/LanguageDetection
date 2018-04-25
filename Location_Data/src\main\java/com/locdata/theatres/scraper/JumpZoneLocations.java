package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

// The trickiest part is getting the store hours information;
// Logic Get the Text and parse based on the keywords of weekdays.
//http://www.jumpzoneparty.com/www.jumpzoneparty.com/JumpZone_Facility.php?jzpcity=Allison Park&jzpid=045
// Phone number - still need to work on parsing it :   412-487-JUMP (5867)


public class JumpZoneLocations {

	final static Set<String> locationurls = new HashSet<String>();
	final public static Set<GeoCodeEntityCarrierToExcelWriter> JumpZoneKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException {
		
		String url = "http://www.jumpzoneparty.com/www.jumpzoneparty.com/Indoor-Inflatable-Locations-for-Jump-Zone.php";
		String root = "http://www.jumpzoneparty.com/www.jumpzoneparty.com/";
		final String country = "US";

		AtomicInteger count = new AtomicInteger(0);
		Document doc = ScraperLogic.Scraper.fetchHtmlContents(url);

		CommonUtils.checkDoc(doc, JumpZoneLocations.class);

		/*
		 * Retrieve Jump Zone Location - Only usa it is available for
		 * entire world
		 */

		Elements r = doc.select(".locator_city");
		for (Element d : r) {
			System.out.println("Href " + d.attr("value"));
			String sublink = d.attr("value");
			sublink = root.concat(sublink);
			locationurls.add(sublink);
			count.addAndGet(1);
		}
		System.out.println(
				" Total Location Count is  :: " + count.get() + " Location urls size is :: " + locationurls.size());
		
		locationurls.forEach((e) -> {
			System.out.println(" Now crawling :: ---------> " + e);
			final GeoCodeEntityCarrierToExcelWriter apiresponse = new GeoCodeEntityCarrierToExcelWriter();

			Document document = ScraperLogic.Scraper.fetchHtmlContents(e);

			CommonUtils.checkDoc(document, JumpZoneLocations.class);
			String storename = document.select(".jzhead1").text();
			
			// Getting the child first and then traversing the parent from there.
			
			String rawaddress = document.select(".jzhead1").parents().select("h1").text();
			String parsedaddress = rawaddress.replace(storename, "").trim();
			String temp =parsedaddress;
			
			PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
			try {
				PhoneNumberMatch itr = phoneUtil.findNumbers(parsedaddress, country).iterator().next();
				String phone = (String) parsedaddress.subSequence(itr.start(), itr.end());
				System.out.println(" Phone information --------------------> " + phone);

				apiresponse.setPhoneNumber(phone);
				temp = temp.substring(0, itr.start());
				apiresponse.setAddress(temp);

			} catch (Exception ex) {
				System.err.println("NumberParseException was thrown: " + ex.toString());
			}
			
			System.out.println(" Address :: ---------> " + temp);
			
			try {
				new GeoCodingApi(apiresponse.getAddress(), apiresponse);
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
			
//			String hourscontent = document.select(".jzbodyLG").text();
//			String storehours = hourscontent.substring(hourscontent.indexOf("Monday"), hourscontent.indexOf("Sunday")+10);
			apiresponse.setStoreHours(e);

			JumpZoneKeyDataSet.add(apiresponse);
		});
		SheetLocalWriter.writeXLSXFile("JumpZone.xlsx", JumpZoneKeyDataSet);
	}
}