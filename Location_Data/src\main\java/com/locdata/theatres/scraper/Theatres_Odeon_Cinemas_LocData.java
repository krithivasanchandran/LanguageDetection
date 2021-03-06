package com.locdata.theatres.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.jsoup.nodes.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


import com.XMLParser.LocData.XmlParser;
import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.scraper.main.ScraperLogic;
import com.locdata.utils.common.CommonUtils;

public class Theatres_Odeon_Cinemas_LocData {
	
	static List<String> theatreUrls = new ArrayList<String>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> odeonCinemasKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();
	
	public static void main(String args[]) throws IOException, InterruptedException, TransformerConfigurationException, ParserConfigurationException, SAXException {
		
		org.w3c.dom.Document doc = XmlParser.parseXml("http://www.odeon.co.uk/sitemap.xml");
		
		doc.getDocumentElement().normalize();
		
		org.w3c.dom.NodeList nodes = doc.getElementsByTagName("url");
        
		// iterate the employees
        for (int i = 0; i < nodes.getLength(); i++) {
        	Element element = (Element) nodes.item(i);	
        
        	    if(element.getElementsByTagName("loc").item(0).getTextContent().contains("cinemas") && 
        	    		!(element.getElementsByTagName("loc").item(0).getTextContent().equalsIgnoreCase("http://www.odeon.co.uk/cinemas/"))){
        	    	theatreUrls.add(element.getElementsByTagName("loc").item(0).getTextContent());
        	    	System.out.println(element.getElementsByTagName("loc").item(0).getTextContent());
           }
         }

		theatreUrls.forEach((action) -> {
			System.out.println(" ===========> " + action);
			boolean checkDoc = false;
			final GeoCodeEntityCarrierToExcelWriter g = new GeoCodeEntityCarrierToExcelWriter();
			
			String raw = action.split("cinemas/")[1];
			String storename = raw.split("/")[0];
			 try {
					String storenumber = raw.split("/")[1];
					g.setStorenumber(Integer.parseInt(storenumber));
		        } catch (IndexOutOfBoundsException e) {
		            System.out.println("Index Out of Bound Exception thrown for URL :: " + action);		        
		        }
			g.setStorename(storename);
			
			System.out.println(" Store name ----- > " + storename );
			
			Document document = ScraperLogic.Scraper.fetchHtmlContents(action);

			
			
			if(!CommonUtils.checkDoc(document, Theatres_Odeon_Cinemas_LocData.class,checkDoc)){
				for(org.jsoup.nodes.Element element : document.getElementsByTag("div")){
					String el = element.attr("id");
					
					if(el.contains("gethere")){
							System.out.println("Address ---> " + element.select(".description").text());
							g.setAddress(element.select(".description").text());
							try {
								new GeoCodingApi(g.getAddress(), g);
							} catch (Exception e1) {
							   System.out.println(e1.getMessage());
							}
					}
				}
				
				document.select("div#corporate-hire").stream().forEach((i) -> {
					i.select(".row").stream().forEach((p) -> {
						p.select(".unstyled.grey-box").forEach((o) -> {
							System.out.println(" Phone number -----------> " + o.getElementsByTag("strong").text());
							g.setPhoneNumber(o.getElementsByTag("strong").text());
						});
					});
				});
			odeonCinemasKeyDataSet.add(g);	
			}
		});
		SheetLocalWriter.writeXLSXFile("OdeonCinemasUKTheatre.xlsx",odeonCinemasKeyDataSet);
}}