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

import com.locdata.scraper.main.ScraperLogic;

/*
 * https://developers.google.com/maps/documentation/geocoding/intro
 * URLs must be properly encoded to be valid and are limited to 8192 characters for all web services. 
 * Be aware of this limit when constructing your URLs.
 * Note that different browsers, proxies, and servers may have different URL character limits as well.
 * 
 * Input -> address , api key
 * 
 */

public class GeoCodingApi {
	
	private static final String geocoding = "https://maps.googleapis.com/maps/api/geocode/json?address=";
	private static final String secureapikey = "&key=AIzaSyCdP9jGPN2qTmxtpV9rBkJI2zPpRsp2E-c";
	
	private String constructgeourl;
	
	public GeoCodingApi(String address) throws UnsupportedEncodingException{
		if(address.length() < 8192){
			address = address.replaceAll("\\s+", "+");
			this.constructgeourl = geocoding.concat(URLEncoder.encode(address, "UTF-8")).concat(secureapikey);
			System.out.println("Constructed geo code API =====> " + this.constructgeourl);
		}
	}
	
	
	public String fireGeoCodeApi(){

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
				return builder.toString();
			
		}catch(MalformedURLException malformedexception) {
			
			System.out.println(malformedexception.getMessage() + "On Error " + ScraperLogic.class.getSimpleName() + " ::: request URL is not a HTTP or HTTPS URL");
            
		}catch (IOException e) {
			
            System.out.println(e.getMessage() + "On Error " + ScraperLogic.class.getSimpleName() + " ::: IOException on URL fetch" + new Date().toString());
		}
		return null;
	}
}