package com.locdata.geocoding.google.service;

import java.util.ArrayList;
/*
 * Json Response Class generated from - http://json2java.azurewebsites.net/
 * 
 *  Sample Response
 * {
   "html_attributions" : [],
   "result" : {
      "address_components" : [
         {
            "long_name" : "5",
            "short_name" : "5",
            "types" : [ "floor" ]
         },
         {
            "long_name" : "48",
            "short_name" : "48",
            "types" : [ "street_number" ]
         },
         {
            "long_name" : "Pirrama Road",
            "short_name" : "Pirrama Rd",
            "types" : [ "route" ]
         },
         {
            "long_name" : "Pyrmont",
            "short_name" : "Pyrmont",
            "types" : [ "locality", "political" ]
         },
         {
            "long_name" : "Council of the City of Sydney",
            "short_name" : "Sydney",
            "types" : [ "administrative_area_level_2", "political" ]
         },
         {
            "long_name" : "New South Wales",
            "short_name" : "NSW",
            "types" : [ "administrative_area_level_1", "political" ]
         },
         {
            "long_name" : "Australia",
            "short_name" : "AU",
            "types" : [ "country", "political" ]
         },
         {
            "long_name" : "2009",
            "short_name" : "2009",
            "types" : [ "postal_code" ]
         }
      ],
      "adr_address" : "5,
      \u003cspan class=\"street-address\"\u003e48 Pirrama Rd\u003c/span\u003e,
      \u003cspan class=\"locality\"\u003ePyrmont\u003c/span\u003e
      \u003cspan class=\"region\"\u003eNSW\u003c/span\u003e
      \u003cspan class=\"postal-code\"\u003e2009\u003c/span\u003e,
      \u003cspan class=\"country-name\"\u003eAustralia\u003c/span\u003e",
      "formatted_address" : "5, 48 Pirrama Rd, Pyrmont NSW 2009, Australia",
      "formatted_phone_number" : "(02) 9374 4000",
      "geometry" : {
         "location" : {
            "lat" : -33.866651,
            "lng" : 151.195827
         },
         "viewport" : {
            "northeast" : {
               "lat" : -33.8653881697085,
               "lng" : 151.1969739802915
            },
            "southwest" : {
               "lat" : -33.86808613029149,
               "lng" : 151.1942760197085
            }
         }
      },
      "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
      "id" : "4f89212bf76dde31f092cfc14d7506555d85b5c7",
      "international_phone_number" : "+61 2 9374 4000",
      "name" : "Google",
      "place_id" : "ChIJN1t_tDeuEmsRUsoyG83frY4",
      "rating" : 4.5,
      "reference" : "CmRSAAAAjiEr2_A4yI-DyqGcfsceTv-IBJXHB5-W3ckmGk9QAYk4USgeV8ihBcGBEK5Z1w4ajRZNVAfSbROiKbbuniq0c9rIq_xqkrf_3HpZzX-pFJuJY3cBtG68LSAHzWXB8UzwEhAx04rgN0_WieYLfVp4K0duGhTU58LFaqwcaex73Kcyy0ghYOQTkg",
      "reviews" : [
         {
            "author_name" : "Robert Ardill",
            "author_url" : "https://www.google.com/maps/contrib/106422854611155436041/reviews",
            "language" : "en",
            "profile_photo_url" : "https://lh3.googleusercontent.com/-T47KxWuAoJU/AAAAAAAAAAI/AAAAAAAAAZo/BDmyI12BZAs/s128-c0x00000000-cc-rp-mo-ba1/photo.jpg",
            "rating" : 5,
            "relative_time_description" : "a month ago",
            "text" : "Awesome offices. Great facilities, location and views. Staff are great hosts",
            "time" : 1491144016
         }
      ],
      "scope" : "GOOGLE",
      "types" : [ "point_of_interest", "establishment" ],
      "url" : "https://maps.google.com/?cid=10281119596374313554",
      "utc_offset" : 600,
      "vicinity" : "5, 48 Pirrama Road, Pyrmont",
      "website" : "https://www.google.com.au/about/careers/locations/sydney/"
   },
   "status" : "OK"
}
      
 */

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true )
public class GeoPlacesApiJsonResponse {

	 @JsonProperty("html_attributions")
	 private ArrayList<Object> html_attributions;

	  public ArrayList<Object> getHtmlAttributions() { return this.html_attributions; }

	  public void setHtmlAttributions(ArrayList<Object> html_attributions) { this.html_attributions = html_attributions; }
	  
      @JsonProperty("result")
	  private Result result;

	  public Result getResult() { return this.result; }

	  public void setResult(Result result) { this.result = result; }
	  
      @JsonProperty("status")
	  private String status;

	  public String getStatus() { return this.status; }

	  public void setStatus(String status) { this.status = status; }
	
    @JsonIgnoreProperties(ignoreUnknown = true )
	public static class AddressComponent
	{
   	 @JsonProperty("long_name")
	  private String long_name;

	  public String getLongName() { return this.long_name; }

	  public void setLongName(String long_name) { this.long_name = long_name; }
	   
	  @JsonProperty("short_name")
	  private String short_name;

	  public String getShortName() { return this.short_name; }

	  public void setShortName(String short_name) { this.short_name = short_name; }
	  
	  @JsonProperty("types")
	  private ArrayList<String> types;

	  public ArrayList<String> getTypes() { return this.types; }

	  public void setTypes(ArrayList<String> types) { this.types = types; }
	}
    
    @JsonIgnoreProperties(ignoreUnknown = true )
	public static class Location
	{
  	  @JsonProperty("lat")
	  private double lat;

	  public double getLat() { return this.lat; }

	  public void setLat(double lat) { this.lat = lat; }
	  
	  @JsonProperty("lng")
	  private double lng;
	  
	  public double getLng() { return this.lng; }

	  public void setLng(double lng) { this.lng = lng; }
	}
    
    @JsonIgnoreProperties(ignoreUnknown = true )
	public static class Northeast
	{ 	 
      @JsonProperty("lat")
	  private double lat;

	  public double getLat() { return this.lat; }

	  public void setLat(double lat) { this.lat = lat; }
	  
	  @JsonProperty("lng")
	  private double lng;

	  public double getLng() { return this.lng; }

	  public void setLng(double lng) { this.lng = lng; }
	}
    
    @JsonIgnoreProperties(ignoreUnknown = true )
	public static class Southwest
	{
  	  @JsonProperty("lat")
	  private double lat;

	  public double getLat() { return this.lat; }

	  public void setLat(double lat) { this.lat = lat; }
	  
	  @JsonProperty("lng")
	  private double lng;

	  public double getLng() { return this.lng; }

	  public void setLng(double lng) { this.lng = lng; }
	}
    
    @JsonIgnoreProperties(ignoreUnknown = true )
	public static class Viewport
	{
  	  @JsonProperty("northeast")
	  private Northeast northeast;

	  public Northeast getNortheast() { return this.northeast; }

	  public void setNortheast(Northeast northeast) { this.northeast = northeast; }
	  
  	  @JsonProperty("southwest")
	  private Southwest southwest;

	  public Southwest getSouthwest() { return this.southwest; }

	  public void setSouthwest(Southwest southwest) { this.southwest = southwest; }
	}

    @JsonIgnoreProperties(ignoreUnknown = true )
	public static class Geometry
	{
    	  @JsonProperty("location")
    	  private Location location;

	  public Location getLocation() { return this.location; }

	  public void setLocation(Location location) { this.location = location; }
	  
	  @JsonProperty("viewport")
	  private Viewport viewport;

	  public Viewport getViewport() { return this.viewport; }

	  public void setViewport(Viewport viewport) { this.viewport = viewport; }
	}

    @JsonIgnoreProperties(ignoreUnknown = true )
	public static class Review
	{
  	  @JsonProperty("author_name")
	  private String author_name;

	  public String getAuthorName() { return this.author_name; }

	  public void setAuthorName(String author_name) { this.author_name = author_name; }
	  
  	  @JsonProperty("author_url")
	  private String author_url;

	  public String getAuthorUrl() { return this.author_url; }

	  public void setAuthorUrl(String author_url) { this.author_url = author_url; }

  	  @JsonProperty("language")
	  private String language;

	  public String getLanguage() { return this.language; }

	  public void setLanguage(String language) { this.language = language; }

	  private String profile_photo_url;

	  public String getProfilePhotoUrl() { return this.profile_photo_url; }

	  public void setProfilePhotoUrl(String profile_photo_url) { this.profile_photo_url = profile_photo_url; }

  	  @JsonProperty("rating")
	  private int rating;

	  public int getRating() { return this.rating; }

	  public void setRating(int rating) { this.rating = rating; }

	  private String relative_time_description;

	  public String getRelativeTimeDescription() { return this.relative_time_description; }

	  public void setRelativeTimeDescription(String relative_time_description) { this.relative_time_description = relative_time_description; }
	  
  	  @JsonProperty("text")
	  private String text;

	  public String getText() { return this.text; }

	  public void setText(String text) { this.text = text; }

	  private int time;

	  public int getTime() { return this.time; }

	  public void setTime(int time) { this.time = time; }
	}

    @JsonIgnoreProperties(ignoreUnknown = true )
	public static class Result
	{
    	  @JsonProperty("address_components")
	  private ArrayList<AddressComponent> address_components;

	  public ArrayList<AddressComponent> getAddressComponents() { return this.address_components; }

	  public void setAddressComponents(ArrayList<AddressComponent> address_components) { this.address_components = address_components; }

	  private String adr_address;

	  public String getAdrAddress() { return this.adr_address; }

	  public void setAdrAddress(String adr_address) { this.adr_address = adr_address; }

	  private String formatted_address;

	  public String getFormattedAddress() { return this.formatted_address; }

	  public void setFormattedAddress(String formatted_address) { this.formatted_address = formatted_address; }

	  @JsonProperty("formatted_phone_number")
      private String formatted_phone_number;

	  public String getFormattedPhoneNumber() { return this.formatted_phone_number; }

	  public void setFormattedPhoneNumber(String formatted_phone_number) { this.formatted_phone_number = formatted_phone_number; }

	  private Geometry geometry;

	  public Geometry getGeometry() { return this.geometry; }

	  public void setGeometry(Geometry geometry) { this.geometry = geometry; }

	  private String icon;

	  public String getIcon() { return this.icon; }

	  public void setIcon(String icon) { this.icon = icon; }

	  private String id;

	  public String getId() { return this.id; }

	  public void setId(String id) { this.id = id; }

	  @JsonProperty("international_phone_number")
	  private String international_phone_number;

	  public String getInternationalPhoneNumber() { return this.international_phone_number; }

	  public void setInternationalPhoneNumber(String international_phone_number) { this.international_phone_number = international_phone_number; }

	  private String name;

	  public String getName() { return this.name; }

	  public void setName(String name) { this.name = name; }

	  private String place_id;

	  public String getPlaceId() { return this.place_id; }

	  public void setPlaceId(String place_id) { this.place_id = place_id; }

	  private double rating;

	  public double getRating() { return this.rating; }

	  public void setRating(double rating) { this.rating = rating; }

	  private String reference;

	  public String getReference() { return this.reference; }

	  public void setReference(String reference) { this.reference = reference; }

	  @JsonProperty("reviews")
	  private ArrayList<Review> reviews;

	  public ArrayList<Review> getReviews() { return this.reviews; }

	  public void setReviews(ArrayList<Review> reviews) { this.reviews = reviews; }

	  private String scope;

	  public String getScope() { return this.scope; }

	  public void setScope(String scope) { this.scope = scope; }

	  private ArrayList<String> types;

	  public ArrayList<String> getTypes() { return this.types; }

	  public void setTypes(ArrayList<String> types) { this.types = types; }

	  private String url;

	  public String getUrl() { return this.url; }

	  public void setUrl(String url) { this.url = url; }

	  private int utc_offset;

	  public int getUtcOffset() { return this.utc_offset; }

	  public void setUtcOffset(int utc_offset) { this.utc_offset = utc_offset; }
	  
	  @JsonProperty("vicinity")
	  private String vicinity;

	  public String getVicinity() { return this.vicinity; }

	  public void setVicinity(String vicinity) { this.vicinity = vicinity; }

	  private String website;

	  public String getWebsite() { return this.website; }

	  public void setWebsite(String website) { this.website = website; }
	}
}