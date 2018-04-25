package com.apexdrop.jewellery;

public class BusinessCard {
	
	private String personName;
	private String designation;
	private String website;
	private String Address;
	private String businessName;
	private String headquartersAddress;
	public String getHeadquartersAddress() {
		return headquartersAddress;
	}
	public void setHeadquartersAddress(String headquartersAddress) {
		this.headquartersAddress = headquartersAddress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIndustries() {
		return Industries;
	}
	public void setIndustries(String industries) {
		Industries = industries;
	}
	public String getEmployees() {
		return employees;
	}
	public void setEmployees(String employees) {
		this.employees = employees;
	}
	public String getCompanyrevenue() {
		return companyrevenue;
	}
	public void setCompanyrevenue(String companyrevenue) {
		this.companyrevenue = companyrevenue;
	}
	private String phone;
	private String Industries;
	private String employees;
	private String companyrevenue;
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}

}
