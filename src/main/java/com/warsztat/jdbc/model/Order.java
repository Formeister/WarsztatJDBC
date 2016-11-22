package com.warsztat.jdbc.model;

import java.sql.Date;

public class Order {

	private int order_id;
	private int customer_id;
	private int action_id;
	private Date order_date;
	private String details;
	
	public Order() {
		super();
	}

	public Order(int order_id, int customer_id, int action_id, Date order_date, String details) {
		super();
		this.order_id = order_id;
		this.customer_id = customer_id;
		this.action_id = action_id;
		this.order_date = order_date;
		this.details = details;
	}



	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getAction_id() {
		return action_id;
	}
	public void setAction_id(int action_id) {
		this.action_id = action_id;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}
