package com.locdata.Legal.TaxPrep;

public class HRCanadaEntity {
	
	private String address;
	private String storeId;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private double latitude;
	private double longitude;
	private String phone;
	private String fax;
	private String email;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
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
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	 public int hashCode(){
	        int prime = 31;
	        int hashcode = 0;
	        hashcode = prime * address.hashCode();
	        hashcode += prime * storeId.hashCode();
	        hashcode += prime * phone.hashCode();
	        hashcode += prime * city.hashCode();
	        return hashcode;
	    }
	     
	    public boolean equals(Object obj){
	        if (obj instanceof HRCanadaEntity) {
	        	HRCanadaEntity pp = (HRCanadaEntity) obj;
	            return (pp.address.equals(this.address) && pp.storeId.equals(this.storeId) && pp.phone.equals(this.phone) && pp.city.equals(this.city));
	        } else {
	            return false;
	        }
	    }
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("address --> " + this.address + " StoreID  ---> " + this.storeId 
				+ " Phone ------> "+ this.phone + " City =======> " + this.city);
		return builder.toString();
	}
	
}
