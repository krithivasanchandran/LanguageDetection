package com.apexdrop.entity.extractor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailExtractor {
	
	//final static String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			
	public static void EmailFinder(String htmlbody, String urlReference, TreeSet<String> emailAddress) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter("./temp/output.txt", true));

		final String RE_MAIL = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";
		Pattern p = Pattern.compile(RE_MAIL);
		Matcher m = p.matcher(htmlbody);

		while (m.find()) {
			
			if(!emailAddress.contains(m.group(1))){
				
				emailAddress.add(m.group(1));
				System.out.println(" Email Address :::  " + m.group(1));
				System.out.println(" Website URL :::  " + urlReference);
				writer.write(" Email Address =  " + m.group(1));
				writer.newLine();
				writer.write("URL :: " + urlReference);
				writer.newLine();
			}
		}
		writer.close();
	}

}
