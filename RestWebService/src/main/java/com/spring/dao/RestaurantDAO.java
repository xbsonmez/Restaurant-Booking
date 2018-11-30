package com.spring.dao;

import java.util.List;


import com.spring.Model.Restaurant;

public interface RestaurantDAO {

	public List<Restaurant> showMyRestaurants(int id);
	
	public List<Restaurant> showRestaurantList();
	
	public List<Restaurant> waitingForApproveList(int id);

	//admın
	public void approveRestaurant(Restaurant restaurant);
	
	//manager
	public void updateRestaurant(Restaurant restaurant);
	
	//manager
	public void createRestaurant(Restaurant restaurant,int id);
	
	//manager
	public void onlineRestaurant(Restaurant restaurant);
	//manager
	public void offlineRestaurant(Restaurant restaurant);
	
	
	
	
}
