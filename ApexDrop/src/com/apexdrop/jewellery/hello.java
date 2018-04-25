package com.apexdrop.jewellery;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class hello {
	
	public static void main(String args[]) throws IOException{
		
		Document reeldoc = Jsoup.connect("https://connect.data.com/company/view/45940")
				.userAgent(
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36")
				.timeout(30000)
				.maxBodySize(0)
				.followRedirects(true)
				.ignoreContentType(true)
				.get();
		
		
			String headquartersAddress = reeldoc.select(".seo-company-data").get(2).text();
			headquartersAddress = headquartersAddress.replace("map", "");
			System.out.println(" hq address  " + headquartersAddress);
		
			String phone = reeldoc.select(".seo-company-data").get(3).text();
			System.out.println(" Phone  " + phone );
		
			String Industries = reeldoc.select(".seo-company-data").get(4).text();
			System.out.println(" Industries  " + Industries );
		
			String employees = reeldoc.select(".seo-company-data").get(5).text();
			System.out.println(" employees  " + employees );
		
			String companyrevenue = reeldoc.select(".seo-company-data").get(6).text();
			System.out.println(" companyrevenue  " + companyrevenue );
	}

}
