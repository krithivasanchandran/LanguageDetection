package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.theatres.entity.EntityTheatre;
import com.locdata.utils.common.CommonUtils;

/* 
 *    Included Fields: 
 *    ---------------
	  Store Name
      Address
      City
	  State
	  Zip Code
	  Phone Number
	  Latitude
	  Longitude
	  Country
	  Country Code
	  County
	  Geo Accuracy
 *
 */

public class Theatres_BowTieCinemas_AggData {

	static List<EntityTheatre> address = new ArrayList<EntityTheatre>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> bowTieKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException {

		EntityTheatre entitytheatre = null;

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.bowtiecinemas.com/locations");
		
		CommonUtils.checkDoc(doc,Theatres_BowTieCinemas_AggData.class);

		Elements element = doc.select("article#post-62011");

		for (Element r : element) {

			Elements d_1 = element.select(".locations");

			for (Element f : d_1) {

				Elements attr = f.getElementsByTag("a");

				for (Element f1 : attr) {

					entitytheatre = new EntityTheatre();
					entitytheatre.setTheatreWebsite(f1.attr("href"));
					entitytheatre.setTheatreStoreName(f1.text());
					address.add(entitytheatre);
				}
			}
		}

		if (address.size() > 0) {

			ListIterator itr = address.listIterator();
			
			while (itr.hasNext()) {

				String website = ((EntityTheatre) itr.next()).getTheatreWebsite();
				
				Document doc_1 = ScraperLogic.Scraper.fetchHtmlContents(website);

				CommonUtils.checkDoc(doc_1,Theatres_BowTieCinemas_AggData.class);
				
				final GeoCodeEntityCarrierToExcelWriter pojo = new GeoCodeEntityCarrierToExcelWriter();
				
				if(doc_1 != null){
					
					Elements parent = doc_1.select("section#content");

					for (Element p : parent) {

						Elements address_l = p.getElementsByTag("span");

						for (Element getaddress : address_l) {
							
							String storeName = getaddress.select("span[itemprop=name]").text();
							
							if(storeName != null && !storeName.isEmpty()){
								pojo.setStorename(storeName);
								System.out.println(" Store name =============> " + storeName);
							}

							Elements s = getaddress.select("span[itemprop=address]");

							for (Element a : s) {
								if (a.text() != null) {

									System.out.println(a.text());
									new GeoCodingApi(a.text(),pojo);
								}
							}

							Elements s_1 = getaddress.select("span[itemprop=telephone]");

							for (Element a1 : s_1) {
								if (a1.text() != null) {

									System.out.println(" TelepHone ------------------------> " + a1.text());
									pojo.setPhoneNumber(a1.text());
								}
							}
						}
					}
				}

				bowTieKeyDataSet.add(pojo);
			}
		}
		
		SheetLocalWriter.writeXLSXFile("BowTieEntertainment.xlsx",bowTieKeyDataSet);
	}
}
