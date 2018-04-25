package com.apexdrop.parser;

import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;

import com.apexdrop.entity.extractor.EmailExtractor;
import com.apexdrop.entity.extractor.PhoneNumberExtractor;

public class LinkExtractor {

	public void extractOutgoingLinks(Document document, String matchingDomainnames, String root,
			Set<String> duplicateValidator, Queue<String> queueA,TreeSet<String> emailAddress,TreeSet<String> phoneNumber) {

		document.select("a").stream().forEach((r1) -> {

			String g1 = r1.attr("href");
			System.out.println(" Extarcted Links -----> " + g1);
			
			/*
			 * Extracting Phone number from href::Links
			 */
			
			try {
				PhoneNumberExtractor.phoneNumberFinder(g1, g1,phoneNumber);
				EmailExtractor.EmailFinder(g1,g1,emailAddress);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			if (!g1.startsWith("./") && !g1.startsWith("#")) {

				if (g1.startsWith("/") || g1.startsWith("?") || g1.startsWith(".*[a-z]*.")) {
					g1 = root.concat(g1);
				}

				if ((g1.contains(matchingDomainnames)) && !duplicateValidator.contains(g1)) {

					if (!shouldVisit(g1)) {

						duplicateValidator.add(g1);
						queueA.add(g1);
					}
				}
			}
		});
	}

	private boolean shouldVisit(String url) {
		final Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpg" + "JPG|png|tiff?|mid|mp2|mp3|mp4"
				+ "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
		String href = url.toLowerCase();
		return FILTERS.matcher(href).matches();
	}

}
