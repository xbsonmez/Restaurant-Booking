package com.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.spring.Model.Restaurant;
import com.spring.dao.RestaurantDAO;

@Controller
public class RestaurantController {

	@Autowired
	RestaurantDAO restaurantDAO;
	
	@RequestMapping(value = "/restaurant/{personID}", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<List<Restaurant>> findMyRestaurant(@PathVariable("personID") int id) {
		
		if(restaurantDAO.showMyRestaurants(id)!=null) {
		 
			return new ResponseEntity<List<Restaurant>>(restaurantDAO.showMyRestaurants(id),HttpStatus.OK);
		}
		
		return new ResponseEntity<List<Restaurant>>(restaurantDAO.showMyRestaurants(id),HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	@RequestMapping(value="/restaurant",method= {RequestMethod.GET})
	@ResponseBody
	public List<Restaurant> showRestaurant(){
		
		return  restaurantDAO.showRestaurantList();
		
		
	}
	//restaurant Approve
	@RequestMapping(value="/restaurant/approve",method= {RequestMethod.POST})
	@ResponseBody
	public HttpStatus approveRestaurant(@RequestBody Restaurant restaurant) {
		
		restaurantDAO.approveRestaurant(restaurant);
		return HttpStatus.OK;
		
	}
	
	// id sýkýntý bi daha bak
	@RequestMapping(value="/restaurant/create",method= {RequestMethod.POST})
	@ResponseBody
	public HttpStatus createRestaurant(@RequestBody Restaurant restaurant,int id) {
		
		restaurantDAO.createRestaurant(restaurant, id);
		
		
		return HttpStatus.OK;
	}

	@RequestMapping(value="/restaurant/online",method= {RequestMethod.POST})
	@ResponseBody
	public HttpStatus setOnlineRestaurant(@RequestBody Restaurant restaurant) {
		restaurantDAO.onlineRestaurant(restaurant);
		return HttpStatus.OK;
	}
	
	@RequestMapping(value="/restaurant/offline",method= {RequestMethod.POST})
	@ResponseBody
	public HttpStatus setOfflineRestaurant(@RequestBody Restaurant restaurant) {
		restaurantDAO.offlineRestaurant(restaurant);
		return HttpStatus.OK;
	}
	
	
	
	
}
