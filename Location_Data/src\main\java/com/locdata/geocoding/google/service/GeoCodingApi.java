package com.locdata.geocoding.google.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.locdata.geocoding.google.service.GeoCodingJsonResponse.AddressComponent;
import com.locdata.geocoding.google.service.GeoCodingJsonResponse.Result;
import com.locdata.scraper.main.ScraperLogic;

/*
 * https://developers.google.com/maps/documentation/geocoding/intro
 * URLs must be properly encoded to be valid and are limited to 8192 characters for all web services. 
 * Be aware of this limit when constructing your URLs.
 * Note that different browsers, proxies, and servers may have different URL character limits as well.
 * 
 * Input -> address , api key
 * 
 * Output : County name : administrative_area_level_2
 * 
 * 
 */

public class GeoCodingApi {
	
	private static final String geocoding = "https://maps.googleapis.com/maps/api/geocode/json?address=";
	private static final String secureapikey = "&key=AIzaSyDL_ikrQBqKuqieCgJyvHUeQRYxT69ZLlw";
	private String geoApiResponse;
	
	private String constructgeourl;
	
	public GeoCodingApi(String address,GeoCodeEntityCarrierToExcelWriter masterData) throws UnsupportedEncodingException{
		if(address.length() < 8192){
			address = address.replaceAll("\\s+", "+");
			this.constructgeourl = geocoding.concat(URLEncoder.encode(address, "UTF-8")).concat(secureapikey);
			System.out.println("Constructed geo code API =====> " + this.constructgeourl);
		}
	fireGeoCodeApi(masterData);
	}
	
	
	private void fireGeoCodeApi(GeoCodeEntityCarrierToExcelWriter masterData){

		try{
			
			URL url = new URL(this.constructgeourl);
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
				extractJsonEntities(geoApiResponse,masterData);
				
		}catch(MalformedURLException malformedexception) {
			
			System.out.println(malformedexception.getMessage() + "On Error " + ScraperLogic.class.getSimpleName() + " ::: request URL is not a HTTP or HTTPS URL");
            
		}catch (IOException e) {
			
            System.out.println(e.getMessage() + "On Error " + ScraperLogic.class.getSimpleName() + " ::: IOException on URL fetch" + new Date().toString());
		}
	}
	
	private void extractJsonEntities(String jsonResponse,GeoCodeEntityCarrierToExcelWriter pojo) throws JsonParseException, JsonMappingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		
		/*
		 * Geo Places API
		 */
		
		GeoCodingJsonResponse georesponse = mapper.readValue(jsonResponse, GeoCodingJsonResponse.class);
		
		
		if(!georesponse.getStatus().trim().equalsIgnoreCase("ZERO_RESULTS")){
			
			/*
			 * Result could have just one array value 
			 */
			Result result = georesponse.getResults().get(0);
			/*
			 * Writing data from json to pojo 
			 */
			 
			
			        	        if(GeoCodingStatusCodes.getErrorCodes().contains(georesponse.getStatus().trim())){
			        	        	String str = georesponse.getStatus().trim();
			        	        	System.out.println( " Ennum response  " + GeoCodingStatusCodes.valueOf(str).name());
			        	        	
			        	        	System.out.println(" Size of address components  " + result.getAddressComponents().size());
			        	        	
			        	        	for(int j=0;j<result.getAddressComponents().size();j++){
			        	        		AddressComponent addresscomponent = result.getAddressComponents().get(j);
			        	        		List<String> exor = addresscomponent.getTypes();
			        	        		
			        	        		exor.parallelStream().forEach((action) ->{ 
				        	        		switch(action){
				        	        		
				        	        		case "administrative_area_level_1":
				        	        			final String state = addresscomponent.getLongName();
				        	        			System.out.println(" State ---> " + state);
				        	        			pojo.setState(state != null ? state : " ");
				        	        			break;
				        	        		
				        	        		case "administrative_area_level_2":
				        	        			final String countyname = addresscomponent.getLongName();
				        	        			System.out.println(" countyname " + countyname);
				        	        			pojo.setCounty(countyname!=null ? countyname : " ");
				        	        			break;
				        	        			
				        	        		case "locality":
				        	        			final String city = addresscomponent.getLongName();
				        	        			System.out.println(" city  " + city);
				        	        			pojo.setCity(city != null ? city : " ");
				        	        			break;
				        	        		
				        	        		case "country":
				        	        			final String country =addresscomponent.getLongName();
				        	        			final String countryCode =  addresscomponent.getShortName();
				        	        			System.out.println(" country " + country );
				        	        			System.out.println( "countrycode " + countryCode );
				        	        			pojo.setCountry(country != null ? country : " ");
				        	        			pojo.setCountryCode(countryCode != null ? countryCode : " ");
				        	        			break;
				        	        		
				        	        		case "postal_code":
				        	        			final String zipcode = addresscomponent.getLongName();
				        	        			System.out.println(" zipcode " + zipcode );
				        	        			pojo.setZipcode(zipcode != null ? zipcode : " ");
				        	        			break;
				        	        		}
			        	        		});
			        	        	}
			        	        	
			        	        	 System.out.println( " formatted Json response " +  result.getFormattedAddress());
			        	        	 pojo.setAddress(result.getFormattedAddress());
			        	    		 pojo.setLatitude(result.getGeometry().getLocation().getLat());
			        	    		 pojo.setLongitude(result.getGeometry().getLocation().getLng());
			        	    		 pojo.setLocationtype(result.getGeometry().getLocationType());
			        	    		 pojo.setPlaceId(result.getPlaceId());
			        	    		 
			        	    		 // Master Data Set That is to be written to Excel Writer
			        	        } 
			        	        
			        	        //Commenting places API request for now.
			        	        
//			        	        synchronized(this){
//			        	        	new PlacesAPI(pojo);
//			        	        }
			        	        
			
		}
		
		
	}
	
	 public static <T> T getLastElement(final Iterable<T> elements) {
	        final Iterator<T> itr = elements.iterator();
	        T lastElement = itr.next();

	        while(itr.hasNext()) {
	            lastElement=itr.next();
	        }
	        return lastElement;
	    }
}