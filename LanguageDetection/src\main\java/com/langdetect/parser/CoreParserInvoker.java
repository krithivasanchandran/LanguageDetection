package com.langdetect.parser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;

import com.langdetect.core.LanguageCodeDetection;
import com.langdetect.parser.constants.Language;

/*
 * Language Detection - https://github.com/optimaize/language-detector
 * We need support for infinite loop redirects like blackhawknetwork.com - 301 / 302 errors.
 */

public class CoreParserInvoker {

	static Queue<String> homepagequeue = new LinkedList<String>();
	static Queue<String> queueA = new LinkedList<String>();
	static Queue<String> failedPingTestUrls = new LinkedList<String>();
	static Set<String> duplicateValidator = new HashSet<String>();
	static final Map<String, String> langResult = new ConcurrentHashMap<String, String>();
	static private short crawlDepth = 2;

	public static void main(String args[]) throws SQLException, IOException {

		LanguageCodeDetection ld = new LanguageCodeDetection();
		String[] url = SeedUrl.getSeeds();

		for (int k = 0; k < url.length && url != null; k++) {

			final String root = url[k];
			ExtractorCore core = new ExtractorCore();

			/*************************************************************************
			 * 						Home Page Language Detection					 *
			 *************************************************************************/

			if (CoreParserInvoker.performWebsiteTest(root)) {
				continue;
			}

			String homeLang = ld.langOverride(core.extractDocument(root).text(), root);
			Language languages = Language.valueOf(homeLang);
			langResult.put(root,languages.getFullTextLanguage());
			DataBlobber resultblob = new DataBlobber();
			resultblob.homepageLanguage(languages.getFullTextLanguage(), root);

			/***************************************************************************
			 * 						End of Home Page Language Detection				   *
			 ***************************************************************************/

			String matchingDomainnames = extractSimilar(root).equals("nomatch") ? "null" : extractSimilar(root);

			if (matchingDomainnames == null) {
				System.out.println(
						" Not a valid URL : URL Should be in the format https://example.com or http://example.com");
				return;
			}
			matchingDomainnames = matchingDomainnames.startsWith("www") ? matchingDomainnames.replace("www.", "") : matchingDomainnames.toLowerCase().trim();
			System.out.println(" Matching Domain Extract ====================> " + matchingDomainnames);

			homepagequeue.add(root);
			LinkExtractor extractor = new LinkExtractor();
			boolean flagFirstEntry = true;

			while (homepagequeue.peek() != null) {

				String head = homepagequeue.poll();

				System.out.println("CURRENT URL CRAWLING HEAD !!  -----------> " + head);

				queueA.stream().forEach((q) -> {
					System.out.println("Elements in the Queue -----------> " + q);
				});

				List<String> nestedlinks = new ArrayList();

				if (flagFirstEntry) {

					if (CoreParserInvoker.performWebsiteTest(head) || core.extractDocument(head) == null) {
						return;
					}

					Document document = core.extractDocument(head);
					if (document != null) {

						extractor.extractOutgoingLinks(document, matchingDomainnames, head, duplicateValidator, queueA);

						/*
						 * Home Page Extracting All the Outgoing Links
						 */
						queueA.parallelStream().forEach((w) -> {
							homepagequeue.add(w);
							nestedlinks.add(w);
						});
						queueA.clear();
						flagFirstEntry = false;

					}
				}

				int depth = 0;
				int i = 0;

				/****************************************************************************
				 * Recursive crawl happens until the crawldepth factor is
				 * satisfied * For Loop executes recursively till the end of the
				 * loop and reintializes * Itself to 0 to go for another
				 * Iteration until depth factor is satisifed * *
				 *****************************************************************************/

				for (; i < nestedlinks.size() && nestedlinks != null; i++) {
					System.out.println(" Inside nested Links loop now ");

					if (CoreParserInvoker.performWebsiteTest(nestedlinks.get(i))
							|| core.extractDocument(nestedlinks.get(i)) == null) {
						continue;
					}

					System.out.println(" NOW EXTRACTING DOCUMENT FOR THIS URL  ===============***************************+++++++++> "+ nestedlinks.get(i));
					Document document = core.extractDocument(nestedlinks.get(i));

					if (document != null) {

						extractor.extractOutgoingLinks(document, matchingDomainnames, root, duplicateValidator, queueA);
						String ptext = document.text();
						ld.parserCumDetector(ptext, langResult, nestedlinks.get(i));
				

					if (i == nestedlinks.size() - 1 && depth < crawlDepth) {
						System.out.println(" Reinitialzing the for with the next set of outgoing links ");
						nestedlinks.clear();
						queueA.forEach((m) -> {
							nestedlinks.add(m);
						});
						queueA.clear();
						depth++;
						i = 0;
					}
					if (depth == crawlDepth) {
						System.out.println("Finished !! Crawl depth satisfied");
						break;
					}
					}
				}
				nestedlinks.stream().forEach((n) -> {
					System.out.println(" Nested Links URL --------> " + n);
				});
			}

			System.out.println(" *********** PRINTING FINAL RESULT ***************************");

			
			List<String> aggregator = new ArrayList<>();
			
			langResult.keySet().forEach((key) -> {
				System.out.println(" Vault Key Values are  KEY ---> " + key + " \t Value ------->" + langResult.get(key));
				String completedLanguage = langResult.get(key);
				aggregator.add(completedLanguage);
			});
			Map<String, Long> result = aggregator.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

			resultblob.OtherLanguageSupportedWebsite(result.toString());
			resultblob.print(root);
			langResult.clear();
			System.out.println(" ********************** END OF FINAL RESULT *********************");

			failedPingTestUrls.stream().forEach((o) -> {
				System.out.println(" Failed URLS are :: ---> " + o);
			});
		}
	}

	private static String extractSimilar(String url) {
		return url.startsWith("http://") ? url.split("http://")[1]
				: (url.startsWith("https://") ? url.split("https://")[1] : "nomatch");
	}

	private static boolean performWebsiteTest(String root) {
		try {
			if (!HTTPCore.pingTest(root)) {
				failedPingTestUrls.add(root);
				System.out.println(" Adding failed URL's to the queue");
				return true;
			}
		} catch (IOException io) {
			System.out.println(io.getMessage() + " Adding failed URL's to the queue");
		}
		return false;
	}
}