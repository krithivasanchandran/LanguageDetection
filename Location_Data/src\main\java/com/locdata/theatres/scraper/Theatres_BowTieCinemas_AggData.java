package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.theatres.entity.EntityTheatre;
import com.locdata.utils.common.CommonUtils;

public class Theatres_BowTieCinemas_AggData {

	static List<EntityTheatre> address = new ArrayList<EntityTheatre>();
	static List<EntityTheatre> entityCoupler = new ArrayList<EntityTheatre>();

	public static void main(String args[]) throws IOException {

		EntityTheatre entitytheatre = null;

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.bowtiecinemas.com/locations/");
		
		CommonUtils.checkDoc(doc,Theatres_BowTieCinemas_AggData.class);

		Elements element = doc.select("article#post-62011");

		for (Element r : element) {

			Elements d_1 = element.select(".locations");

			for (Element f : d_1) {

				Elements attr = f.getElementsByTag("a");

				for (Element f1 : attr) {

					entitytheatre = new EntityTheatre();
					entitytheatre.setTheatreWebsite(f1.attr("href"));
					entitytheatre.setTheatreStoreName(f1.text());
					address.add(entitytheatre);
				}
			}
		}

		if (address.size() > 0) {

			ListIterator itr = address.listIterator();

			while (itr.hasNext()) {

				String website = ((EntityTheatre) itr.next()).getTheatreWebsite();
				
				Document doc_1 = ScraperLogic.Scraper.fetchHtmlContents(website);

				CommonUtils.checkDoc(doc_1,Theatres_BowTieCinemas_AggData.class);

				Elements parent = doc_1.select("section#content");

				for (Element p : parent) {

					Elements address_l = p.getElementsByTag("span");
					EntityTheatre t_e = null;

					for (Element getaddress : address_l) {

						Elements s = getaddress.select("span[itemprop=address]");
						t_e = new EntityTheatre();

						for (Element a : s) {
							if (a.text() != null) {

								t_e.setAddress(a.text());
								System.out.println(a.text());

							}
						}

						Elements s_1 = getaddress.select("span[itemprop=telephone]");

						for (Element a1 : s_1) {
							if (a1.text() != null) {

								t_e.setPhoneNumber(a1.text());
								System.out.println(a1.text());
							}
						}

						entityCoupler.add(t_e);
					}
				}
			}
		}

		if (entityCoupler.contains(null) || entityCoupler.contains("")) {
			System.out.println("contains null or empty elements ");
		}

		// for(int i=0; i < entityCoupler.size() ; i++)
		// {
		// System.out.println(" ===== > " +
		// entityCoupler.get(i).getTheatreWebsite());
		// System.out.println("----------> " +
		// entityCoupler.get(i).getAddress());
		// System.out.println(" $$$$$$$ " +
		// entityCoupler.get(i).getPhoneNumber());
		// }
	}
}
