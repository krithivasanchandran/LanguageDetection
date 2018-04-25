package com.langdetect.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;

public class LanguageCodeDetection {

	public LanguageCodeDetection() {

		try {
			String profileDirectory = "/Users/krichandran/Downloads/language-detection-master/profiles";
			init(profileDirectory);

		} catch (LangDetectException ex) {
			System.out.println(" Critical Error Initializing Language Detection Profile");
		}
	}

	public void init(String profileDirectory) throws LangDetectException {
		DetectorFactory.loadProfile(profileDirectory);
	}

	private String detect(String text) throws LangDetectException {
		Detector detector = DetectorFactory.create();
		detector.append(text);
		return detector.detect();
	}

	private ArrayList<Language> detectLangs(String text) throws LangDetectException {
		Detector detector = DetectorFactory.create();
		detector.append(text);
		return detector.getProbabilities();
	}

	/*
	 * LANGUAGE DETECTION FOR CHILD PAGES
	 */

	public void parserCumDetector(String bodyText, Map<String, String> langResult, String head) {

		try {
			ArrayList<Language> probabilities = detectLangs(bodyText);
			ArrayList<Double> findDominantList = new ArrayList<Double>();

			probabilities.stream().forEach((pt) -> {
				findDominantList.add(pt.prob);
			});

			Double dominantprobability = Collections.max(findDominantList);

			Language language_a = probabilities.stream().filter((e) -> Double.compare(e.prob, dominantprobability) == 0)
					.findAny().orElse(null);

			String dominantLanguage = language_a.lang;

			String completeLang = com.langdetect.parser.constants.Language.valueOf(dominantLanguage)
					.getFullTextLanguage();

				System.out.println(" Dominant Language is ::::  " + dominantLanguage);

				langResult.put(head, completeLang);

		} catch (com.cybozu.labs.langdetect.LangDetectException ex) {
			System.out.println(" Language error !!! ");
			System.out.println(" Error body is ::: ");
		}
	}

	/*
	 * LANGUAGE DETECTION --> HOME PAGE
	 */

	public String langOverride(String bodyText, String head) {

		try {
			ArrayList<Language> probabilities = detectLangs(bodyText);
			ArrayList<Double> findDominantList = new ArrayList<Double>();

			probabilities.stream().forEach((pt) -> {
				findDominantList.add(pt.prob);
			});

			Double dominantprobability = Collections.max(findDominantList);

			Language language_a = probabilities.stream().filter((e) -> Double.compare(e.prob, dominantprobability) == 0)
					.findAny().orElse(null);

			String dominantLanguage = language_a.lang;

			System.out.println(" Dominant Language is ::::  " + dominantLanguage);
			return dominantLanguage;

		} catch (com.cybozu.labs.langdetect.LangDetectException ex) {
			System.out.println(" Language error !!! ");
			System.out.println(" Error body is ::: ");
		}
		return null;
	}
}