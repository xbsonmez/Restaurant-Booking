package com.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.Model.Reservation;
import com.spring.Services.Time;

@Repository
public class ReservationDAOImp implements ReservationDAO {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate temp;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		temp = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Time> checkAvailableTime(Reservation reservation, int capacity) {
		List<Time> availableTimes = new ArrayList<Time>();
		int peopleCapacity = capacity;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();

			int numberOfReserved = 0;
			// RestaurantCAPACÝTY
			boolean a=dateCheck(reservation.getDate());
			int b=9;
			if(a==true ) {
				
				Calendar cal = Calendar.getInstance();
			 	SimpleDateFormat sdf = new SimpleDateFormat("HH");  
		        
		        b=Integer.parseInt(sdf.format(cal.getTime()));
		        if(9>b)
		        	b=9;
			}
				
		        
			for (int i =b+1; i < 24; i++) {
				Time t=new Time();
				String SQL = "SELECT numberOfPeoplReserv FROM restaurantbooking.reservation where date ='"
						+ reservation.getDate() + "'";

				ResultSet rs = stmt.executeQuery(SQL);
				
				while (rs.next()) {
					numberOfReserved = numberOfReserved + rs.getInt("numberOfPeoplReserv");

				}
				if (reservation.getNumberOfPeople() + numberOfReserved <= peopleCapacity) {
					t.setTime(i);
					availableTimes.add(t);
					
				}
			
				
			}

			stmt.close();
			conn.close();
		} catch (Exception e) {
			return null;
		}

		return availableTimes;
	}

	public void makeReservation(Reservation reservation, int personID, int restaurantID) {
		Map<String, Object> params = new HashMap<String, Object>();
		reservation.setEnable(true);
		params.put("date", reservation.getDate());
		params.put("numberOfPeoplReserv", reservation.getNumberOfPeople());
		params.put("enable", reservation.isEnable());
		params.put("personID", personID);
		params.put("restaurantID", restaurantID);
		params.put("time", reservation.getTime());
		temp.update(
				"insert into restaurantbooking.reservation(restaurantID,personID,date,numberOfPeoplReserv,enable,time) values (:restaurantID,:personID,:date,:numberOfPeoplReserv,:enable,:time)",
				params);

	}

	public void cancelReservation(Reservation reservation) {

		Map<String, Object> params = new HashMap<String, Object>();

		String SQL = "update reservation set restaurantID=:restaurantID ,personID=:personID,date=:date,numberOfPeoplReserv=:numberOfPeoplReserv,enable=:enable,time=:time where reservationID=:reservationID ";
		// enable is false so cancel
		reservation.setEnable(false);
		params.put("date", reservation.getDate());
		params.put("numberOfPeoplReserv", reservation.getNumberOfPeople());
		params.put("enable", reservation.isEnable());
		params.put("time", reservation.getTime());
		params.put("reservationID", reservation.getId());

		temp.update(SQL, params);

	}

	public List<Reservation> showMyReservation(int personID) {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();
			
			String SQL = "SELECT  * FROM restaurantbooking.reservation  where enable='1' AND personID='" + personID
					+ "'";

			ResultSet rs = stmt.executeQuery(SQL);
			List<Reservation> reservationList = new ArrayList<Reservation>();
			while (rs.next()) {
				Reservation reservation = new Reservation();

				reservation.setEnable(rs.getBoolean("enable"));
				reservation.setDate(rs.getString("date"));
				reservation.setId(rs.getInt("reservationID"));

				reservation.setNumberOfPeople(rs.getInt("numberOfPeoplReserv"));

				reservation.setTime(rs.getInt("time"));

				reservation.setRestaurantID(rs.getInt("restaurantID"));

				reservationList.add(reservation);

			}
			stmt.close();
			conn.close();
			return reservationList;
		} catch (Exception e) {

			System.out.println("ERROR Connection");
		}
		return null;

	}

	public List<Reservation> showAllReservation() {
	try {
			
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();
			
			String SQL = "SELECT  * FROM restaurantbooking.reservation  where enable='1' ";

			ResultSet rs = stmt.executeQuery(SQL);
			List<Reservation> reservationList = new ArrayList<Reservation>();
			while (rs.next()) {
				Reservation reservation = new Reservation();

				reservation.setEnable(rs.getBoolean("enable"));
				reservation.setDate(rs.getString("date"));
				reservation.setId(rs.getInt("reservationID"));

				reservation.setNumberOfPeople(rs.getInt("numberOfPeoplReserv"));

				reservation.setTime(rs.getInt("time"));

				reservation.setRestaurantID(rs.getInt("restaurantID"));

				reservationList.add(reservation);

			}
			stmt.close();
			conn.close();
			return reservationList;
		} catch (Exception e) {

			System.out.println("ERROR Connection");
		}
		return null;

	}

	public boolean dateCheck(String s) {
		 
	    Date myDate = new Date();
		
		SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
		String mdy = mdyFormat.format(myDate);
		if(s.equals(mdy)) {
		return true;
		}	
		return false;
	}

	public List<Reservation> showMyRestaurantReservationList(int restaurantID) {
try {
			
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();
			
			String SQL = "SELECT  * FROM restaurantbooking.reservation  where enable='1' AND restaurantID='" + restaurantID
					+ "'";

			ResultSet rs = stmt.executeQuery(SQL);
			List<Reservation> reservationList = new ArrayList<Reservation>();
			while (rs.next()) {
				Reservation reservation = new Reservation();

				reservation.setEnable(rs.getBoolean("enable"));
				reservation.setDate(rs.getString("date"));
				reservation.setId(rs.getInt("reservationID"));

				reservation.setNumberOfPeople(rs.getInt("numberOfPeoplReserv"));

				reservation.setTime(rs.getInt("time"));

				reservation.setRestaurantID(rs.getInt("restaurantID"));

				reservationList.add(reservation);

			}
			stmt.close();
			conn.close();
			return reservationList;
		} catch (Exception e) {

			System.out.println("ERROR Connection");
		}
		return null;

	}

}
