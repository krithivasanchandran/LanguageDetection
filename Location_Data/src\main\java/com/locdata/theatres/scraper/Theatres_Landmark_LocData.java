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

public class Theatres_Landmark_LocData {

	static List<String> theatreUrls = new ArrayList<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> landmarkKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException, InterruptedException {
	
			
				Document document = ScraperLogic.Scraper.fetchHtmlContents("https://www.landmarktheatres.com/LocationFindResults?region=");
				CommonUtils.checkDoc(document,Theatres_Landmark_LocData.class);
				
					
					Elements t = document.select(".gridRow.finder-results-cinemas");
					

					for(Element b : t){
						
						final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();

						for(Element v : b.select(".finder-results-cinema")){

							String storeName = v.select(".finder-results-cinema-head").text();
							System.out.println(" Store name -------------> " + storeName);
							g.setStorename(storeName);
							String rawText = v.select(".finder-results-cinema-info").text();

							Pattern pattern = Pattern.compile("\\(\\d{3}\\)\\s\\d{3}-\\d{4}");
							Matcher matcher = pattern.matcher(rawText);
							
							if (matcher.find()) {
								//g.setPhoneNumber(matcher.group(0));
								System.out.println(" Phone number ----------> " + matcher.group(0));
								g.setPhoneNumber(matcher.group(0));
								
								String address = rawText.substring(0,rawText.indexOf(matcher.group(0)));
								System.out.println(" address --------> " + address);
								g.setAddress(address);
								try {
								new GeoCodingApi(g.getAddress(), g);
								} catch (Exception e1) {
									System.out.println(e1.getMessage());
								}
							}
						}
						landmarkKeyDataSet.add(g);
					}
				
			SheetLocalWriter.writeXLSXFile("LandmarkTheatre.xlsx",landmarkKeyDataSet);
	}
}