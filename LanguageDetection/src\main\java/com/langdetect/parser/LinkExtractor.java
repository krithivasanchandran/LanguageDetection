package com.langdetect.parser;

import java.util.Queue;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.util.StringUtil;
import org.jsoup.nodes.Document;

public class LinkExtractor {

	public void extractOutgoingLinks(Document document, String matchingDomainnames, String root,

			Set<String> duplicateValidator, Queue<String> queueA) {

		document.select("a").stream().forEach((r1) -> {

				String g1 = r1.attr("href").toLowerCase();
				
				 /* ************************
				 * Filtering Social Links *
				****************************/
				boolean nomatchesCondition = !g1.startsWith("./")
						                     && !g1.startsWith("//")
						                     && !g1.startsWith("mailto")
						                     && !g1.contains("facebook.com")
						                     && !g1.contains("google.com")
						                     && !g1.contains("twitter.com")                
						                     && !g1.contains("linkedin.com")
						                     && !g1.contains("reddit.com")
						                     && !g1.contains("digg.com")
						                     && !g1.contains("yahoo.com")
						                     && !g1.startsWith("javascript");
				
				/*
				 * Validate if href contains http or https 
				 */
				if((g1.startsWith("http://") || g1.startsWith("https://")) && nomatchesCondition){
					
					if(!duplicateValidator.contains(g1) && g1.contains(matchingDomainnames)){
						
							System.out.println(" Filtered & Adding Extarcted Links -----===================> " + g1);
							duplicateValidator.add(g1);
							queueA.add(g1);
					}
				}else if (nomatchesCondition) {
					
					boolean misnomer = g1.startsWith("/")
									  || g1.startsWith("?")
									  || g1.matches("[a-zA-Z0-9](.*)")
									  || g1.endsWith(".html")
									  || g1.startsWith("#");
									  
					if (misnomer) {
						
							if(root.endsWith("/") && g1.matches("[a-zA-Z0-9](.*)")){
								g1 = root.concat(g1);
							}else if(root.matches("(.*)[a-zA-Z0-9]") && g1.startsWith("/")){
								g1 = root.concat(g1);
							}else if(root.endsWith("/") && g1.startsWith("/")){
								String normalizedString = root.substring(0, root.length()-1);
								g1 = normalizedString.concat(g1);
							}else if(root.matches("(.*)[a-zA-Z0-9]") && g1.matches("[a-zA-Z0-9](.*)")){
								g1 = root.concat("/"+g1);
							}else{
								if(g1.startsWith("#")){
									g1 = g1.substring(1,g1.length());
									g1=root.concat(g1);
								}
							}
					}
					
					if ((g1.startsWith(root)) && !duplicateValidator.contains(g1)) {
						
						System.out.println(" Filtered Extarcted Links -----> " + g1);
							duplicateValidator.add(g1);
							queueA.add(g1);
					}
				}else{
					System.out.println(" WARNING !!! None of the URL Extraction Worked Out !!! ");
					System.out.println(" ********* URL NONMATCHED ONES ********\t" + g1);
				}
		});
	}

	private boolean shouldVisit(String url) {
		String href = url.toLowerCase();
		boolean FILTERS = href.matches("(.*)(css|js|bmp|gif|jpe?g"
											+ "JPG|png|tiff?|mid|mp2|mp3|mp4"+
											"|wav|avi|mov|mpeg|ram|m4v|pdf" + 
											"|rm|smil|wmv|swf|wma|zip|rar|gz|mailto)(.*)$");
		
		return FILTERS;
	}
}