package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * Same logic as the cinemark theatres
 */

public class Theatres_Santikos_LocData {
	
	static List<String> theatreUrls = new ArrayList<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> santikosKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException, InterruptedException {
	
			Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.fandango.com/movie-theaters/santiko-theatres");
	
			CommonUtils.checkDoc(doc,Theatres_Santikos_LocData.class);

			Elements e = doc.select("div#GlobalBody_singleChain");
			
			e.forEach((a) -> {
				Elements r = doc.select(".theatersname");
				
				r.forEach((link) -> {
					
					Elements url = link.select(".theaterlink");
					
					url.forEach((m) -> {
						System.out.println(m.text());
						System.out.println(" ----> " + m.select("a[href]").attr("href"));
						theatreUrls.add(m.select("a[href]").attr("href"));
					});
				});
			});
			
			if(theatreUrls.size() > 0){
				theatreUrls.forEach((root) -> {
					
					Document document = ScraperLogic.Scraper.fetchHtmlContents(root);
					
					CommonUtils.checkDoc(document,Theatres_Santikos_LocData.class);
					
					final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();
					
					String storename  = root.split("com/")[1].split("_")[0];
					System.out.println(" Store name  -------------> " + storename);
					g.setStorename(storename);
										
						Elements r = document.select(".tdp.js-tdp");
						
						r.forEach((link) -> {
							
							Elements url = link.select(".subnav__link-item.subnav__link-item--address");
							
							url.forEach((m) -> {
								System.out.println(m.text());
								g.setAddress(m.text());
								System.out.println(" Phone number ---> " + link.select(".subnav__link-item").last().text());
								g.setPhoneNumber(link.select(".subnav__link-item").last().text());
							});
						});
						try {
							new GeoCodingApi(g.getAddress(), g);
						} catch (Exception e1) {
						   System.out.println(e1.getMessage());
						}
						santikosKeyDataSet.add(g);
					});
			}
			SheetLocalWriter.writeXLSXFile("SantikosTheatre.xlsx",santikosKeyDataSet);
	}
}