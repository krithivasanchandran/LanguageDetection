package com.dataruvi.nlp;

import java.util.List;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;

public class NERDemo {


	  public static void entityR(String[] example) throws Exception {

	    String serializedClassifier = "/Users/krichandran/Desktop/stanford-ner-2017-06-09/classifiers/english.all.3class.distsim.crf.ser.gz";

	    AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
	    
	    /* For either a file to annotate or for the hardcoded text example, this
	       demo file shows several ways to process the input, for teaching purposes.
	    */

	    

	      /* For the hard-coded String, it shows how to run it on a single
	         sentence, and how to do this and produce several formats, including
	         slash tags and an inline XML output format. It also shows the full
	         contents of the {@code CoreLabel}s that are constructed by the
	         classifier. And it shows getting out the probabilities of different
	         assignments and an n-best list of classifications with probabilities.
	      */
	    
	    

	    //  String[] example = {"Good afternoon Rajat Raina, how are you today?",
	           //               "I go to school at Stanford University, which is located in California." };
	      for (String str : example) {
	        System.out.println(classifier.classifyToString(str));
	      }
	      System.out.println("---");

	      for (String str : example) {
	        // This one puts in spaces and newlines between tokens, so just print not println.
	        System.out.print(classifier.classifyToString(str, "slashTags", false));
	      }
	      System.out.println("---");

	      for (String str : example) {
	        // This one is best for dealing with the output as a TSV (tab-separated column) file.
	        // The first column gives entities, the second their classes, and the third the remaining text in a document
	        System.out.print(classifier.classifyToString(str, "tabbedEntities", false));
	      }
	      System.out.println("---");

	      for (String str : example) {
	        System.out.println(classifier.classifyWithInlineXML(str));
	      }
	      System.out.println("---");

	      for (String str : example) {
	        System.out.println(classifier.classifyToString(str, "xml", true));
	      }
	      System.out.println("---");

	      for (String str : example) {
	        System.out.print(classifier.classifyToString(str, "tsv", false));
	      }
	      System.out.println("---");

	      // This gets out entities with character offsets
	      int j = 0;
	      for (String str : example) {
	        j++;
	        List<Triple<String,Integer,Integer>> triples = classifier.classifyToCharacterOffsets(str);
	        for (Triple<String,Integer,Integer> trip : triples) {
	          System.out.printf("%s over character offsets [%d, %d) in sentence %d.%n",
	                  trip.first(), trip.second(), trip.third, j);
	        }
	      }
	      System.out.println("---");

	      // This prints out all the details of what is stored for each token
	      int i=0;
	      for (String str : example) {
	        for (List<CoreLabel> lcl : classifier.classify(str)) {
	          for (CoreLabel cl : lcl) {
	            System.out.print(i++ + ": ");
	            System.out.println(cl.toShorterString());
	          }
	        }
	      }

	      System.out.println("---");

	    }
	
}
