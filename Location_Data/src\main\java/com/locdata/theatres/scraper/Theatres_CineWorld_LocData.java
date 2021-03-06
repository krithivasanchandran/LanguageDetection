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

public class Theatres_CineWorld_LocData {
	
	static Set<String> loc_data = new HashSet<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> cineWorldKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException{
		
		String root = "http://www.cinema-finder.co.uk/cat/cineworld-cinemas/";
		final Set<String> toVisit = new HashSet<String>(){{
			add("http://www.cinema-finder.co.uk/cat/cineworld-cinemas/");
			add("http://www.cinema-finder.co.uk/cat/cineworld-cinemas/page/2/");
			add("http://www.cinema-finder.co.uk/cat/cineworld-cinemas/page/3/");
		}};
		
		toVisit.stream().forEach((itr) -> {
			
			Document doc = ScraperLogic.Scraper.fetchHtmlContents(itr);
			CommonUtils.checkDoc(doc,Theatres_CineWorld_LocData.class);
			
			Elements s = doc.select("div#main");
			
			s.forEach((a) -> {
				
				Elements t = a.select(".category-items.clearfix");
				
				t.parallelStream().forEach((theatrelist) -> {
					Elements k = theatrelist.select("ul li h3 > a");
					
					for(Element q : k){
							System.out.println(q.attr("href"));
							loc_data.add(q.attr("href"));
					}
				});
			});
		});
		
		loc_data.forEach((action) ->
		{
			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

			CommonUtils.checkDoc(document,Theatres_CineWorld_LocData.class);
			
			final GeoCodeEntityCarrierToExcelWriter cineWorldMasterData = new GeoCodeEntityCarrierToExcelWriter();
			cineWorldMasterData.setStorename(action.substring(action.indexOf("item/")+"item/".length(), action.length()-1));
			
			System.out.println("Store Name -----------> " + action.substring(action.indexOf("item/")+"item/".length(), action.length()-1));
			
			Elements t = document.body().select("div#main");
			
			t.forEach((theatrelist) -> {
				
				Elements k = theatrelist.select("div#content");
				
				for(Element q : k){
					 
						 System.out.println(" Address ------> " +q.select(".item-address").select("dt.address").next().text());
						 
						 System.out.println(" Phone ------> " +q.select(".item-address").select("dt.phone").next().text());
						 //System.out.println(" Phone number ------- > " + q.select(".item-address").tagName("dd").get(1));
						 cineWorldMasterData.setAddress(q.select(".item-address").select("dt.address").next().text());
						 cineWorldMasterData.setPhoneNumber(q.select(".item-address").select("dt.phone").next().text());
				}
				});
			try {
				new GeoCodingApi(cineWorldMasterData.getAddress(),cineWorldMasterData);
			} catch (Exception e) {
				System.out.println(" Excpetion Thrown at URL :::: " + action);
			}
			cineWorldKeyDataSet.add(cineWorldMasterData);
		});
		  SheetLocalWriter.writeXLSXFile("CineWorldTheatres.xlsx",cineWorldKeyDataSet);
	}
}