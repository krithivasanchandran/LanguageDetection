package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_CineplexOdeon_LocData {
	
	static Set<String> loc_data = new HashSet<String>();
	static int count = 0;
	public static Set<GeoCodeEntityCarrierToExcelWriter> cineplexOdeonKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException{
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://en.wikipedia.org/wiki/List_of_Cineplex_Entertainment_movie_theatres");

		CommonUtils.checkDoc(doc,Theatres_CineplexOdeon_LocData.class);
		
		Element content = doc.getElementById("mw-content-text");
		
		Elements s = content.getElementsByTag("table");
		
		s.forEach((a) -> {
			
			Elements t = a.getElementsByTag("tbody");
			
			t.parallelStream().forEach((theatrelist) -> {
				Elements k = theatrelist.getElementsByTag("tr");
				
				for(Element q : k){
					if(!q.getElementsByTag("td").text().contains("[edit]")){
						
						if(q.select("td").size() > 0){
							
							String filterOdeon = q.select("td").first().text();
							if(filterOdeon.contains("Odeon")){
								System.out.println(filterOdeon);
								loc_data.add(filterOdeon);
							}
						}
					}
				}
			});
		});
		
		loc_data.forEach((action) ->
		{
			action = action.replaceAll("\\s+", "-");
			Document document = ScraperLogic.Scraper.fetchHtmlContents("https://www.cineplex.com/Theatres/TheatreListings?LocationURL="+action+"&Range=50");

			CommonUtils.checkDoc(document,Theatres_CineplexOdeon_LocData.class);
			
			final GeoCodeEntityCarrierToExcelWriter OdeonMasterData = new GeoCodeEntityCarrierToExcelWriter();
			OdeonMasterData.setStorename(action);
			if(document.select(".error-simple").hasText()){
				System.out.println(" Critical !!! Need to change the URL of the request parameter ");
 			}
			
			Elements t = document.body().select("div#Wrapper");
			
			t.forEach((theatrelist) -> {
				
				Elements k = theatrelist.select(".showtime-theatre");
				
				for(Element q : k){
					count++;
								System.out.println(" Address ------> " + q.select(".theatre-address").text());
								try {
									new GeoCodingApi(q.select(".theatre-address").text(),OdeonMasterData);
								} catch (Exception e1) {
									System.out.println(" Exception Printed !!! ");
								}
								System.out.println("  $$$$ "  + q.getElementsByAttributeValue("itemprop", "telephone").text());
								OdeonMasterData.setPhoneNumber(q.getElementsByAttributeValue("itemprop", "telephone").text());
					}
				});
			cineplexOdeonKeyDataSet.add(OdeonMasterData);
		});
		 SheetLocalWriter.writeXLSXFile("CineplexOdeonTheatres.xlsx",cineplexOdeonKeyDataSet);
		System.out.println("Final count - ----> " + count);
	}
}
