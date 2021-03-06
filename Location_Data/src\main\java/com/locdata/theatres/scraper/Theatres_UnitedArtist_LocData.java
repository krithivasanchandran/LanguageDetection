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

// Filter UA Theatres 

public class Theatres_UnitedArtist_LocData {
	static Set<String> websiteUrls = new HashSet<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> unitedArtistKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();
	
	public static void main(String args[]) throws IOException {
			
			String root = "https://www.regmovies.com";
			
			Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.regmovies.com/theaters");
			CommonUtils.checkDoc(doc, Theatres_Edwards_LocData.class);

			//Selecting the fourth table
			Elements t = doc.select("main.content-container");

			for(Element p : t){
				
				Elements theatreUrl = p.select(".grid.grid-2-col.grid-1-col-xs");
				
				theatreUrl.forEach((action) -> {
					Elements tags = action.select(".list-unstyled.state-venue-list a");
					
					for(Element r : tags){
						if(r.text().contains("UA")){
							System.out.println(r.text());
							websiteUrls.add(root.concat(r.attr("href")));
						}
					}
				});
			}
			
			websiteUrls.forEach((tbag) -> {
				
				System.out.println(" URLS : ::::: ------------> " + tbag);
				//https://www.regmovies.com/theaters/ua-arden-fair-6/3541
				String rawText = tbag.split("theaters/")[1];
				String storename = rawText.split("/")[0];
				String storenumber = rawText.split("/")[1];
				final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();
				
				g.setStorename(storename);
				g.setStorenumber(Integer.parseInt(storenumber));

				Document document = ScraperLogic.Scraper.fetchHtmlContents(tbag);
				CommonUtils.checkDoc(document, Theatres_Edwards_LocData.class);
				Elements address = document.body().select(".info-cell .address");
				Elements phone = document.body().select(".info-cell .phone");
				String addr = address.text().replace("view on google maps","");
				g.setAddress(addr);
				System.out.println(addr);
				g.setPhoneNumber(phone.text());
				System.out.println(" Phone --->" + phone.text());
				
				try {
					new GeoCodingApi(g.getAddress(), g);
				} catch (Exception e1) {
				   System.out.println(e1.getMessage());
				}
				unitedArtistKeyDataSet.add(g);
			});
			SheetLocalWriter.writeXLSXFile("UnitedArtistTheatre.xlsx",unitedArtistKeyDataSet);
		}	
}
