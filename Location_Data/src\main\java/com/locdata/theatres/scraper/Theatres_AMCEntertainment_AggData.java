package com.locdata.theatres.scraper; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.XMLParser.LocData.XmlParser;
import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.theatres.entity.EntityTheatre;

/*
 * Store Number
   Store Name 
   Address
   Address Line 2
   City
   State
   Zip Code
   Phone Number
   Latitude
   Longitude
   Geo Accuracy
   Country
   Country Code
   County
   Still Pending : Phone number -- To be retreived from google places
  
 */

public class Theatres_AMCEntertainment_AggData {

	static List<String> theatres = new ArrayList<String>();
	static List<EntityTheatre> address = new ArrayList<EntityTheatre>();
	public static Set<GeoCodeEntityCarrierToExcelWriter> amcKeyDataSet = new LinkedHashSet<GeoCodeEntityCarrierToExcelWriter>();

	public static void main(String args[]) throws  JAXBException, IOException, TransformerConfigurationException, ParserConfigurationException, SAXException {

		String parentUrl = "https://www.amctheatres.com/sitemaps/sitemap-theatres.xml";
		org.w3c.dom.Document document = XmlParser.parseXml(parentUrl);
		
		document.getDocumentElement().normalize();
		
		NodeList nodes = document.getElementsByTagName("url");
        
		// iterate the employees
        for (int i = 0; i < nodes.getLength(); i++) {
           Element element = (Element) nodes.item(i);
           
           final GeoCodeEntityCarrierToExcelWriter object = new GeoCodeEntityCarrierToExcelWriter();
           
           NodeList name = element.getElementsByTagName("Attribute");
           for(int k=0;k<name.getLength();k++){
        	   Element line = (Element) name.item(k);
        	   
        	   switch(k){
        	   
        	   case 1:
        		   System.out.println("Theatre name :::: " + line.getTextContent());
        		   object.setStorename(line.getTextContent());
        		   break;
        	   case 2:
        		   System.out.println(" Theatre ID :::: " + line.getTextContent());
        		   object.setStorenumber(Integer.parseInt(line.getTextContent()));
        		   break;
        	   case 3:
        		   System.out.println(" Address Line1 :::: " + line.getTextContent());
        		   object.setAddress(line.getTextContent());
        		   break;
        	   case 4:
        		   System.out.println("city ::::: " + line.getTextContent());
        		   object.setCity(line.getTextContent());
        		   break;
        	   case 5:
        		   System.out.println(" state ::::: " + line.getTextContent());
        		   object.setState(line.getTextContent());
        		   break;
        	   case 6: 
        		   System.out.println(" Postal Code ::: " + line.getTextContent());
        		   object.setZipcode(line.getTextContent());
        		   break;
        	   }
            
           }
           new GeoCodingApi(object.getAddress().concat(object.getCity()).concat(object.getState()).concat(object.getZipcode()),object);
           amcKeyDataSet.add(object);
        }
        SheetLocalWriter.writeXLSXFile("AmcEntertainment.xlsx",amcKeyDataSet);
			
//		Element t = doc.getElementById("tabs-tabs");
//		Elements t_1 = t.select(".tab-content");
//		
//		for (Element r : t_1) {
//
//			Elements re = r.select(".Link.Link--arrow.Link--reversed.Link--arrow--txt--tiny.txt--tiny");
//
//			for (Element k : re) {
//				System.out.println(parentUrl.concat(k.attr("href")));
//				theatres.add(parentUrl.concat(k.attr("href")));
//				System.out.println(k.text());
//				// address.add(new EntityTheatre().setCountyName(k.text()));
//			}
//		}
//
//		ListIterator<String> itr = theatres.listIterator();
//
//		while (itr.hasNext()) {
//
//			Document doc_1 = ScraperLogic.Scraper.fetchHtmlContents(itr.next());
//
//			CommonUtils.checkDoc(doc_1,Theatres_AMCEntertainment_AggData.class);
//
//
//			Elements tr = doc_1.select(".TheatreInfo");
//			System.out.println(tr.size());
//			final GeoCodeEntityCarrierToExcelWriter entity = new GeoCodeEntityCarrierToExcelWriter();
//
//			for (Element e : tr) {
//				
//				String storename = e.select(".Link-text.Headline--h3").text().trim();
//				
//				if(storename != null && !storename.isEmpty()){
//					System.out.println(" -------->>>>>>>>> " + e.select(".Link-text.Headline--h3").text());
//					entity.setStorename(storename);
//				}
//				
//				System.out.println(" ==============> Theatre Website " + e.select("a").attr("href"));
//
//				System.out.println(
//						" ==============> address " + e.select(".TheatreInfo-address.Headline--eyebrow").text());
//				String address =  e.select(".TheatreInfo-address.Headline--eyebrow").text();
//				if(address != null && !address.isEmpty()){
//					entity.setAddress(e.select(".TheatreInfo-address.Headline--eyebrow").text());
//					new GeoCodingApi(entity.getAddress(),entity);
//					amcKeyDataSet.add(entity);
//				}
//				
//						amcKeyDataSet.forEach((action) ->{
//							StringBuilder builder = new StringBuilder();
//							builder.append(action.getAddress() + " --> " + action.getCity() + " --> " + action.getCountry() + " --> " + action.getCountryCode() + " --> " + action.getCounty()
//							+ " --> " + action.getLatitude() + " --> " + action.getLongitude() +" --> " + action.getState() + " --> " + action.getZipcode());
//							System.out.println(" builder ::::  " + builder.toString());
//						});
//						
//				}
//				        	
//			//	address.add(entity);
//		}
//		SheetLocalWriter.writeXLSXFile("AmcEntertainment.xlsx",amcKeyDataSet);
	}
}