package com.warsztat.jdbc.model;

public class Action {
	
	private int action_id;
	private String name;
	private String desc;
	private double price;
	
	public Action() {
		super();
	}
	
	public Action(int action_id, String name, String desc, double price) {
		super();
		this.action_id = action_id;
		this.name = name;
		this.desc = desc;
		this.price = price;
	}
	
	
	public int getAction_id() {
		return action_id;
	}
	public void setAction_id(int action_id) {
		this.action_id = action_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
