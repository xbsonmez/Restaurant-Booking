package com.spring.dao;

import java.util.List;

import com.spring.Model.Restaurant;
import com.spring.Services.Search;

public interface RestaurantDAO {

	public List<Restaurant> showMyRestaurants(int id);

	// viewRestaurant
	public Restaurant viewRestaurant(int id);

	// show all enable restaurants
	public List<Restaurant> showRestaurantList();

	//admin can see waiting for approve list 
	public List<Restaurant> waitingForApproveList();

	// admın can see waiting for update list
	public List<Restaurant> waitingForUpdateList();

	// admin can see waiting for delete list

	public List<Restaurant> waitingForDeleteList();

	// admin reject
	public void Reject(Restaurant restaurant);

	// admın
	public void approveRestaurant(Restaurant restaurant);

	// manager
	public void updateRestaurant(Restaurant restaurant);

	// manager
	public void createRestaurant(Restaurant restaurant);

	// manager
	public void onlineRestaurant(Restaurant restaurant);

	// manager
	public void offlineRestaurant(Restaurant restaurant);

	// searchRestaurant
	public List<Restaurant> searchRestaurant(String s);

}
