package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

import javassist.bytecode.analysis.Util;

/*
 * Car Mike Cinemas will be rebranded to AMC Theatres Now
 * 
 * Parent List - AMC Entertainment - Store Number and Store Name from there
 * 
 */

public class Theatres_CarMike_AggData {

	static List<String> urlVisits = new ArrayList<String>();
	static Set<String> address = new HashSet<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> carMikeKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();
	public static boolean criticalThrowException = false;

	public static void main(String args[]) throws IOException {

		String rootUrl = "https://www.amctheatres.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.amctheatres.com/carmike");

		CommonUtils.checkDoc(doc,Theatres_CarMike_AggData.class);
		
		Elements parentLocation = doc.select("article#locations-list");

		for (Element e : parentLocation) {

			Elements t = e.select(".row");

			for (Element ar : t) {
				Elements loc = ar.select("div.ColumnLinkList-col.cleared-col.col-md-3.col-sm-3.col-xs-12 > ul");
				Elements li = loc.select("li");

				li.forEach((s) -> System.out.println(s.text()));

				Elements getUrls = loc.select("li > a");

				getUrls.forEach((s) -> urlVisits.add(rootUrl.concat(s.attr("href"))));

			}
		}

		if (urlVisits.size() > 0) {
			urlVisits.forEach((url -> {

				try {
					
					Document visitPage = ScraperLogic.Scraper.fetchHtmlContents(url);

					CommonUtils.checkDoc(visitPage,Theatres_CarMike_AggData.class);
					final GeoCodeEntityCarrierToExcelWriter masterObject = new GeoCodeEntityCarrierToExcelWriter();
					if(visitPage != null){
						
						Elements pin = visitPage.select(".SiteContent");

						for (Element e : pin) {

							Elements getplace = e.select("p.Headline--sub");

							System.out.println(" website ==> " + url);

							getplace.forEach((p_12) -> {
								System.out.println(" -----> " + p_12.text());
								 try {
									new GeoCodingApi(p_12.text(),masterObject);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								address.add(p_12.text());
							});
						}
						carMikeKeyDataSet.add(masterObject);
					
					}
 
				} catch (Exception e1) {

					System.out.println(e1.getMessage());
					throw new IllegalArgumentException();
				}
			}));
		}
		address.forEach((i) -> System.out.println(" ====> " + i));
		SheetLocalWriter.writeXLSXFile("CarMike.xlsx",carMikeKeyDataSet);
	}
}