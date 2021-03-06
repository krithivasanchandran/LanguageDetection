package com.XMLParser.LocData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlParser {
	
	 public static Document parseXml(String urlString)
	            throws FileNotFoundException, ParserConfigurationException,
	            SAXException, IOException, TransformerConfigurationException {
		 
			 URL url = new URL(urlString);
			 URLConnection conn = url.openConnection();
			 
	        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
	                .newInstance();
	        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	        Document doc = docBuilder.parse(conn.getInputStream(),"UTF-8");
	        
	        
	        //String xml = doc.getTextContent();
	        //xml = xml.trim().replaceFirst("^([\\W]+)<","<");
	       
	        return doc;
	    }


}
