package com.langdetect.parser;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cybozu.labs.langdetect.LangDetectException;
import com.langdetect.core.LanguageCodeDetection;

public class CoreParserInvoker {
	
	static Queue<String> queueA = new LinkedList<String>();
	static Queue<String> failedPingTestUrls = new LinkedList<String>();
	static Set<String> duplicateValidator = new HashSet<String>();
	static Map<String, String> langResult = new HashMap<String, String>();
	
	public static void main(String args[]){
		
		LanguageCodeDetection ld = new LanguageCodeDetection();
		
		try {
	
			final String root = "https://www.pt6a.aero";
			String matchingDomainnames;
			
			System.out.println(extractSimilar(root));
			
			if(extractSimilar(root).equals("nomatch")) return;
			else matchingDomainnames = extractSimilar(root);
			
			String profileDirectory = "/Users/krichandran/Downloads/language-detection-master/profiles";
			ld.init(profileDirectory);

			queueA.add(root);

			while (queueA.size() > 0) {
				
				String head = queueA.poll();
				
				for (String t : queueA) {
					System.out.println("Elements in the Queue -----------> " + t);
				}
				
				if(!HTTPCore.pingTest(head)){
					failedPingTestUrls.add(head);
					continue;
				}
				
				System.out.println(" Currently Invoking this URL =======================> " + head);
				
				Document document = (new ExtractorCore().extractDocument(head) != null) ? new ExtractorCore().extractDocument(head) : null;
				
				if(document != null){
					
					Elements e1 = document.select("a");
					
					for (Element r1 : e1) {
						String g1 = r1.attr("href");
						System.out.println(" Extarcted Links -----> " + g1);
						
						if (!g1.startsWith("./") && !g1.startsWith("#")) {

							if (g1.startsWith("/") || g1.startsWith("?")) {
								g1 = root.concat(g1);
							}

							if ((g1.contains(matchingDomainnames))) {
								if (!duplicates.contains(g1)) {
									System.out.println(" href ------> " + g1);
									duplicates.add(g1);
									if(!g1.endsWith(".JPG") && !g1.startsWith("mailto") && !g1.endsWith(".jpg") && !g1.endsWith(".pdf")){
										queueA.add(g1);
									}
								}
							}
						}
					}
					
				}else{
					System.out.println(" Document returned null !!!");
					continue;
				}
				

				
				String body = document.select("body").text();
				
				try {
					System.out.println(ld.detectLangs(body));
					System.out.println(ld.detect(body));
					langResult.put(head, ld.detect(body));
				} catch (com.cybozu.labs.langdetect.LangDetectException ex) {
					System.out.println(" Language error !!! ");
					System.out.println(" Error body is ::: " + body);
				}
			}
			langResult.keySet().forEach(key -> System.out.println(key + "->" + langResult.get(key)));
		} catch (LangDetectException e) {
			System.out.println(e.getMessage());
		}
	
	}
	
	private static String extractSimilar(String url) {
		return url.startsWith("http://") ? url.split("http://")[1] : (url.startsWith("https://") ? url.split("https://")[1] : "nomatch");
	}
}