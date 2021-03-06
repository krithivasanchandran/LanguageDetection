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

public class Theatres_CineBistro_Aggdata {
	public static Set<GeoCodeEntityCarrierToExcelWriter> cineBistroKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException {

		String parentUrl = "http://cinebistro.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://cinebistro.com/about-cinebistro/index.php");
		
		CommonUtils.checkDoc(doc,Theatres_CineBistro_Aggdata.class);

		Elements parentLocation = doc.select("div#content");

		for (Element p : parentLocation) {

			Elements ele = p.select(".twothirdCol.colorBg.rightChild");

			ele.forEach((location) -> {

				Elements t = location.select(".locationInfo");

				t.forEach((f) -> {
					final GeoCodeEntityCarrierToExcelWriter dataSet = new GeoCodeEntityCarrierToExcelWriter();
				//	System.out.println(
					//		" -->href >>>>  " + parentUrl.concat(f.select("a").attr("href").replace("..", "")));

					Elements r = f.getElementsByTag("p");

					r.forEach((j) -> {
						
						try {
							String address = j.tagName("strong").text();
							Pattern pattern = Pattern.compile("\\d{3}.\\d{3}.\\d{4}");
							Matcher matcher = pattern.matcher(address);
							if (matcher.find()) {
								int len = matcher.group(0).length();
								address = address.substring(0, address.length() - len);
								System.out.println(" ----------->  " + address);
							    System.out.println( " Grouper " + matcher.group(0));
							    dataSet.setPhoneNumber(matcher.group(0));
							}
							new GeoCodingApi(address,dataSet);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					cineBistroKeyDataSet.add(dataSet);
				});
			});

		}
	   SheetLocalWriter.writeXLSXFile("CineBistro.xlsx",cineBistroKeyDataSet);
	}
}
