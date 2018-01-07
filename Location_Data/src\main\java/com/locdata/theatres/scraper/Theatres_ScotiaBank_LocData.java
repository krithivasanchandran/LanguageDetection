package com.locdata.theatres.scraper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/*
 * Using ajax to fetch the theatre details page URL : view-source:https://cineplex.com/search-2015?search-query=Scotiabank+Theatre
 * 
 * Need manual copy pasting of values to excel sheet
 */

public class Theatres_ScotiaBank_LocData {
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
public static void main(String args[]) throws IOException, InterruptedException {
		
		URL url = new URL("https://search.cineplex.com/api/Search/Get?query=Scotiabank%20Theatre&from=summary&[0].Count=1&[0].Type=Value&[0].Name=Language&[0].Value=English&lang=en-us");
	      JsonParser jsonParser = new JsonParser();
	      JsonObject jsonObject = (JsonObject)jsonParser.parse(new InputStreamReader(url.openStream(), Charset.forName("UTF-8")));
	      String str = jsonObject.get("Summary").toString();
	      String details = str.substring(str.indexOf("TheatreDetails"),str.indexOf("}}]", str.indexOf(str))).toString();
	      System.out.println(details);
}
}