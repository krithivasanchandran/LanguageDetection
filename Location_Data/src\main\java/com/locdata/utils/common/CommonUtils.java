package com.locdata.utils.common;

import org.jsoup.nodes.Document;

public class CommonUtils {
	
	public static void checkDoc(Document document, Class parentClass)
	{
		if(document == null){
			System.out.println(" The link is broken ");
		    throw new IllegalArgumentException("Document !!! urls is null ::: Program Exited " + parentClass.getSimpleName());
		}
	}
}