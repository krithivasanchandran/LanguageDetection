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

public class Theatres_Starplex_LocData {
	
	public static Set<GeoCodeEntityCarrierToExcelWriter> starplexKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

public static void main(String args[]) throws IOException, InterruptedException {
		
		Set<String> theatreurls = new HashSet<String>();
	
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.movieticketsandshowtimes.com/starplex-cinemas/movietheaterchain/");

		CommonUtils.checkDoc(doc, Theatres_Starplex_LocData.class);

		Elements e = doc.select("div#wrapper");
		
		e.forEach((a) -> {
				
			for(Element peter : a.select("div#movie-chain-list-main")){
				
				for(Element x : peter.select("a")){
					System.out.println("Website ---> " + x.attr("href"));
					theatreurls.add(x.attr("href"));
				}
			 }
			});
		
		theatreurls.forEach((action) -> {

			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

			CommonUtils.checkDoc(document, Theatres_Starplex_LocData.class);
			
			final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();

			String raw = action.split(".com/")[1].split("/")[0];
			String storename = raw.split("/")[0];
			storename = storename.substring(0, storename.lastIndexOf("-"));
			System.out.println(" Store name " + storename);
			g.setStorename(storename);
			raw = raw.substring(raw.lastIndexOf("-")+1, raw.length());
			raw = raw.replace("l","");
			System.out.println(" storenumber -----> " + raw);
			g.setStorenumber(Integer.parseInt(raw));
			
			Elements element = document.select("div#wrapper");
			
			element.forEach((a) -> {
				
				for(Element peter : a.select("div#street")){
					
					for(Element x : peter.select("span#phone")){
						System.out.println("Phone ---> " + x.text());
						g.setPhoneNumber(x.text());
					}
					
					System.out.println("Address =========> " + peter.getElementsByTag("span").first().text());
					g.setAddress(peter.getElementsByTag("span").first().text());
					try {
						new GeoCodingApi(g.getAddress(), g);
					} catch (Exception e1) {
					   System.out.println(e1.getMessage());
					}
				}
				});
			starplexKeyDataSet.add(g);
		});
		SheetLocalWriter.writeXLSXFile("StarplexTheatre.xlsx",starplexKeyDataSet);
		}
   }