package com.locdata.realestate.commercialproperty.scraper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.XMLParser.LocData.XmlParser;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

// A total different requires to be take a copy of the html to the local
// Stores Information --> https://api.simon.com/v1.2/tenant?mallId=207&key=40A6F8C3-3678-410D-86A5-BAEE2804C8F2&lw=true <-- return type xml 

public class helloworld {

static Set<String> esturl = new LinkedHashSet<String>();
static String storeApi= "https://api.simon.com/v1.2/tenant?mallId=207&key=40A6F8C3-3678-410D-86A5-BAEE2804C8F2&lw=true";
	
	public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException, TransformerConfigurationException {
		
		File input = new File("./temp/simon.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://www.simon.com/mall");

		Elements re = doc.select("main#simon");
		
		for(Element d : re){
			Elements web = d.select(".col-xl-4.col-md-6");
			for(Element n : web){
				//System.out.println("Website ----------> " + n.getElementsByTag("a").attr("href").concat("/about"));
				esturl.add(n.getElementsByTag("a").attr("href").concat("/about"));
				esturl.add(n.getElementsByTag("a").attr("href").concat("/hours"));
			}
		}
			
//		if(esturl.size() > 0){
//			esturl.forEach((action) -> {
//				System.out.println(" --------------> " + action);
//				if(action.contains("/about")){
//					
//					Document document = ScraperLogic.Scraper.fetchHtmlContents(action);
//					CommonUtils.checkDoc(document,RealEst_PremiumOutlet_LocData.class);
//
//					for(Element ti : document.select("main#simon")){
//						System.out.println("Address --------> " + ti.getElementsByTag("h5").text());
//					}
//					String contact = document.getElementsByTag("aside").select(".aside-group").first().getElementsByTag("p").text();
//					System.out.println( "Phone --> " + contact);
//				}
//				
//				if(action.contains("/hours")){
//					
//					Document document = ScraperLogic.Scraper.fetchHtmlContents(action);
//					CommonUtils.checkDoc(document,RealEst_PremiumOutlet_LocData.class);
//					
//
//					for(Element ti : document.select("main#simon")){	
//						
//						Elements a1 = ti.select(".col-md-12.no-gutter-mobile");
//						
//						for(Element d: a1){
//							System.out.println("Week --------> " + d.select(".hour-day.bold.pull-sm-left").text());
//							System.out.println("Hours Open =========>" + d.select(".hour-time.pull-sm-right").text());
//						}
//					}
//				 }
//			});
//		}
		
		org.w3c.dom.Document docs = XmlParser.parseXml(storeApi);
//		 NodeList nodeList = docs.getElementsByTagName("Tenant");
//         System.out.println("Information for each employee");
//
//	        for (int i = 0; i < nodeList.getLength(); i++) {
//	        	org.w3c.dom.Node node = nodeList.item(i);
//	        	 if (node.getNodeType() == Node.ELEMENT_NODE) {
//	        		 org.w3c.dom.Element ele = ( org.w3c.dom.Element ) node;
//	                 System.out.println(ele.getTextContent());
//	             }
//	        }

}
}