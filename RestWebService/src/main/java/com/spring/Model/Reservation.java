package com.spring.Model;

public class Reservation {
	private int id;
	private String date;
	private int numberOfPeople;
	private boolean enable;
	private int time;
    private int restaurantID;
	
	
	public Reservation() {

	}
	
	
	

	public int getRestaurantID() {
		return restaurantID;
	}




	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}




	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getTime() {
		return time;
	}



	public void setTime(int time) {
		this.time = time;
	}



	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
