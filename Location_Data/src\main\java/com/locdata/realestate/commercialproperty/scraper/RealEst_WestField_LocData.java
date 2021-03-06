package com.locdata.realestate.commercialproperty.scraper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class RealEst_WestField_LocData {

	static Set<String> esturl = new HashSet<String>();

	public static void main(String args[]) throws IOException {

		String parentUrl = "https://www.westfield.com";

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.westfield.com/");

		CommonUtils.checkDoc(doc, RealEst_WestField_LocData.class);

		Elements t = doc.select("h2.tile-centre__header.u-font--flama");

		for (Element r : t) {

			System.out.println(" Website ------> " + parentUrl.concat(r.getElementsByTag("a").attr("href")));
			esturl.add(parentUrl.concat(r.getElementsByTag("a").attr("href")));
		}

		if (esturl.size() > 0) {
			esturl.forEach((action) -> {

				Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

				CommonUtils.checkDoc(document, RealEst_GeneralGrowth_LocData.class);

				Elements r = document.select(".footer.centreFooter");

				for (Element ti : r) {

					Elements pana = ti.select(".footer__info.footer__info--centre");
					
					for(Element e : pana){
						
						Elements per =  e.select(".footer__centre.u-text-size-small.u-s-pe-base");
						
						for(Element po : per){
							System.out.println(" Url --------> " + po.getElementsByTag("a").text());
						}
					}
				}
				
				Document doc_1 = ScraperLogic.Scraper.fetchHtmlContents(action.concat("/stores/all-stores"));
				CommonUtils.checkDoc(doc_1, RealEst_GeneralGrowth_LocData.class);

				Elements r_1 = doc_1.select(".allStores");
				
				r_1.parallelStream().forEach((f1) -> {
					
					Elements p1 = f1.select(".store-tile__content");
					
					for(Element g : p1){
						System.out.println(" Store name ------ > " + g.select(".store-title__content__heading").text());
						System.out.println(" Contact Store --------> " + g.select(".u-hide-up-to-small").text());
					}
				});
			});
		}
	}
}