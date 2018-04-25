package com.locdata.theatres.scraper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.dataruvi.nlp.NERDemo;
import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.theatres.entity.EntityTheatre;
import com.locdata.utils.common.CommonUtils;

/*
 * http://www.altitudetrampolinepark.com/
 * 
 * Locations -- 1 . Current locations
 *  			2.  Under Construction
 *  
 *  Locations Include -- > England , Norway -- Only one location
 *                            \
 *    						   \- Child Locations 
 *    
 *    Few locations have totally different pages need to look into that
 *    
 *    Sitemap : http://www.altitudetrampolinepark.com/sitemap.xml
 *    
 *    Failed URLs -----------> http://altitudepuertorico.com
 	Failed URLs -----------> http://altitudepuertorico.com/bayamon/
 	Failed URLs -----------> http://altitudecstat.com/
 	Failed URLs -----------> https://www.gojumpin.com/locations/tonbridge
 	Failed URLs -----------> http://www.altitudetrampolinepark.com/fayetteville/
 	Failed URLs -----------> http://altitudesa.com/
 	Failed URLs -----------> http://altitudemarysville.com/
 	Failed URLs -----------> http://www.altitudemonroe.com/
 	Failed URLs -----------> http://www.altituderidgeland.com/
 	Failed URLs -----------> http://www.altitudegulfport.com/
 *    
 */

public class Altitude_Trampoline_Park {

	static List<EntityTheatre> address = new ArrayList<EntityTheatre>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> trampolinePark = new HashSet<GeoCodeEntityCarrierToExcelWriter>();
	static LinkedList<String> parentLocationSeeds = new LinkedList<>();
	static List<String> failed = new ArrayList<String>();
	
	public static void main(String args[]) throws IOException {

		String parentUrl = "http://www.altitudetrampolinepark.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents(parentUrl);

		CommonUtils.checkDoc(doc, Altitude_Trampoline_Park.class);

		Elements parent = doc.select(".locations-list-active");

		parent.stream().forEach((t) -> {
			Elements r = t.select("li a");
			r.parallelStream().forEach((q) -> {
				String href = q.attr("href");
				parentLocationSeeds.add(href);
			});
		});
		
		System.out.println("parentLocationSeeds .size :: " + parentLocationSeeds.size());

		if (parentLocationSeeds.size() == 0) {
			System.out.println(" CRITICAL :: Website DOM HAS CHanged" + Altitude_Trampoline_Park.class.getName());
		}

		parentLocationSeeds.stream().forEach((childseed) -> {
			
			if (childseed.equalsIgnoreCase("http://altitudedenton.com/")
					|| childseed.equalsIgnoreCase("http://www.altituderichardson.com/")
					|| childseed.equalsIgnoreCase("http://www.altitudenorway.com/")
					|| childseed.equalsIgnoreCase("https://www.gojumpin.com/locations/enfield")
					|| childseed.equalsIgnoreCase("https://www.gojumpin.com/wk/warwick/")
					|| childseed.equalsIgnoreCase("https://www.gojumpin.com/contact/slough/")) {
				System.out.println(" Inside here");

			} else {

				System.out.println(" Now Scraping URL :: -----------------> " + childseed);

				Document document = ScraperLogic.Scraper.fetchHtmlContents(childseed);
				CommonUtils.checkDoc(document, Altitude_Trampoline_Park.class);

				Elements iowa = document.select("footer#footer");
				if(iowa.isEmpty() || iowa == null){
					failed.add(childseed);
				}
				Elements footer = iowa.select(".footer-top");

				footer.stream().forEach((t) -> {
					
					final GeoCodeEntityCarrierToExcelWriter dataSet = new GeoCodeEntityCarrierToExcelWriter();

					Elements q = t.select(".col-sm-4");
					Elements block = q.tagName("p");

					String p = block.first().html();
				
					/* <p>Altitude Trampoline Park - South Austin<br>6800 West Gate Blvd, Suite 100<br>
					 * Austin, TX 78745<br>(512) 814-0090</p>
					 * Split based on <br> tag and replace all the <p> tag.
					 */
					
					String[] splitText = p.replace("<p>", "").replace("</p>", "").split("<br>");
					String fileName = "/Users/krichandran/Desktop/Trampoline.txt";
					BufferedWriter writer;
					try {
						writer = new BufferedWriter(new FileWriter(fileName, true));
						writer.write("****************************************************************** \n");
						StringBuffer address = new StringBuffer();
						
						for(int i=0;i<splitText.length;i++){
							if(i==1 || i == 2){
								address.append(splitText[i]);
							}
							if(i == 3){
								dataSet.setPhoneNumber(splitText[i]);
							}
							writer.write("--> " + splitText[i] +"\n");
							System.out.println("--> " + splitText[i] +"\n");
						}
						new GeoCodingApi(address.toString(),dataSet);

						writer.write("\n");
						writer.write(" ****************************************************************** \n");
						writer.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					trampolinePark.add(dataSet);
				});
		
	    } 
		});
		
		SheetLocalWriter.writeXLSXFile("ALTITUDE_TRAMPOLINE_PARK.xlsx",trampolinePark);
		failed.stream().forEach((m) -> {
			System.out.println(" Failed URLs -----------> "+m);
		});
	}
}