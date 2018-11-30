package com.spring.Model;

public class Restaurant {

	private int  restaurantID;
	private String telNo;
	private int capacityOfPeople;
	private int capacityOfTable;
	private int rate;
	private int ownerID;
	private boolean enable;
	private String featuresInfo;
	private String location;
	private boolean isApproved;
	
	
	public Restaurant() {
		
	}
	


	public boolean isApproved() {
		return isApproved;
	}



	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}



	public int getRestaurantID() {
		return restaurantID;
	}


	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}


	public String getTelNo() {
		return telNo;
	}


	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}


	public int getCapacityOfPeople() {
		return capacityOfPeople;
	}


	public void setCapacityOfPeople(int capacityOfPeople) {
		this.capacityOfPeople = capacityOfPeople;
	}


	public int getCapacityOfTable() {
		return capacityOfTable;
	}


	public void setCapacityOfTable(int capacityOfTable) {
		this.capacityOfTable = capacityOfTable;
	}


	public int getRate() {
		return rate;
	}


	public void setRate(int rate) {
		this.rate = rate;
	}


	public int getOwnerID() {
		return ownerID;
	}


	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}


	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}


	public String getFeaturesInfo() {
		return featuresInfo;
	}


	public void setFeaturesInfo(String featuresInfo) {
		this.featuresInfo = featuresInfo;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}
	
	
	 
}
