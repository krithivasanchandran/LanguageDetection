package com.locdata.theatres.entity;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true )
@JsonDeserialize(contentAs = FamilVideoJsonResponse.class)
public class FamilVideoJsonResponse {
	
	  public FamilVideoJsonResponse(){};
	
	  @JsonProperty("storesjson")
	  private ArrayList<Storesjson> storesjson;

	  public ArrayList<Storesjson> getStoresjson() { return this.storesjson; }

	  public void setStoresjson(ArrayList<Storesjson> storesjson) { this.storesjson = storesjson; }

	  @JsonProperty("pagination")
	  private String pagination;

	  public String getPagination() { return this.pagination; }

	  public void setPagination(String pagination) { this.pagination = pagination; }

	  @JsonProperty("num_store")
	  private int num_store;

	  public int getNumStore() { return this.num_store; }

	  public void setNumStore(int num_store) { this.num_store = num_store; }
	  
	  @JsonIgnoreProperties(ignoreUnknown = true )
	  public static class Storesjson
		{
		  
		  public Storesjson() {}
		  
		  @JsonProperty("storelocator_id")
		  private String storelocator_id;

		  public String getStorelocatorId() { return this.storelocator_id; }

		  public void setStorelocatorId(String storelocator_id) { this.storelocator_id = storelocator_id; }

		  @JsonProperty("store_name")
		  private String store_name;

		  public String getStoreName() { return this.store_name; }

		  public void setStoreName(String store_name) { this.store_name = store_name; }

		  @JsonProperty("phone")
		  private String phone;

		  public String getPhone() { return this.phone; }

		  public void setPhone(String phone) { this.phone = phone; }

		  @JsonProperty("address")
		  private String address;

		  public String getAddress() { return this.address; }

		  public void setAddress(String address) { this.address = address; }

		  @JsonProperty("latitude")
		  private String latitude;

		  public String getLatitude() { return this.latitude; }

		  public void setLatitude(String latitude) { this.latitude = latitude; }

		  @JsonProperty("longitude")
		  private String longitude;

		  public String getLongitude() { return this.longitude; }

		  public void setLongitude(String longitude) { this.longitude = longitude; }

		  @JsonProperty("marker_icon")
		  private String marker_icon;

		  public String getMarkerIcon() { return this.marker_icon; }

		  public void setMarkerIcon(String marker_icon) { this.marker_icon = marker_icon; }

		  @JsonProperty("zoom_level")
		  private String zoom_level;

		  public String getZoomLevel() { return this.zoom_level; }

		  public void setZoomLevel(String zoom_level) { this.zoom_level = zoom_level; }
		  
		  @JsonProperty("rewrite_request_path")
		  private String rewrite_request_path;

		  public String getRewriteRequestPath() { return this.rewrite_request_path; }

		  public void setRewriteRequestPath(String rewrite_request_path) { this.rewrite_request_path = rewrite_request_path; }

		  @JsonProperty("distance")
		  private String distance;

		  public String getDistance() { return this.distance; }

		  public void setDistance(String distance) { this.distance = distance; }

		  @JsonProperty("baseimage")
		  private Object baseimage;

		  public Object getBaseimage() { return this.baseimage; }

		  public void setBaseimage(Object baseimage) { this.baseimage = baseimage; }
		}
	}