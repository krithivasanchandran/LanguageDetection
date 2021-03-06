package com.locdata.scraper.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/*
 * Logic for King - Scraper
 */

public final class ScraperLogic {
	
	private ScraperLogic() {};
	
	public static Queue<String> queue = new LinkedList<String>();
	public static Queue<String> _200OkQueue = new LinkedList<String>();
 	
	public static class Scraper{
		
		public static Document fetchHtmlContents(String url){

			try {
				if(url == null || url.isEmpty() || url.equals("null")) return null; 
				
				Document document = Jsoup.connect(url)
						.userAgent(
								"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36")
						.timeout(30000)
						 .maxBodySize(0)
						.followRedirects(true)
						 .ignoreContentType(true)
						.get();
				
				
				synchronized(document){
					
					Thread.sleep((long)(Math.random() * 1000));
				}
				
					_200OkQueue.add(url);
				
				return document;
				
			} catch(SocketTimeoutException timeout){
				
	            System.out.println(timeout.getMessage()  + "On Error " + ScraperLogic.class.getSimpleName() + " ::: IOException on URL fetch" + new Date().toString());
	            queue.add(url);
	            System.out.println(" Added url to the queue ");

			}catch(HttpStatusException httpEror){
				
	            System.out.println(httpEror.getMessage() + " <:::> " + ScraperLogic.class.getSimpleName() + " ::: response is not OK and HTTP response errors are not ignored" + new Date().toString());
	            queue.add(url);
	            System.out.println(" Added url to the queue ");

			}catch(MalformedURLException malformedexception) {
				
				System.out.println(malformedexception.getMessage() + "On Error " + ScraperLogic.class.getSimpleName() + " ::: request URL is not a HTTP or HTTPS URL");
				queue.add(url);
	            System.out.println(" Added url to the queue ");

			} catch (IOException | InterruptedException e) {
				
	            System.out.println(e.getMessage() + "On Error " + ScraperLogic.class.getSimpleName() + " ::: IOException on URL fetch" + new Date().toString());
	            queue.add(url);
	            System.out.println(" Added url to the queue ");

			}finally{
				
				/*
				 * Url's in queue Checks
				 */
				queue.forEach((_notOk) -> System.out.println(" ::: <><> Url's in the queue with exeptions  <><><> :::" + _notOk));
				_200OkQueue.forEach((_success) -> System.out.println(" ::: Successfully Completed Url's" + _success));
				
			}
			return null;
		}
	}
}