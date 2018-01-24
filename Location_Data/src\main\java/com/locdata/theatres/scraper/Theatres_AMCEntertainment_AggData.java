package com.locdata.theatres.scraper; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
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


public class Theatres_AMCEntertainment_AggData {

	static List<String> theatres = new ArrayList<String>();
	static List<EntityTheatre> address = new ArrayList<EntityTheatre>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> amcKeyDataSet = new LinkedHashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException {

		String parentUrl = "https://www.amctheatres.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.amctheatres.com/movie-theatres");

		CommonUtils.checkDoc(doc,Theatres_AMCEntertainment_AggData.class);

			
		Element t = doc.getElementById("tabs-tabs");
		Elements t_1 = t.select(".tab-content");

		for (Element r : t_1) {

			Elements re = r.select(".Link.Link--arrow.Link--reversed.Link--arrow--txt--tiny.txt--tiny");

			for (Element k : re) {
				System.out.println(parentUrl.concat(k.attr("href")));
				theatres.add(parentUrl.concat(k.attr("href")));
				System.out.println(k.text());
				// address.add(new EntityTheatre().setCountyName(k.text()));
			}
		}

		ListIterator<String> itr = theatres.listIterator();

		while (itr.hasNext()) {

			Document doc_1 = ScraperLogic.Scraper.fetchHtmlContents(itr.next());

			CommonUtils.checkDoc(doc_1,Theatres_AMCEntertainment_AggData.class);


			Elements tr = doc_1.select(".TheatreInfo");
			System.out.println(tr.size());
			EntityTheatre entity = null;

			for (Element e : tr) {
				entity = new EntityTheatre();
				System.out.println(" -------->>>>>>>>> " + e.select(".Link-text.Headline--h3").text());
				entity.setTheatreStoreName(e.select(".Link-text.Headline--h3").text());
				System.out.println(" ==============> Theatre Website " + e.select("a").attr("href"));
				entity.setTheatreWebsite(parentUrl.concat(e.select("a").attr("href")));
				System.out.println(
						" ==============> address " + e.select(".TheatreInfo-address.Headline--eyebrow").text());
				entity.setAddress(e.select(".TheatreInfo-address.Headline--eyebrow").text());
				
				GeoCodingApi geo = new GeoCodingApi(entity.getAddress(),amcKeyDataSet);
					
						amcKeyDataSet.forEach((action) ->{
							StringBuilder builder = new StringBuilder();
							builder.append(action.getAddress() + " --> " + action.getCity() + " --> " + action.getCountry() + " --> " + action.getCountryCode() + " --> " + action.getCounty()
							+ " --> " + action.getLatitude() + " --> " + action.getLongitude() +" --> " + action.getState() + " --> " + action.getZipcode());
							System.out.println(" builder ::::  " + builder.toString());
						});
						
				}
				        	
			//	address.add(entity);
		}
		SheetLocalWriter.writeXLSXFile("AmcEntertainment.xlsx",amcKeyDataSet);
	}
}