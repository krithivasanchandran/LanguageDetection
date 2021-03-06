package com.locdata.Legal.TaxPrep;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.google.common.base.Splitter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

/*
 * s[0] ---->mallNm
s[1] ---->CEDAR HILLS SHOPPING CENTER
s[2] ---->addr1
s[3] ---->5975-9 WILSON BLVD
s[4] ---->addr1ForUrl
s[5] ---->5975-9-wilson-blvd
s[6] ---->addr2
s[7] ---->addr3
s[8] ---->Corner of Wilson Boulevard and Blanding Boulevard Next to Little Caesars Pizza and Gladwells Florist in the Winn Dixie Shopping Center
s[9] ---->city
s[10] ---->JACKSONVILLE
s[11] ---->state
s[12] ---->FL
s[13] ---->zip
s[14] ---->32210
s[15] ---->countryRegion	USA
s[16] ---->phone
s[17] ---->904-777-2686
s[18] ---->latitude
s[19] ---->30.270780
s[20] ---->longitude
s[21] ---->-81.737895
s[22] ---->stateName
s[23] ---->Florida
s[24] ---->taxId
s[25] ---->8702
s[26] ---->seasInd
s[27] ---->0
s[28] ---->seasOpenDt
s[29] ---->Jan 02, 2018
s[30] ---->fatpInd
s[31] ---->1
s[32] ---->spare7_ind
s[33] ---->2
s[34] ---->aff_nm
s[35] ---->NA

<!--
			mallNm			MAIN STREET STATION SHOP CTR
			addr1			5751 N MAIN ST STE 105
			addr1ForUrl		5751-n-main-st-ste-105
			addr2			
			addr3			Corner of 48th and Main in the Sav a Lot center.
			city			JACKSONVILLE
			state			FL
			zip				32208
			countryRegion	USA
			phone			904-791-9240
			latitude		30.375256
			longitude		-81.649892
			stateName		Florida
			taxId			8699
			seasInd			0
			seasOpenDt		
			fatpInd			1
			spare7_ind		2
			aff_nm			NA
		-->
		
		 taxData.put(s[2], s[3]);
					 taxData.put(s[4], s[5]);
					 taxData.put(s[7], s[8]);
					 taxData.put(s[9], s[10]);
					 taxData.put(s[11], s[12]);
					 taxData.put(s[13], s[14]);
					 taxData.put(s[16], s[17]);
					 taxData.put(s[18], s[19]);
					 taxData.put(s[20], s[21]);
					 taxData.put(s[22], s[23]);
					 taxData.put(s[24], s[25]);
					 taxData.put(s[26], s[27]);
					 taxData.put(s[28], s[29]);
					 taxData.put(s[30], s[31]);
					 taxData.put(s[32], s[33]);
 */

public class TaxReform {

	static Map<String, String> taxData = new LinkedHashMap<String, String>();
	private static Set<String> services = new LinkedHashSet<String>();

	public static void main(String args[]) throws IOException, EncryptedDocumentException, InvalidFormatException {

		Document doc = ScraperLogic.Scraper.fetchHtmlContents(
				"https://www.hrblock.com/tax-offices/local/florida-tax-preparation/jacksonville-tax-professionals");
		CommonUtils.checkDoc(doc, TaxReform.class);

		Elements t = doc.select(".office-group");
		for (Element r : t) {
			List<Node> child = r.childNodes();
			int rowCount = 0;
			for (Node n : child) {
				if (n.nodeName().equals("#comment")) {
					Comment comment = (Comment) n;
					// System.out.println(" Data =========> " +
					// comment.getData());
					// String[] s = comment.getData().trim().split("\\s{2,}");

					

					
					Iterable<String> split= Splitter.onPattern("\r?\n").trimResults().omitEmptyStrings().split(comment.getData());

					Iterator<String> itr = split.iterator();
					
					while(itr.hasNext()){
					
						String[] tokens = itr.next().trim().split("\\s{2,}");
						for(int i=0;i<tokens.length;i+=2){
							String s;
							try{
								s = tokens[i+1];
							}catch(IndexOutOfBoundsException e ){
								s = "NA";	
							}
							System.out.println("  ------->  "  + tokens[i] + " ------> "  + s);
						}
					}
				}
				
				
			}
		}
		
		for(Element tp : doc.select(".tpf-office-info-non-mobile")){
			System.out.println(" Office URL  __+_+_________++++++-=============================> "+ tp.getElementsByTag("a").attr("href"));
			
			Document document$ = ScraperLogic.Scraper.fetchHtmlContents("https://www.hrblock.com"+ tp.getElementsByTag("a").attr("href"));
			CommonUtils.checkDoc(document$, LegalTax_HRBlockUSA_LocData.class);
			
			// Scrape for contents that office will has services provided for per office basis
			Elements reel = document$.select(".services.visible-desktop");
			for(Element g : reel){
				Elements r1 = g.select(".accordion-wrap");
				
				for( Element cd : r1){
					Elements fr = cd.select(".accordion-content");
					for(Element as : fr){
						Elements r2 = as.getElementsByTag("span");
						for(Element r3 :r2){
							System.out.println(" ---> " + r3.text());
							services.add(r3.text());
						}
					}
				}
			}
		}
		services.removeIf(filter -> filter == null || "".equals(filter));
		// Get Office Info 
		services.forEach((action$) -> {
			System.out.println(" Services offered { { " + action$);
		});
	}
}