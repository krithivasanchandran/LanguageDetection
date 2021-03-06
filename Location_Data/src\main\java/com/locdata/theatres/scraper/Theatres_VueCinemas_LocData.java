package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_VueCinemas_LocData {

	static Set<String> loc_data = new HashSet<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> vueCinemasKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws IOException {

		String root = "http://www.cinema-finder.co.uk/cat/vue-cinemas/";
		final Set<String> toVisit = new HashSet<String>() {
			{
				add("http://www.cinema-finder.co.uk/cat/vue-cinemas/");
				add("http://www.cinema-finder.co.uk/cat/vue-cinemas/page/2/");
				add("http://www.cinema-finder.co.uk/cat/vue-cinemas/page/3/");
			}
		};

		toVisit.stream().forEach((itr) -> {

			Document doc = ScraperLogic.Scraper.fetchHtmlContents(itr);
			CommonUtils.checkDoc(doc, Theatres_VueCinemas_LocData.class);

			Elements s = doc.select("div#main");

			s.forEach((a) -> {

				Elements t = a.select(".category-items.clearfix");

				t.parallelStream().forEach((theatrelist) -> {
					Elements k = theatrelist.select("ul li h3 > a");

					for (Element q : k) {
						System.out.println(q.attr("href"));
						loc_data.add(q.attr("href"));
					}
				});
			});
		});

		loc_data.forEach((action) -> {
			boolean flag = false;
			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

			final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();

			if (action.contains("item")) {
				action = action.substring(action.indexOf("vue-cinema-") + "vue-cinema-".length(), action.length())
						.replace("/", "");
				System.out.println("Store name :::::  " + action);
				g.setStorename(action);
			}
			
			if(!CommonUtils.checkDoc(document, Theatres_VueCinemas_LocData.class,flag)){
				
				Elements t = document.body().select("div#main");

				t.forEach((theatrelist) -> {

					Elements k = theatrelist.select("div#content");

					for (Element q : k) {

						q.select(".item-address").forEach((r) -> {

							String address = r.select(".address").next().text();
							System.out.println(" addresss ----------> " + address);
							g.setAddress(address);

							String phone = r.select(".phone").next().text();
							System.out.println("Phone -------> " + phone);
							g.setPhoneNumber(phone);

							try {
								new GeoCodingApi(g.getAddress(), g);
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
					}
					vueCinemasKeyDataSet.add(g);
				});
			}
		});
		SheetLocalWriter.writeXLSXFile("VueCinemasEntertainment.xlsx", vueCinemasKeyDataSet);
	}
}