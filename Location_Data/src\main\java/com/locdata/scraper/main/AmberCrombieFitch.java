package com.locdata.scraper.main;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.locdata.utils.common.CommonUtils;

public class AmberCrombieFitch {
	
	public static void main(String args[]){
		
		List<String> linkedlist = new LinkedList<>();
		String url = "https://www.abercrombie.com/shop/wd";
		Document doc = ScraperLogic.Scraper.fetchHtmlContents(url);
		CommonUtils.checkDoc(doc,AmberCrombieFitch.class);
		Elements e1 = doc.select("a");
		for(Element r1 : e1){
		String g1 = r1.attr("href");
		if(g1.startsWith("/shop")){
			String f = g1.replaceAll("/shop/wd/", "/");
			linkedlist.add(url.concat(f));
		}
		}
		
		ListIterator<String> itr = linkedlist.listIterator();
		while(itr.hasNext()){
			Document document = ScraperLogic.Scraper.fetchHtmlContents(itr.next());
			Elements e = document.select("a");
			for(Element r : e){
				String g = r.attr("href");
				if(g.startsWith("/shop")){
					String h2 = g.replace("/shop/wd/", "/");
					linkedlist.add(url.concat(h2));
						String text = doc.body().text();
						System.out.println(text);
				}
			}
		}
		}
	}
