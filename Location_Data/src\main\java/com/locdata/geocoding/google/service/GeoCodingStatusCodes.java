package com.locdata.geocoding.google.service;

import java.util.LinkedHashSet;
import java.util.Set;

public enum GeoCodingStatusCodes {
	
	OK("SUCCESS"),
	ZERO_RESULTS("Indicates that the geocode was successful but returned no results. This may occur if the geocoder was passed a non-existent address."),
	OVER_QUERY_LIMIT("Critical !!!  indicates that you are over your quota."),
	REQUEST_DENIED("indicates that your request was denied"),
	INVALID_REQUEST("Generally indicates that the query (address, components or latlng) is missing"),
	UNKNOWN_ERROR("indicates that the request could not be processed due to a server error. The request may succeed if you try again");

    private final String key;
    private static final Set<String> errorCodes = new LinkedHashSet(){{
    	add("OK");
    	add("ZERO_RESULTS");
    	add("OVER_QUERY_LIMIT");
    	add("REQUEST_DENIED");
    	add("INVALID_REQUEST");
    	add("UNKNOWN_ERROR");
    }};


    GeoCodingStatusCodes(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
    
    public static Set<String> getErrorCodes(){
    	return errorCodes;
    }
}
