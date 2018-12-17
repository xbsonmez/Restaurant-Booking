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
import com.spring.Services.Search;
import com.spring.dao.PersonDAO;
import com.spring.dao.RestaurantDAO;

@Controller
public class RestaurantController {

	@Autowired
	RestaurantDAO restaurantDAO;
	PersonDAO personDAO;

	@RequestMapping(value = "/restaurant/{personID}", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<List<Restaurant>> findMyRestaurant(@PathVariable("personID") int id) {

		if (restaurantDAO.showMyRestaurants(id) != null) {

			return new ResponseEntity<List<Restaurant>>(restaurantDAO.showMyRestaurants(id), HttpStatus.OK);
		}

		return new ResponseEntity<List<Restaurant>>(restaurantDAO.showMyRestaurants(id), HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/restaurants", method = { RequestMethod.GET })
	@ResponseBody
	public List<Restaurant> showRestaurant() {

		return restaurantDAO.showRestaurantList();

	}
	
	@RequestMapping(value = "/restaurants/search", method = { RequestMethod.POST })
	@ResponseBody
	public List<Restaurant> showSearchRestaurant(@RequestBody Search s) {
		
		if(s.equals("")) {
		 return  restaurantDAO.showRestaurantList();
		}
		else
			return restaurantDAO.searchRestaurant(s.getSearch());
					
					
		
	}
	
	
	@RequestMapping(value = "/restaurants/{restaurantID}", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<Restaurant>showRestaurantByID(@PathVariable("restaurantID") int id ){
		if(restaurantDAO.viewRestaurant(id)!=null) {
			return new ResponseEntity<Restaurant>(restaurantDAO.viewRestaurant(id),HttpStatus.OK);
			
		}
		
		return new ResponseEntity<Restaurant>(restaurantDAO.viewRestaurant(id),HttpStatus.BAD_REQUEST);
		
	}

	
	
	
	
	// restaurant Approve
	@RequestMapping(value = "/restaurant/approve", method = { RequestMethod.POST })
	@ResponseBody
	public HttpStatus approveRestaurant(@RequestBody Restaurant restaurant) {

		restaurantDAO.approveRestaurant(restaurant);
		return HttpStatus.OK;

	}

	
	@RequestMapping(value = "/restaurant/create", method = { RequestMethod.POST })
	@ResponseBody
	public HttpStatus createRestaurant(@RequestBody Restaurant restaurant) {
		
		
		restaurantDAO.createRestaurant(restaurant);

		return HttpStatus.OK;
	}

	@RequestMapping(value = "/restaurant/online", method = { RequestMethod.POST })
	@ResponseBody
	public HttpStatus setOnlineRestaurant(@RequestBody Restaurant restaurant) {
		restaurantDAO.onlineRestaurant(restaurant);
		return HttpStatus.OK;
	}

	@RequestMapping(value = "/restaurant/offline", method = { RequestMethod.POST })
	@ResponseBody
	public HttpStatus setOfflineRestaurant(@RequestBody Restaurant restaurant) {
		restaurantDAO.offlineRestaurant(restaurant);
		return HttpStatus.OK;
	}
	
	@RequestMapping(value = "/restaurants/waiting/approvelist", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<List<Restaurant>> showWaitingApproveRestaurant() {
		
		return new ResponseEntity<List<Restaurant>>(restaurantDAO.waitingForApproveList(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/restaurants/waiting/deletelist", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<List<Restaurant>>showingForDeleteList() {
		
		return new ResponseEntity<List<Restaurant>>(restaurantDAO.waitingForDeleteList(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/restaurants/waiting/updatelist", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<List<Restaurant>> showWaitingUpdateRestaurant() {
		
		return new ResponseEntity<List<Restaurant>>(restaurantDAO.waitingForUpdateList(),HttpStatus.OK);
		
	
	

}			
	@RequestMapping(value = "/restaurant/reject", method = { RequestMethod.POST })
@ResponseBody
public HttpStatus rejectRestaurant(@RequestBody Restaurant restaurant) {

	restaurantDAO.Reject(restaurant);
	return HttpStatus.OK;

}
	
	
	
	
}
