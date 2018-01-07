package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.theatres.entity.EntityTheatre;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Cinema21Indonesia_AggData {

	static List<EntityTheatre> entityCoupler = new ArrayList<EntityTheatre>();
	public static Set<String> urls = new HashSet<String>();

	public static void main(String args[]) throws IOException {

		String rootUrl = "https://www.amctheatres.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("http://www.21cineplex.com/theaters");

		CommonUtils.checkDoc(doc,Theatres_Cinema21Indonesia_AggData.class);

		Elements parentLocation = doc.select(".table-theater");

		parentLocation.forEach((body) -> {

			// Elements el = body.select("tr[data-city=3]");

			Elements pm = body.getElementsByAttribute("href");

			pm.forEach((itr) -> {

				urls.add(itr.attr("href"));
				System.out.println("----> " + itr.attr("href"));
			});
		});

		if (urls.size() > 0) {

			urls.forEach((url) -> {
				try {

					Document connect = ScraperLogic.Scraper.fetchHtmlContents(url);

					CommonUtils.checkDoc(connect,Theatres_Cinema21Indonesia_AggData.class);
					
					Elements parent = connect.select("div#content");
					EntityTheatre entity;
					for (Element p : parent) {

						entity = new EntityTheatre();
						Element col = p.select(".col-m_462").last();
						String fullText = col.select(".col-content").first().text().split("HTM :")[0];
						String[] fullText_1 = fullText.split("PHONE :");
						entity.setAddress(fullText_1[0]);
						entity.setPhoneNumber(fullText_1[1]);
						System.out.println(col.select(".col-content").first().text().split("HTM :")[0]);
						entityCoupler.add(entity);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}
}