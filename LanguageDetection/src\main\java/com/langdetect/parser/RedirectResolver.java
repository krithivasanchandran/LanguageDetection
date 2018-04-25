package com.langdetect.parser;

import java.util.Queue;

public class RedirectResolver {


	/*
	 * Functionality to remove all the redirects from the queue Improve the
	 * traversing of the queue by using hashmap or some other better algorithm
	 */

	private static void redirectsPoper(Queue<String> queueA) {

		System.out.println(" ******************************** REDIRECTS ***********************************");
		int len = queueA.size();

		while (len > 0) {
			String pointer = queueA.peek();
			if (pointer.split("RedirectURL").length > 1) {
				queueA.remove();
			}
			len--;
		}
		System.out.println(" ******************************** REDIRECTS END ***********************************");
	}
	
}
