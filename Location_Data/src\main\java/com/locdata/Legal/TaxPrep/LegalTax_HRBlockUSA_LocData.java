package com.locdata.Legal.TaxPrep;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.realestate.commercialproperty.scraper.RealEst_ApparelGroup_LocData;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class LegalTax_HRBlockUSA_LocData {
	
	private static String root = "https://www.hrblock.com";

	public static void main(String args[]) throws IOException {

		Document doc = ScraperLogic.Scraper.fetchHtmlContents("https://www.hrblock.com/tax-offices/local/");
		CommonUtils.checkDoc(doc, LegalTax_HRBlockUSA_LocData.class);

		// USA Only
		Element t = doc.select(".ol-states.clearfix").first();


			Elements re = t.getElementsByTag("a");

			re.parallelStream().forEach((action) -> {
				System.out.println("Address --->  " + root.concat(action.attr("href")));
			});
		}
}
