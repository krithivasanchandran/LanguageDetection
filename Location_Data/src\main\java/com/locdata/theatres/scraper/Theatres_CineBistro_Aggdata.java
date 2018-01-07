package com.locdata.theatres.scraper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_CineBistro_Aggdata {

	public static void main(String args[]) throws IOException {

		String parentUrl = "http://cinebistro.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://cinebistro.com/about-cinebistro/index.php");
		
		CommonUtils.checkDoc(doc,Theatres_CineBistro_Aggdata.class);

		Elements parentLocation = doc.select("div#content");

		for (Element p : parentLocation) {

			Elements ele = p.select(".twothirdCol.colorBg.rightChild");

			ele.forEach((location) -> {

				Elements t = location.select(".locationInfo");

				t.forEach((f) -> {

					System.out.println(
							" -->href >>>>  " + parentUrl.concat(f.select("a").attr("href").replace("..", "")));

					Elements r = f.getElementsByTag("p");

					r.forEach((j) -> {
						System.out.println(" ----------->  " + j.tagName("strong").text());
					});

				});
			});

		}
	}
}
