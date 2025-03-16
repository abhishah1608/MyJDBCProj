package com.demo.model;

public class Address {
	
	private int addressId;
	
	private String street;
	
	private String zipCode;
	
	private String city;

	private String State;
	
	private String Country;

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}
	
	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", street=" + street + ", zipCode=" + zipCode + ", city=" + city
				+ ", State=" + State + ", Country=" + Country + "]";
	}
}
