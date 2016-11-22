package com.warsztat.jdbc.model;

public class Customer {
	
	private int customer_id;
	private String name;
	private long pesel;
	private String address;
	
	public Customer() {
		super();
	}
	

	public Customer(int customer_id, String name, long pesel, String address) {
		super();
		this.customer_id = customer_id;
		this.name = name;
		this.pesel = pesel;
		this.address = address;
	}


	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPesel() {
		return pesel;
	}
	public void setPesel(Long pesel) {
		this.pesel = pesel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
