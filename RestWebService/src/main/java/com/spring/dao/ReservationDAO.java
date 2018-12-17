package com.spring.dao;

import java.util.List;

import com.spring.Model.Reservation;
import com.spring.Services.Time;

public interface ReservationDAO {

	public List<Time> checkAvailableTime(Reservation reservation, int capacity);

	public void makeReservation(Reservation reservation, int personID, int restaurantID);

	public void cancelReservation(Reservation reservation);
	
	
	// for customer show reservation List
	public List<Reservation> showMyReservation(int personID);
	
	
	//test
	public List<Reservation> showAllReservation();
	
	//belli bir yere reservasyon yapan kullanýcýlarýn listesi
	
	public List<Reservation>showMyRestaurantReservationList(int restaurantID);
	

	
	
}
