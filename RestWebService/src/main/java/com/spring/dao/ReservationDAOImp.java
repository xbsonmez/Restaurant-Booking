package com.spring.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.Model.Reservation;

@Repository
public class ReservationDAOImp implements ReservationDAO{

	private DataSource dataSource;
	private NamedParameterJdbcTemplate temp;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		temp = new NamedParameterJdbcTemplate(dataSource);
	}

	public int checkAvailableTime(int NumberOfPeople) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void makeReservation(Reservation reservation) {
		// TODO Auto-generated method stub
		
	}
	
	
}
