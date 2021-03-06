package com.locdata.geocoding.google.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashSet;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.locdata.geocoding.google.service.GeoPlacesApiJsonResponse.Result;
import com.locdata.scraper.main.ScraperLogic;

public class PlacesAPI {

	private static final String placecoding = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
	private static final String secureapikey = "&key=AIzaSyDL_ikrQBqKuqieCgJyvHUeQRYxT69ZLlw";
	private String geoApiResponse;

	private String constructplacesurl;
	
	public PlacesAPI(GeoCodeEntityCarrierToExcelWriter masterPojo) throws UnsupportedEncodingException{
			this.constructplacesurl = placecoding.concat(masterPojo.getPlaceId()).concat(secureapikey);
			System.out.println("Constructed geo code API =====> " + this.constructplacesurl);
			fireplacesApi(masterPojo);
	}
	
	private void fireplacesApi(GeoCodeEntityCarrierToExcelWriter masterPojo){

		try{
			
			URL url = new URL(this.constructplacesurl);
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
				geoApiResponse = builder.toString();
				System.out.println(" Places JSON Response -----> " + geoApiResponse);
				extractJsonEntities(geoApiResponse,masterPojo);
				
		}catch(MalformedURLException malformedexception) {
			
			System.out.println(malformedexception.getMessage() + "On Error " + ScraperLogic.class.getSimpleName() + " ::: request URL is not a HTTP or HTTPS URL");
            
		}catch (IOException e) {
			
            System.out.println(e.getMessage() + "On Error " + ScraperLogic.class.getSimpleName() + " ::: IOException on URL fetch" + new Date().toString());
		}
	}
	
private void extractJsonEntities(String placejsonResponse,GeoCodeEntityCarrierToExcelWriter masterPojo) throws JsonParseException, JsonMappingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		
		/*
		 * Geo Places API
		 */
		
		GeoPlacesApiJsonResponse response = mapper.readValue(placejsonResponse, GeoPlacesApiJsonResponse.class);
		
		/*
		 * Result could have just one array value 
		 */
		Result result = response.getResult();
		/*
		 * Writing data from json to pojo 
		 */
		
		        	        if(GeoCodingStatusCodes.getErrorCodes().contains(response.getStatus().trim())){
		        	        	String str = response.getStatus().trim();
		        	        	System.out.println( " Ennum response  " + GeoCodingStatusCodes.valueOf(str).name());
		        	        	
		        	        	System.out.println(" Places Phone number details ::: " + result.getFormattedPhoneNumber());
		        	        	System.out.println(" Places Phone number details ::: " + result.getInternationalPhoneNumber());
		        	        	
		        	        	masterPojo.setPhoneNumber(result.getFormattedPhoneNumber()+ " -- International : " + result.getInternationalPhoneNumber());
		        	        	
		        	        } 
		      }
}