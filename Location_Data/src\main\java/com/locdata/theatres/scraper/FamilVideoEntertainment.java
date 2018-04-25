package com.locdata.theatres.scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jsoup.nodes.Document;

import com.dataruvi.SSL.SSLHTTPPostRequest;
import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.theatres.entity.FamilVideoJsonResponse;
import com.locdata.utils.common.CommonUtils;

/*
 * List of Video Rental Shops - https://en.wikipedia.org/wiki/Video_rental_shop
 * 
 * Store Locator : https://www.familyvideo.com/storelocator/ 
 * POST REQUEST : latitude , longitude and Radius
 * Return Type : JSON Data
 * 
 * POST REQUEST URL :: https://www.familyvideo.com/storelocator/index/loadstore/
 * 
 * SSL Contect - POST Request to given parameter :
 * 
 * 	"radius", "16090003.4"
 *	"latitude", "41.4925374"
 *	"longitude", "-99.90181310000003"
 */

public class FamilVideoEntertainment {

	public static Set<GeoCodeEntityCarrierToExcelWriter> familyVideoKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException {

		// Post Request has to be iterated over 8 different pages
		// Crawl Happens Based on the page limit

		int initialPage = 1;
		int counter = 1;
		final String root = "https://www.familyvideo.com/";

		while (counter <= initialPage) {

			String url = "https://www.familyvideo.com/storelocator/index/loadstore/";
			Map<String, String> postRequestParameters = new ConcurrentHashMap<String, String>();
			postRequestParameters.put("radius", "16090003.4");
			postRequestParameters.put("latitude", "41.4925374");
			postRequestParameters.put("longitude", "-99.90181310000003");
			postRequestParameters.put("curPage", counter + "");

			HttpsURLConnection sslResponse = SSLHTTPPostRequest.makePostRequest(url, postRequestParameters);

			try {
				if (sslResponse != null) {
					sslResponse.connect();
					if (sslResponse.getContent() != null) {

						StringBuilder builder = new StringBuilder();
						BufferedReader br = new BufferedReader(new InputStreamReader((sslResponse.getInputStream())));

						String output;
						System.out.println("Reading from the Server.... \n");
						while ((output = br.readLine()) != null) {
							builder.append(output);
						}
						System.out.println("Server Task Completed \n");

						TypeReference<FamilVideoJsonResponse> typeRef = new TypeReference<FamilVideoJsonResponse>() {
						};

						ObjectMapper mapper = new ObjectMapper();

						FamilVideoJsonResponse jsonResponse = mapper
								.readValue(builder.toString() != null ? builder.toString() : null, typeRef);

						initialPage = RetrievePaginationLast(jsonResponse.getPagination());

						jsonResponse.getStoresjson().forEach((t) -> {
							try {
								final GeoCodeEntityCarrierToExcelWriter familyVideoChainMasterResponse = new GeoCodeEntityCarrierToExcelWriter();

								familyVideoChainMasterResponse.setStorenumber(Integer.parseInt(t.getStorelocatorId()));
								familyVideoChainMasterResponse.setStorename(t.getStoreName());
								familyVideoChainMasterResponse.setAddress(t.getAddress());
								familyVideoChainMasterResponse.setPhoneNumber(t.getPhone());
								new GeoCodingApi(familyVideoChainMasterResponse.getAddress(),
										familyVideoChainMasterResponse);

								// Get Store Hours : Use rewrite_request_path
								// key from the json path to create another url
								// and get the response.
								String storeUrl = root.concat(t.getRewriteRequestPath());
								Document document = ScraperLogic.Scraper.fetchHtmlContents(storeUrl);

								CommonUtils.checkDoc(document, FamilVideoEntertainment.class);
								final String hours = getStoreHours(document);

							    familyVideoChainMasterResponse.setStoreHours(hours);

								familyVideoKeyDataSet.add(familyVideoChainMasterResponse);

							} catch (Exception e1) {
								System.out.println(e1.getMessage());
							}
							System.out.println(" Response !!! " + t.getAddress() + "\n");
						});

					}
				} else {
					System.err.println(FamilVideoEntertainment.class + " POST Request => " + url
							+ " Return Error Response SSL Handshake Failed ");
				}
			} catch (Exception ex) {
				System.out.println(FamilVideoJsonResponse.class + " <== Class Name " + ex.getMessage());
				// params.add(new BasicNameValuePair("curPage","1"));
			}
			counter++;
		}
		SheetLocalWriter.writeXLSXFile("FamilyVideoEntertainment.xlsx", familyVideoKeyDataSet);
	}

	private static int RetrievePaginationLast(String paginationString) {
		if (paginationString.contains("data-last-page")) {
			int index = paginationString.indexOf("data-last-page");
			String counter = paginationString.substring(index, paginationString.indexOf(' ', index));
			String pager = counter.split("=")[1];
			int lastpage = Integer.parseInt(pager.split("\"")[1]);
			return lastpage;
		} else {
			System.out.println(
					FamilVideoEntertainment.class + " Exception Parsing the Stores as the Page Count is missing");
		}
		return 0;
	}

	private static String getStoreHours(Document document) {
		final StringBuilder storeHours = new StringBuilder();
		document.select(".page-wrapper").forEach((t) -> {
			t.select("main#maincontent").forEach((m) -> {
				m.select(".columns").forEach((q) -> {
					q.select(".column.main").forEach((e) -> {
						e.select(".views-wrapper").forEach((c) -> {
							c.select(".col-md-4.col-sm-12.col-xs-12.pull-left.table-wrap").forEach((o) -> {
								o.select(".tab_content.open.col-md-12.col-sm-6.col-xs-12.pull-left").forEach((h) -> {
									String s = h.select("div#open_hour").text();
									storeHours.append(s);
									System.out.println(" Store HOURS :: =======> " + s);
								});
							});
						});
					});
				});
			});
		});
	  return storeHours.toString();
	}
}