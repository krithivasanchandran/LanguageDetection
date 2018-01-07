package com.locdata.theatres.scraper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/*
 * Still needs work in it
 */

public class Theatres_SilverCity_LocData {

	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
public static void main(String args[]) throws IOException, InterruptedException {
	
	//URL :  https://www.cineplex.com/Utilities/GetTheatresForAutocomplete?jsonp=jQuery110207441148221285268_1514717224891&lang=en&query=SilverCity&_=1514717224894
	//({"results":[{"title":"SilverCity Mission Cinemas","id":"silvercity-mission-cinemas","idVal":"1407","rowType":"theatre"},{"title":"SilverCity Riverport Cinemas","id":"silvercity-riverport-cinemas","idVal":"1409","rowType":"theatre"},{"title":"SilverCity Victoria Cinemas","id":"silvercity-victoria-cinemas","idVal":"1417","rowType":"theatre"},{"title":"SilverCity St. Vital Cinemas","id":"silvercity-st-vital-cinemas","idVal":"2402","rowType":"theatre"},{"title":"SilverCity CrossIron Mills Cinemas and XSCAPE Entertainment Centre","id":"silvercity-crossiron-mills-cinemas-and-xscape-entertainment-centre","idVal":"3150","rowType":"theatre"},{"title":"SilverCity Richmond Hill Cinemas","id":"silvercity-richmond-hill-cinemas","idVal":"7405","rowType":"theatre"},{"title":"SilverCity Newmarket Cinemas and XSCAPE Entertainment Centre","id":"silvercity-newmarket-cinemas-and-xscape-entertainment-centre","idVal":"7407","rowType":"theatre"},{"title":"SilverCity Brampton Cinemas","id":"silvercity-brampton-cinemas","idVal":"7411","rowType":"theatre"},{"title":"SilverCity Burlington Cinemas","id":"silvercity-burlington-cinemas","idVal":"7413","rowType":"theatre"},{"title":"SilverCity London Cinemas","id":"silvercity-london-cinemas","idVal":"7422","rowType":"theatre"}]});
	URL url = new URL("https://www.cineplex.com/Utilities/GetTheatresForAutocomplete?jsonp=jQuery110207441148221285268_1514717224891&lang=en&query=SilverCity&_=1514717224894");
    JsonParser jsonParser = new JsonParser();
    JsonObject jsonObject = (JsonObject)jsonParser.parse(new InputStreamReader(url.openStream(), Charset.forName("UTF-8")));
    System.out.println(jsonObject.toString());
}
}
