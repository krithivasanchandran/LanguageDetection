package com.locdata.geocoding.google.service;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/*
 * Json Response Class generated from - http://json2java.azurewebsites.net/
 * 
 *  Sample Response
 *  
 *  {
  "results": [
    {
      "address_components": [
        {
          "long_name": "3234",
          "short_name": "3234",
          "types": [
            "street_number"
          ]
        },
        {
          "long_name": "South Clack Street",
          "short_name": "S Clack St",
          "types": [
            "route"
          ]
        },
        {
          "long_name": "Park Central Area",
          "short_name": "Park Central Area",
          "types": [
            "neighborhood",
            "political"
          ]
        },
        {
          "long_name": "Abilene",
          "short_name": "Abilene",
          "types": [
            "locality",
            "political"
          ]
        },
        {
          "long_name": "Taylor County",
          "short_name": "Taylor County",
          "types": [
            "administrative_area_level_2",
            "political"
          ]
        },
        {
          "long_name": "Texas",
          "short_name": "TX",
          "types": [
            "administrative_area_level_1",
            "political"
          ]
        },
        {
          "long_name": "United States",
          "short_name": "US",
          "types": [
            "country",
            "political"
          ]
        },
        {
          "long_name": "79606",
          "short_name": "79606",
          "types": [
            "postal_code"
          ]
        }
      ],
      "formatted_address": "3234 S Clack St, Abilene, TX 79606, USA",
      "geometry": {
        "location": {
          "lat": 32.4095665,
          "lng": -99.7742875
        },
        "location_type": "ROOFTOP",
        "viewport": {
          "northeast": {
            "lat": 32.4109154802915,
            "lng": -99.77293851970849
          },
          "southwest": {
            "lat": 32.4082175197085,
            "lng": -99.7756364802915
          }
        }
      },
      "place_id": "ChIJl40NlzaMVoYR6dTBNmKKEgs",
      "types": [
        "street_address"
      ]
    }
  ],
  "status": "OK"
}
 * 
 */

public class GeoCodingJsonResponse {
	
	  @JsonProperty("results")
	  private ArrayList<Result> results;

	  public ArrayList<Result> getResults() { return this.results; }

	  public void setResults(ArrayList<Result> results) { this.results = results; }

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
	  
	  @JsonProperty("location_type")
	  private String location_type;

	  public String getLocationType() { return this.location_type; }

	  public void setLocationType(String location_type) { this.location_type = location_type; }
	  
	  @JsonProperty("viewport")
	  private Viewport viewport;

	  public Viewport getViewport() { return this.viewport; }

	  public void setViewport(Viewport viewport) { this.viewport = viewport; }
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true )
	public static class Result
	{
	  @JsonProperty("address_components")
	  private ArrayList<AddressComponent> address_components;

	  public ArrayList<AddressComponent> getAddressComponents() { return this.address_components; }

	  public void setAddressComponents(ArrayList<AddressComponent> address_components) { this.address_components = address_components; }

	  @JsonProperty("formatted_address")
	  private String formatted_address;

	  public String getFormattedAddress() { return this.formatted_address; }

	  public void setFormattedAddress(String formatted_address) { this.formatted_address = formatted_address; }

	  @JsonProperty("geometry")
	  private Geometry geometry;

	  public Geometry getGeometry() { return this.geometry; }

	  public void setGeometry(Geometry geometry) { this.geometry = geometry; }

	  @JsonProperty("place_id")
	  private String place_id;

	  public String getPlaceId() { return this.place_id; }

	  public void setPlaceId(String place_id) { this.place_id = place_id; }
	  
	  @JsonProperty("types")
	  private ArrayList<String> types;

	  public ArrayList<String> getTypes() { return this.types; }

	  public void setTypes(ArrayList<String> types) { this.types = types; }
	}
	
	 @Override
     public String toString() {
         return "Messages:{address: " + this.getResults().get(0).formatted_address + ", PlaceId: " + this.getResults().get(0).place_id + ", Location"
         		+ "_Type: " + this.getResults().get(0).getGeometry().location_type +
                      "}";
     }

}