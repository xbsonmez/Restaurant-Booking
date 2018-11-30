package com.spring.dao;

import com.spring.Model.Reservation;

public interface ReservationDAO {

	public int checkAvailableTime(int NumberOfPeople);
	
	public void makeReservation(Reservation reservation);
	
	
}
