package com.locdata.theatres.entity;

public class EntityTheatre {
	
	private String address;
	private String phoneNumber;
	private String countyName;
	private String theatreWebsite;
	private String theatreStoreName;

	public String getTheatreStoreName() {
		return theatreStoreName;
	}
	public void setTheatreStoreName(String theatreStoreName) {
		this.theatreStoreName = theatreStoreName;
	}
	public String getTheatreWebsite() {
		return theatreWebsite;
	}
	public EntityTheatre setTheatreWebsite(String theatreWebsite) {
		this.theatreWebsite = theatreWebsite;
		return this;
	}
	public String getCountyName() {
		return countyName;
	}
	public EntityTheatre setCountyName(String countyName) {
		this.countyName = countyName;
		return this; 
	}
	public String getAddress() {
		return address;
	}
	public EntityTheatre setAddress(String address) {
		this.address = address;
		return this;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
