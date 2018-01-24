package com.locdata.theatres.scraper; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.geocoding.google.service.GeoCodingJsonResponse;
import com.locdata.geocoding.google.service.GeoCodingJsonResponse.Result;
import com.locdata.geocoding.google.service.GeoCodingStatusCodes;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.theatres.entity.EntityTheatre;
import com.locdata.utils.common.CommonUtils;


public class Theatres_AMCEntertainment_AggData {

	static List<String> theatres = new ArrayList<String>();
	static List<EntityTheatre> address = new ArrayList<EntityTheatre>();

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
				break;
				// address.add(new EntityTheatre().setCountyName(k.text()));
			}
			break;
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
				
				GeoCodingApi geo = new GeoCodingApi(entity.getAddress());
				//System.out.println(geo.fireGeoCodeApi());
				 String returnJsonGeo = geo.fireGeoCodeApi();
				ObjectMapper mapper = new ObjectMapper();
				GeoCodingJsonResponse georesponse = mapper.readValue(returnJsonGeo, GeoCodingJsonResponse.class);
				System.out.println(" Size of results -----> " + georesponse.getResults().size());
				System.out.println(georesponse);
				Result result = georesponse.getResults().get(0);
				System.out.println(result.toString());
				
				        	        System.out.println( " formatted Json response " +  result.getFormattedAddress());
				        	        System.out.println( " latitude :: " + result.getGeometry().getLocation().getLat());
				        	        System.out.println( " longitude :: " + result.getGeometry().getLocation().getLng());
				        	        System.out.println( " location_type :: " + result.getGeometry().getLocationType());
				        	        System.out.println(" Place Id ::: " + result.getPlaceId());
				        	        System.out.println(" Status ::: " + georesponse.getStatus());
				        	        
				        	        if(GeoCodingStatusCodes.getErrorCodes().contains(georesponse.getStatus().trim())){
				        	        	String str = georesponse.getStatus().trim();
				        	        	System.out.println( " Ennum response  " + GeoCodingStatusCodes.valueOf(str).name());
				        	        } 
				        	
				System.exit(0);
			//	address.add(entity);
			}
		}
		System.out.println("Fetching the details");
		System.out.println(Arrays.toString(address.toArray()));
	}
}