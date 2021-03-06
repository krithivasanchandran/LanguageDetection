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

public class Theatres_ShowCase_LocData {
	
	public static Set<GeoCodeEntityCarrierToExcelWriter> showCaseKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

public static void main(String args[]) throws IOException, InterruptedException {
		
		String root = "https://www.showcasecinemas.com";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.showcasecinemas.com/theatres");

		CommonUtils.checkDoc(doc, Theatres_ShowCase_LocData.class);

		Elements e = doc.select("div#block-system-main");
		
		e.forEach((a) -> {
			a.select(".theatres-overview").forEach((peter) ->{
			   peter.select(".row.locations-wrap").forEach((t)-> {
				   t.select(".col-sm-3.inner").forEach((p) -> {
					   final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();
					   Elements ele = p.select("p");
					   for(Element r : ele){
						   String storename =  r.select("strong[itemprop=name]").text();
						   System.out.println(storename);
						   g.setStorename(storename);
						   //System.out.println( " address  " + p.tagName("p").tagName("span").text());
						   String address = r.select("span[itemprop=address]").text();
						   g.setAddress(address);
						   System.out.println(" Address ::::  "+ address);
						   
						   try {
								new GeoCodingApi(g.getAddress(), g);
							} catch (Exception e1) {
							   System.out.println(e1.getMessage());
							}
						   showCaseKeyDataSet.add(g);
					   }
				  });
			    });
			});
		});
		SheetLocalWriter.writeXLSXFile("showCaseTheatre.xlsx",showCaseKeyDataSet);
}
}
