package com.locdata.theatres.scraper;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * Different website - Just structured on tables
 * There are 2 theatres - Cobb theatres other is cinebistro.com
 */

public class Theatres_Cobb_LocData {
	
	static Set<String> loc_data = new HashSet<String>();

	public static void main(String args[]) {
		
		String root = "https://www.cobbtheatres.com/";
		
		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.cobbtheatres.com/theatres.asp");
		CommonUtils.checkDoc(doc, Theatres_Cobb_LocData.class);

		//Selecting the fourth table
		Element table = doc.select("table").get(4);

		Elements r = table.select("tbody tr a");
		
		for(Element p : r){
			
			String theatreUrl = p.attr("href");
			
			if((theatreUrl.contains("https")) || (theatreUrl.contains("http"))){
			}else{
				if(!theatreUrl.contains(" ") || !theatreUrl.contains(null)) theatreUrl = root.concat(theatreUrl);
			}
			//System.out.println(" href ----------->  " + theatreUrl);
			loc_data.add(theatreUrl);
		}
		
		loc_data.forEach((action) ->
		{
			//cobb theatres head quarters
			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);
			CommonUtils.checkDoc(document,Theatres_Cobb_LocData.class);
			
			if(action.contains("cobbtheatres")){
				
				if(action.trim().equalsIgnoreCase("https://www.cobbtheatres.com/")){
					
					System.out.println("2000-B South Bridge Parkway, Suite 100, Birmingham, AL 35209 | FAX (205) 802-7771");
				}
				
				if(document.select("table").size() > 0){
					
					Element t = document.select("table").get(5);
					
					Element re = t.select("tr").get(0);
					
					if(re != null){
						System.out.println(" --> " +re.select("div#theatreInfo p").text());
					}
				}
			}else if(action.contains("cinebistro.com")){
				
				Elements t = document.body().select("div#content");
				
				t.forEach((theatrelist) -> {
					
					Elements k = theatrelist.select(".vcard");
					
					for(Element q : k){
						 
						if(q.select(".fn.org").text().length() > 0){
							
							System.out.println(" cinebistro Address =======> "+ q.select(".fn.org").text());
							 Elements inner = q.select(".adr");
							 for(Element dom : inner){
								 String address = dom.text();
							     System.out.println(" cinebistro Address ------> " +address);
							 }
					}
					}
					});
			}
		});
	}
}