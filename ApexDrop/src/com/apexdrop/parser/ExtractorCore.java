package com.apexdrop.parser;

import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ExtractorCore {
	
	public Document extractDocument(String head){
		
		Document document = null;
		try{
			document = Jsoup.connect(head)
					.userAgent("Mozilla")
					.timeout(40000)
					.maxBodySize(0)
					.followRedirects(true)
					.ignoreContentType(true)
					.get();
			return document;
		}catch(Exception ex){
			System.out.println(" Http Exception occurred hence stalling the operation ");
		}
		return null;
	}
	

}
