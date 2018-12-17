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

import com.spring.Model.Reservation;
import com.spring.Model.Restaurant;
import com.spring.Services.Time;
import com.spring.dao.ReservationDAO;
import com.spring.dao.RestaurantDAO;

@Controller
public class ReservationController {

	@Autowired
	ReservationDAO reservationDAO;

	@Autowired
	RestaurantDAO restaurantDAO;

	@RequestMapping(value = "/restaurants/{restaurantID}/reservation", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<List<Time>> time(@RequestBody Reservation reservation,
			@PathVariable("restaurantID") int id) {
		
		Restaurant restaurant = restaurantDAO.viewRestaurant(id);
		int capacity = restaurant.getCapacityOfPeople();
		
		
		System.out.println(reservation.getDate()+"test");
		if (reservationDAO.checkAvailableTime(reservation, capacity) != null) {
			return new ResponseEntity<List<Time>>(reservationDAO.checkAvailableTime(reservation, capacity),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<Time>>(reservationDAO.checkAvailableTime(reservation, capacity),
				HttpStatus.BAD_REQUEST);
		
	}

	@RequestMapping(value = "/restaurant/{restaurantID}/reservation/make/{personID}", method = { RequestMethod.POST })
	@ResponseBody
	public HttpStatus makeReservation(@RequestBody Reservation reservation, @PathVariable("personID") int personID,
			@PathVariable("restaurantID") int restaurantID) {
		if (personID != 0 && restaurantID != 0 && reservation != null ) {
			reservationDAO.makeReservation(reservation, personID, restaurantID);
			return HttpStatus.CREATED;
		}
		return HttpStatus.BAD_REQUEST;
	}

	@RequestMapping(value = "/reservation/cancel/{personID}", method = { RequestMethod.POST })
	@ResponseBody
	public HttpStatus cancelReservation(@RequestBody Reservation reservation, @PathVariable("personID") int personID) {

		reservationDAO.cancelReservation(reservation);
		return HttpStatus.OK;

	}
	
	@RequestMapping(value = "/reservation/{personID}", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<List<Reservation>>showMyReservations(@PathVariable("personID") int id){
		
		if(reservationDAO.showMyReservation(id)!=null) {
		return new ResponseEntity<List<Reservation>>(reservationDAO.showMyReservation(id), HttpStatus.OK);
		
		}
		return new ResponseEntity<List<Reservation>>(reservationDAO.showMyReservation(id),HttpStatus.BAD_REQUEST);
		
	}
	
	
	@RequestMapping(value="/reservations",method= {RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<List<Reservation>>reservations(){
	
		return new ResponseEntity<List<Reservation>>(reservationDAO.showAllReservation(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/reservations/myrestaurant/{restaurantID}",method= {RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<List<Reservation>>myRestaurantReservation(@PathVariable("restaurantID")int restaurantID){
	
		return new ResponseEntity<List<Reservation>>(reservationDAO.showMyRestaurantReservationList(restaurantID),HttpStatus.OK);
	}
	
	
	

}








