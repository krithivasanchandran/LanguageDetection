package com.locdata.scraper.main;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.locdata.utils.common.CommonUtils;

public class AmberCrombieFitch {
	
	public static void main(String args[]){
		
		Set<String> linkedlist = new CopyOnWriteArraySet<String>();
		String url = "http://explore.calvinklein.com/";
		Document doc = ScraperLogic.Scraper.fetchHtmlContents(url);
		CommonUtils.checkDoc(doc,AmberCrombieFitch.class);
		 System.out.println(doc.text());
		Elements e1 = doc.select("a");
		
		for(Element r1 : e1){
		String g1 = r1.attr("href");

		//System.out.println("url  ----> " + f);
			if(g1.contains("about") || g1.contains("contact") || g1.contains("help")){
				//System.out.println(" Inside the about page =================> " +  f);
				linkedlist.add(g1);
			}
		}
		
		while(linkedlist.size() > 0){
			
			for(String sr : linkedlist){
				
				Document document = ScraperLogic.Scraper.fetchHtmlContents(sr);
				if(document == null) continue;
				Elements e2 = document.select("a");
				for(Element r1 : e2){
					String g1 = r1.attr("href");
					if(g1.startsWith("/") && g1.contains("help")){
						String f = g1;
					//	System.out.println("Added URL --=======================>>>>>>>  ----> "+ url.concat(f).toString());
							linkedlist.add(url.concat(f));
					}
				}
				
				String tr = document.text();
			
			//	System.out.println(" body ------> " + tr);
				
				 PhoneNumberUtil util = PhoneNumberUtil.getInstance();
				 
				 Iterator<PhoneNumberMatch> iterator = util.findNumbers(tr, null).iterator();
				 
				 while (iterator.hasNext()) {
				        String  s = iterator.next().rawString();
				        System.out.println(" Phone number found here are ------> " + s);
				        
				        System.out.println(" Website URL :::  " + sr);	
				    }
				
			//	 Pattern pattern = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");
						 
						 /*"(\\d{1}\\s\\d{2}\\s\\d{8})"
			                + "|"
			                + "(\\d{2}\\s\\d{2}\\s\\d{8})"
			                + "|"
			                + "(\\(\\d{3}\\)\\s\\d{3}-\\d{4})"
			                + "|"
			                + "(\\d{1}\\s\\(\\d{3}\\)\\s\\d{3}-\\d{4})"
			                + "|"
			                + "(\\d{1}\\s\\(\\d{3}\\)\\s\\d{3}\\s\\d{4})"
			                + "|"
			                + "(\\d{2}\\s\\(\\d{3}\\)\\s\\d{3}\\s\\d{4})"
			                + "|"
			                + "(\\d{2}\\s\\(\\d{3}\\)\\s\\d{3}-\\d{4})"
			                + "|"
			                + "(\\(\\d{2}\\)\\-\\d{10})"
			                + "|"
			                + "(\\(\\d{2}\\)\\s\\d{10})"
			                + "|"
			                + "(\\(\\d{4}\\)\\s\\d{6})"
			                + "|"
			                + "(\\(\\+(\\d{2}\\s\\d{4}\\))\\s\\d{6})"//(+91 2632) 233261  
			                + "|"
			                + "(\\(\\+(\\d{3}\\))\\s\\d{3}\\s\\d{3}\\s\\d{3})"//(+351) 252 300 300
			                + "|"
			                + "(\\(\\+(\\d{2}\\s\\-\\d{2}\\))\\s\\d{8})"//(+91 -22) 26559178 
			                + "|"
			                + "(\\(\\d{2}\\)\\s\\-\\s\\(\\d{2}\\)\\s\\-\\s\\d{8})"//+(91) - (79) - 26447736 
			                + "|"
			                + "(\\(\\+(\\d{2}\\))\\-\\d{2}\\s\\d{8})"//(+91)-80 22932900
			                + "|"
			                + "(\\(\\d{3}\\s\\d{3}\\)\\s\\d{8})"
			                + "|"
			                + "(\\(\\d{2}\\)\\-\\(\\d{2}\\)\\-\\s\\d{8})"//+(91)-(22)- 42957551
			                + "|"
			                + "(\\d{2}\\s\\(\\d{2}\\)\\s\\d{8})"
			                + "|"
			                + "(\\d{3}-\\d{3}-\\d{4})"
			                + "|"
			                + "(\\[\\+\\d{1}]\\s\\d{3}-\\d{3}-\\d{4})" // [+1] 858-952-7570
			                + "|"
			                + "(\\(\\d{3}\\)\\-\\d{3}-\\d{4})"
			                + "|"
			                + "(\\d{2}-\\d{2}-\\d{8})"
			                + "|"
			                + "(\\d{2}-\\d{5}\\s\\d{5})"
			                + "|"
			                + "(\\d{2}\\s\\d{5}\\s\\d{5})"
			                + "|"
			                + "(\\d{2}\\s\\-\\s\\d{2}\\s\\-\\s\\d{8})"
			                + "|"
			                + "(\\d{2}-\\d{4}-\\d{6})"
			                + "|"
			                + "(\\d{2}\\s\\-\\s\\d{4}\\s\\-\\s\\d{6})"
			                + "|"
			                + "(\\d{1}-\\d{3}-\\d{3}-\\d{4})"
			                + "|"
			                + "(\\d{2}-\\d{3}-\\d{3}-\\d{4})"
			                + "|"
			                + "(\\d{1}\\s\\d{3}-\\d{3}-\\d{4})"
			                + "|"
			                + "(\\d{2}\\s\\d{3}-\\d{3}-\\d{4})"
			                + "|"
			                + "(\\d{3}-\\d{7})"
			                + "|"
			                + "(\\d{2}-\\d{10})"
			                + "|"
			                + "(\\d{1}\\-\\d{3}-\\d{7})"
			                + "|"
			                + "(\\d{2}\\-\\d{3}-\\d{7})"
			                + "|"
			                + "(\\d{1}\\s\\d{3}-\\d{7})"
			                + "|"
			                + "(\\d{2}\\s\\d{3}-\\d{7})"
			                + "|"
			                + "(^(\\+\\d{2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$)"
			                + "|"
			                + "(\\d{1}\\s\\d{3}\\s\\d{3}\\s\\d{4})"
			                + "|"
			                + "(\\d{2}\\s\\d{3}\\s\\d{2}\\s\\d{2}\\s\\d{3})"
			                + "|"
			                + "(\\d{3}\\s\\d{2}\\s\\d{3}\\s\\d{3})"
			                + "|"
			                + "(\\d{3}\\s\\d{2}\\s\\d{3}\\s\\d{4})"
			                + "|"
			                + "(\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{4})"
			                + "|"
			                + "(\\d{3}\\s\\d{3}\\s\\d{4})"
			                + "|"
			                + "(\\(\\d{3}\\)\\s\\d{3}\\s\\d{4})"
			                + "|"
			                + "(\\d{1}\\s\\d{5}\\s\\d{5})"
			                + "|"
			                + "(\\d{2}\\s\\d{1}\\s\\d{4}\\s\\d{4})"
			                + "|"
			                + "(\\d{2}\\s\\d{2}\\s\\d{3}\\s\\d{4})"
			                + "|"
			                + "(\\d{2}\\s\\d{2}\\s\\d{3}\\s\\d{3})"
			                + "|"
			                + "(\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3})"
			                + "|"
			                + "(\\d{2}\\s\\d{3}\\s\\d{7})"
			                + "|"
			                + "(\\d{1}\\s\\d{3}\\s\\d{7})"
			                + "|"
			                + "(\\d{3}\\s\\d{3}\\s\\d{2}\\s\\d{2})"
			                + "|"
			                + "(\\d{2}\\s\\d{2}\\s\\d{3}\\s\\d{2}\\s\\d{2})"
			                + "|"
			                + "(\\d{2}\\s\\d{2}\\s\\d{4}\\s\\d{4})"
			                + "|"
			                + "(\\d{2}\\s\\(\\d{2}\\)\\s\\d{4}\\s\\d{4})"
			                + "|"
			                + "(\\d{2}\\-\\d{2}\\-\\d{4}\\s\\d{4})"
			                + "|"
			                + "(\\d{3}\\.\\d{3}\\.\\d{4})"
			                + "|"
			                + "(\\d{2}\\s\\d{3}\\.\\d{3}\\.\\d{4})"
			                + "|"
			                + "(\\d{1}\\.\\d{3}\\s\\d{3}\\s\\d{4})"
			                + "|"
			                + "(\\d{5}\\s\\d{9})"
			                + "|"
			                + "(\\d{4}\\(\\d{1}\\)\\d{4}\\s\\d{6})" // 0044(0)1283 225111 
			                + "|"
			                + "(\\d{2}\\s\\(\\d{1}\\)\\s\\d{3}\\s\\d{3}\\s\\d{4})"//+44 (0) 161 306 4114
			                + "|"
			                + "(\\d{3}\\s\\(\\d{1}\\)\\s\\d{7})"
			                + "|"
			                + "(\\d{2}\\s\\(\\d{1}\\)\\s\\d{4}\\s\\d{3}\\s\\d{3})" 
			                + "|"
			                + "(\\d{2}\\s\\(\\d{1}\\)\\s\\d{2}\\.\\d{2}\\.\\d{2}\\.\\d{2})"//+32 (0) 11.26.24.20 
			                + "|"
			                + "(\\d{2}\\s\\(\\d{1}\\)\\s\\d{3}\\-\\s\\d{3}\\s\\d{2}\\s\\d{2})"//+49 (0) 241- 413 42 73
			                + "|"
			                + "(\\d{2}\\s\\(\\d{1}\\)\\s\\d{3}\\-\\d{3}\\s\\d{2}\\s\\d{2})"
			                + "|"
			                + "(\\d{2}\\s\\(\\d{1}\\)\\d{3}\\s\\d{2}\\s\\d{5})"//+44 (0)113 34 33705 
			                + "|"
			                + "(\\d{2}\\s\\(\\d{1}\\)\\d{1}\\s\\d{2}\\s\\d{2}\\s\\d{2}\\s\\d{2})"//+33 (0)1 44 08 19 00 
			                + "|"
			                + "(\\d{2}\\s\\(\\d{1}\\)\\s\\d{4}\\s\\d{6})"    //+44 (0) 1204 669033  
			                + "|"
			                + "(\\d{2}\\s\\(\\d{1}\\)\\d{4}\\s\\d{6})"
			                + "|"
			                + "(\\d{4}\\s\\d{3}\\s\\d{4})"
			                + "|"
			                + "(\\d{4}-\\d{3}-\\d{4})"
			                + "|"
			                + "(\\d{3}-\\d{8})"
			                + "|"
			                + "(\\d{3}-\\d{2}-\\d{5})"
			                + "|"
			                + "(\\d{2}\\s\\d{2}\\s\\d{4}-\\d{4})"
			                + "|"
			                + "(\\d{2}\\-\\d{2}\\. ISSN \\d{4}-\\d{4})"
			                + "|"
			                + "(\\d{4}\\-\\d{4}\\-\\d{1}-\\d{3})"
			                + "|"
			                + "(\\d{2}\\s\\d{4}\\s\\d{4})"
			                + "|"
			                + "(\\d{3}\\s\\d{1}\\s\\d{7})"
			                + "|"
			                + "(\\d{13})"
			                + "|"
			                + "(\\d{12})"
			                + "|"
			                + "(\\d{11})"
			                + "|"
			                + "(\\d{10})"
			                + "|"
			                + "(\\d{9})"
			                + "|"
			                + "(\\d{2}\\s\\d{10})"
			                + "|"
			                + "(\\d{1}\\s\\d{10})"
			                + "|"
			                + "(^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$)" */ 
			             

//				Matcher m = pattern.matcher(body);
//				if(m.matches()){
//					System.out.println(" Phone number match found  :::::::::: " + m.group());
//				}
				
				linkedlist.remove(sr);
			}
		}
		System.out.println(" linkedlist final size  " + linkedlist.size());
		}
	}
