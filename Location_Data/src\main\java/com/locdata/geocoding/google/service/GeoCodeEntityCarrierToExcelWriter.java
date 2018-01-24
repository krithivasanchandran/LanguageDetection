package com.locdata.geocoding.google.service;

/*
 * 
 * Store the bean as a list of JSON responses and send it to excel writer
 * 
 * Data that we would require 
 *  1. int storenumber
 *  2. storename
 *  3. Address
 *  4. City
 *  5. State
 *  6. Zip code
 *  7. Phone number
 *  8. Latitude
 *  9. Longitude
 *  10. Geo Accuracy (location_type)
 *  11. Country
 *  12. Country Code
 *  13. County
 * 
 */

public class GeoCodeEntityCarrierToExcelWriter {
	
	private int storenumber;
	private String storename;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String phoneNumber; 
	private double latitude;
	private double longitude;
	private String locationtype;
	private String country;
	private String countryCode;
	private String county;
	private String placeId;
	
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public int getStorenumber() {
		return storenumber;
	}
	public void setStorenumber(int storenumber) {
		this.storenumber = storenumber;
	}
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getLocationtype() {
		return locationtype;
	}
	public void setLocationtype(String locationtype) {
		this.locationtype = locationtype;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
}
