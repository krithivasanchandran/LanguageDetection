package com.locdata.theatres.scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.theatres.entity.ScotiaBankJson;
import com.locdata.theatres.entity.ScotiaBankJson.Record;
import com.locdata.theatres.entity.ScotiaBankJson.TheatreDetails;


/*
 * Using ajax to fetch the theatre details page URL : view-source:https://cineplex.com/search-2015?search-query=Scotiabank+Theatre
 * 
 * Need manual copy pasting of values to excel sheet
 */

public class Theatres_ScotiaBank_LocData {
	
	public static Set<GeoCodeEntityCarrierToExcelWriter> scotiabankKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

public static void main(String args[]) throws IOException, InterruptedException {
    String[] urlset = {"https://search.cineplex.com/api/Search/Search?query=Scotiabank%20Theatre&from=theatredetails&siteFilter=www.cineplex.com&[0].Count=1&[0].Type=Value&[0].Name=Language&[0].Value=English&lang=en-us",
                       "https://search.cineplex.com/api/Search/Search?query=Scotiabank%20Theatre&from=theatredetails&siteFilter=www.cineplex.com&page=2&lang=en-us"};
    for(String u : urlset){
    	fireScotiaAjaxRequest(u);
    }
	SheetLocalWriter.writeXLSXFile("ScotiaTheatre.xlsx",scotiabankKeyDataSet);
}
		
private static void fireScotiaAjaxRequest(String scotiaurl){

	try{
		
		URL url = new URL(scotiaurl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		StringBuilder builder = new StringBuilder();
				
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				builder.append(output);
			}

			conn.disconnect();
			String scotiaApiResponse = builder.toString();
			extractJsonEntities(scotiaApiResponse);
			
	}catch(MalformedURLException malformedexception) {
		
		System.out.println(malformedexception.getMessage() + "On Error " + ScraperLogic.class.getSimpleName() + " ::: request URL is not a HTTP or HTTPS URL");
        
	}catch (IOException e) {
		
        System.out.println(e.getMessage() + "On Error " + ScraperLogic.class.getSimpleName() + " ::: IOException on URL fetch" + new Date().toString());
	}
}

private static void extractJsonEntities(String jsonResponse) throws JsonParseException, JsonMappingException, IOException{
	
	ObjectMapper mapper = new ObjectMapper();
	
	/*
	 * Scotia Bank API
	 */
	
	ScotiaBankJson scotiaResponse = mapper.readValue(jsonResponse, ScotiaBankJson.class);
	
		/*
		 * Result could have just one array value 
		 */
		TheatreDetails result =  scotiaResponse.getTheatreDetails();
		/*
		 * Writing data from json to pojo 
		 */
		 
		ArrayList<Record> record = result.getRecords();
		
		for(Record r : record){
			final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();

			int storenumber = r.getAllMeta().getId();
			String storename = r.getAllMeta().getTitle();
			String address = r.getAllMeta().getTheatreAddress();
			String phone = r.getAllMeta().getTheatrePhone();
			
			g.setStorenumber(storenumber);
			g.setStorename(storename);
			g.setAddress(address);
			g.setPhoneNumber(phone);
			
			try {
				new GeoCodingApi(g.getAddress(), g);
			} catch (Exception e1) {
			   System.out.println(e1.getMessage());
			}
			scotiabankKeyDataSet.add(g);
			System.out.println(" Theatre Address " + r.getAllMeta().getTheatreAddress());
		}
    }
}