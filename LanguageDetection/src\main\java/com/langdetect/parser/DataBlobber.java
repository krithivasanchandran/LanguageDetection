package com.langdetect.parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class DataBlobber {

	private String[][] results = new String[20][20];

	public void homepageLanguage(String lang, String url) {
		results[0][0] = "HomePage";

		results[0][1] = lang.concat(" URL hit --->" + url);
		System.out.println(" Language Home Page :: \t" + lang + "\t "+" URL Hit ----> " + url);
	}

	public void OtherLanguageSupportedWebsite(String a) {
		if (a.length() > 0) {
			results[1][0] = "Languages Supported By Child Pages of Website";
		    results[1][1] = a;
		}
	}

	public void print(String rootUrl) throws IOException {
		String fileName = "/Users/krichandran/Desktop/LanguageDetectionOutput.txt";
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName, true));
			writer.write("****************************************************************** \n");
			writer.write("\t Root URL :: " + rootUrl + "\n");

			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					if (results[i][j] != null) {
						switch (results[i][j]) {
						case "HomePage":
							writer.write("\t" + results[i][j] + " : ");
							break;
						case "Languages Supported By Child Pages of Website":
							writer.write("\n\t" + results[i][j] + " : \t");
							break;
						default:
							writer.write(results[i][j] + "\t,");
						}
						System.out.println(" These are the Language Array Results Please make note of it -----> \t "
								+ results[i][j]);
					}
				}
			}
		//	writer.write("\t Count of Pages of Language  == \t" + LanguageCount);
			writer.write("\n");
			writer.write(" ****************************************************************** \n");
		} catch (IOException e) {
			System.out.println(this.getClass().getName() + new Date().getDate()
					+ " Exception Writing to the File LanguageDetectionOutput.txt ");
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}