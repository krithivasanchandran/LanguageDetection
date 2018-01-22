package com.locdata.ajax.request.response.builder;

import java.net.HttpURLConnection;
import java.net.URL;

public class AjaxResponseBuilder {

	public static HttpURLConnection generateRequest(String sURL, int timeout){
		
		try{
			 URL url = new URL(sURL);
	         HttpURLConnection request = (HttpURLConnection) url.openConnection();
	         request.setReadTimeout(timeout);
	         return request;
		}catch(Exception ex){
			ex.getMessage();
			System.out.println("Exception in " + ex.getClass().getName());
		}
		return null;
	}
}
