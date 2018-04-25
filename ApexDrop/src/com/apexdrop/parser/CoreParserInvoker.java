package com.apexdrop.parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.nodes.Document;

import com.apexdrop.entity.extractor.EmailExtractor;
import com.apexdrop.entity.extractor.NERDemo;
import com.apexdrop.entity.extractor.PhoneNumberExtractor;

/*
 * We need support for infinite loop redirects like blackhawknetwork.com - 301 / 302 errors.
 */

public class CoreParserInvoker {

	static final Queue<String> queueA = new LinkedList<String>();
	static final Queue<String> failedPingTestUrls = new LinkedList<String>();
	static final Set<String> duplicateValidator = new HashSet<String>();
	static final Map<String, String> langResult = new HashMap<String, String>();
	static final TreeSet<String> emailAddress = new TreeSet<String>();
	static final TreeSet<String> phoneNumber = new TreeSet<String>();

	public static void main(String args[]) throws Exception {

		final String[] seeds = {"http://www.varian.com/"};

		for (String root : seeds) {

			System.out.println(extractSimilar(root));

			String matchingDomainnames = extractSimilar(root).equals("nomatch") ? "null" : extractSimilar(root);

			if (matchingDomainnames == null)
				return;

			queueA.add(root);
			LinkExtractor extractor = new LinkExtractor();
			ExtractorCore core = new ExtractorCore();

			Document doc = core.extractDocument(root);
			String title;
			try{
				title = doc.getElementsByTag("title").text();
			}catch(NullPointerException ex){
				System.out.println(ex.getMessage());
				title = root;
			}
			System.out.println(" Title :: " + title);

			BufferedWriter writer = new BufferedWriter(new FileWriter("./temp/output.txt", true));
			writer.write(" ----------------- " + title + " ---------------- ");
			writer.newLine();
			writer.close();

			while (queueA.size() > 0) {

				String head = queueA.poll();

				queueA.stream().forEach((q) -> {
					System.out.println("Elements in the Queue -----------> " + q);
				});

				//if (head.equalsIgnoreCase(root) || head.contains("contact") || head.contains("about")
					//	|| head.contains("location")) {

					try {
						if (!HTTPCore.pingTest(head)) {
							failedPingTestUrls.add(head);
							System.out.println(" Added failed URL's to the queue");
							continue;
						}
					} catch (IOException io) {
						System.out.println(io.getMessage() + " Added failed URL's to the queue");
					}

					Document document = (core.extractDocument(head) != null) ? core.extractDocument(head) : null;

					if (document != null) {
						extractor.extractOutgoingLinks(document, matchingDomainnames, root, duplicateValidator, queueA,
								emailAddress, phoneNumber);

						String body = document.text();
						PhoneNumberExtractor.phoneNumberFinder(body, head, phoneNumber);
						EmailExtractor.EmailFinder(body, head, emailAddress);
					}
				//}
			}

			BufferedWriter writer_end = new BufferedWriter(new FileWriter("./temp/output.txt", true));
			writer_end.write(" --------------------------  END --------------------------- ");
			writer_end.close();

			langResult.keySet().forEach(key -> System.out.println(key + "->" + langResult.get(key)));
			failedPingTestUrls.stream().forEach((o) -> System.out.println(" Failed URLS are :: ---> " + o));
		}
	}

	private static String extractSimilar(String url) {
		return url.startsWith("http://") ? url.split("http://")[1]
				: (url.startsWith("https://") ? url.split("https://")[1] : "nomatch");
	}
}