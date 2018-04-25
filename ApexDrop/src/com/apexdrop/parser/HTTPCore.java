package com.apexdrop.parser;

import java.io.IOException;

import javax.net.ssl.SSLEngineResult.Status;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/*
 * Ping Test Happens before jsoup makes a connection
 */

public class HTTPCore {
	
	public static boolean pingTest(String url) throws IOException{
		CloseableHttpResponse response=null;
		try{
		 CloseableHttpClient httpclient = HttpClients.createDefault();
		 
		 RequestConfig requestConfig = RequestConfig.custom()
			        .setSocketTimeout(8000)
			        .setConnectTimeout(8000)
			        .build();
		 
		 HttpGet getRequest = new HttpGet(url);
		 getRequest.setConfig(requestConfig);
		 
		 response = httpclient.execute(getRequest);
		 
		 return (response.getStatusLine().getStatusCode() == 200);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}finally{
			if(response!=null){
				response.close();
			}
		}
		return false;
	}

}
