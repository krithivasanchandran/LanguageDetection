package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Fridley_LocData {

	static List<String> theatreUrls = new ArrayList<String>();

	public static void main(String args[]) throws IOException, InterruptedException {

		String root = "http://www.fridleytheatres.com/";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.fridleytheatres.com/locations");

		CommonUtils.checkDoc(doc, Theatres_Fridley_LocData.class);

		Elements e = doc.select("div#locations-list");

		e.forEach((a) -> {

			Elements url = a.getElementsByTag("figcaption");

			url.forEach((m) -> {
				System.out.println(" Address ----->  " + m.text());
				System.out.println("Website  ----> " + m.select("a[href]").attr("href"));
				theatreUrls.add(root.concat(m.select("a[href]").attr("href")));
			});
		});

		theatreUrls.parallelStream().forEach((action) -> {

			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

			CommonUtils.checkDoc(document, Theatres_Fridley_LocData.class);

			Elements el = document.select("div#theater-info");

			el.forEach((a) -> {

				Elements url = a.select("section#theater-tabs");

				url.forEach((m) -> {
					System.out.println("phone  ----> " + m.select("div#info-right").text());
				});
			});
		});
	}

}
