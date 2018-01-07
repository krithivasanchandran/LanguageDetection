package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Marcus_LocData {
	
	public static void main(String args[]) throws IOException, InterruptedException {
	
			Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.marcustheatres.com/theatre-locations");
	
			CommonUtils.checkDoc(doc,Theatres_Marcus_LocData.class);

			Elements e = doc.select(".main-col");
			
			e.forEach((a) -> {
			
				Elements r = doc.select(".theatres.clearfix");
				
				r.forEach((link) -> {
					
					Elements url = link.select(".theatre-listing");
							
					for(Element t : url){
						for(Element v :  t.select(".theatre-listing__info")){
							
							System.out.println("Website====>  " + "http://www.marcustheatres.com/"+v.select("a").attr("href"));
							System.out.println(" Info Addr =----> " + v.text());
						}
					}
				});
			});
}
}
