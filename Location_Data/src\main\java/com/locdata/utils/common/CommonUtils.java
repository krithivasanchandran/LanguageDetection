package com.locdata.utils.common;

import org.jsoup.nodes.Document;

public class CommonUtils {
	
	public static void checkDoc(Document document, Class parentClass)
	{
		if(document == null){
			System.out.println(" The link is broken ");
		    System.out.println("Document !!! urls is null ::: Program Exited " + parentClass.getSimpleName());
		}
	}
	
	public static boolean checkDoc(Document document, Class parentClass, boolean docnull)
	{
		if(document == null){
			System.out.println(" The link is broken ");
		    System.out.println("Document !!! urls is null ::: Program Exited " + parentClass.getSimpleName());
		    docnull = true;
		}
		return docnull;
	}
}