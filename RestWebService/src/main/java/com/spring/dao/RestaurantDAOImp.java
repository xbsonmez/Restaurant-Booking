package com.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.Model.Restaurant;

@Repository
public class RestaurantDAOImp implements RestaurantDAO {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate temp;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		temp = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Restaurant> showMyRestaurants(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();

			String SQL = "SELECT  * FROM restaurantbooking.restaurant where enable='1' AND personID='" + id + "'";

			ResultSet rs = stmt.executeQuery(SQL);

			List<Restaurant> restaurantList = new ArrayList<Restaurant>();

			while (rs.next()) {

				Restaurant restaurant = new Restaurant();

				restaurant.setCapacityOfPeople(rs.getInt("capacityOfPeople"));
				restaurant.setCapacityOfTable(rs.getInt("capacityOfTable"));
				restaurant.setEnable(rs.getBoolean("enable"));
				restaurant.setFeaturesInfo(rs.getString("features"));
				restaurant.setLocation(rs.getString("location"));
				restaurant.setOwnerID(rs.getInt("personID"));
				restaurant.setRate(rs.getInt("rate"));
				restaurant.setRestaurantID(rs.getInt("restaurantID"));
				restaurant.setTelNo(rs.getString("telNo"));

				restaurantList.add(restaurant);
			}

			stmt.close();
			conn.close();
			return restaurantList;

		} catch (Exception e) {

			System.out.println("ERROR Connection");
		}
		return null;

	}

	public List<Restaurant> showRestaurantList() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();

			String SQL = "SELECT  * FROM restaurantbooking.restaurant where enable='1'" + "AND isApproved='1'";

			ResultSet rs = stmt.executeQuery(SQL);

			List<Restaurant> restaurantList = new ArrayList<Restaurant>();

			while (rs.next()) {

				Restaurant restaurant = new Restaurant();

				restaurant.setCapacityOfPeople(rs.getInt("capacityOfPeople"));
				restaurant.setCapacityOfTable(rs.getInt("capacityOfTable"));
				restaurant.setEnable(rs.getBoolean("enable"));
				restaurant.setFeaturesInfo(rs.getString("features"));
				restaurant.setLocation(rs.getString("location"));
				restaurant.setOwnerID(rs.getInt("personID"));
				restaurant.setRate(rs.getInt("rate"));
				restaurant.setRestaurantID(rs.getInt("restaurantID"));
				restaurant.setTelNo(rs.getString("telNo"));

				restaurantList.add(restaurant);
			}

			stmt.close();
			conn.close();
			return restaurantList;

		} catch (Exception e) {

			System.out.println("ERROR Connection");
		}
		return null;

	}

	public List<Restaurant> waitingForApproveList(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();

			String SQL = "SELECT  * FROM restaurantbooking.restaurant where enable='1'" + "AND isApproved='0'";

			ResultSet rs = stmt.executeQuery(SQL);

			List<Restaurant> restaurantList = new ArrayList<Restaurant>();

			while (rs.next()) {

				Restaurant restaurant = new Restaurant();

				restaurant.setCapacityOfPeople(rs.getInt("capacityOfPeople"));
				restaurant.setCapacityOfTable(rs.getInt("capacityOfTable"));
				restaurant.setEnable(rs.getBoolean("enable"));
				restaurant.setFeaturesInfo(rs.getString("features"));
				restaurant.setLocation(rs.getString("location"));
				restaurant.setOwnerID(rs.getInt("personID"));
				restaurant.setRate(rs.getInt("rate"));
				restaurant.setRestaurantID(rs.getInt("restaurantID"));
				restaurant.setTelNo(rs.getString("telNo"));

				restaurantList.add(restaurant);
			}

			stmt.close();
			conn.close();
			return restaurantList;

		} catch (Exception e) {

			System.out.println("ERROR Connection");
		}
		return null;

	}

	// update is approve true
	public void approveRestaurant(Restaurant restaurant) {

		Map<String, Object> params = new HashMap<String, Object>();

		String SQL = "update restaurant set isApproved='1'where restaurantID=:restaurantID";

		params.put("restaurantID", restaurant.getRestaurantID());

		temp.update(SQL, params);

	}

	// update Restaurant
	public void updateRestaurant(Restaurant restaurant) {

		restaurant.setEnable(false);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("location", restaurant.getLocation());
		params.put("telNo", restaurant.getTelNo());
		params.put("capacityOfPeple", restaurant.getCapacityOfPeople());
		params.put("capacityOfTable", restaurant.getCapacityOfTable());

		params.put("enable", restaurant.isEnable());
		params.put("features", restaurant.getFeaturesInfo());
		params.put("isApproved", 0);
		temp.update(
				"insert into restaurantbooking.restaurant(location,telNo,password,capacityOfPeple,capacityOfTable,enable,features,isApproved) values (:location,:telNo,:password,:capacityOfPeple,:capacityOfTable,:enable,:features,:isApproved)",
				params);

	}

	// create Restaurant
	public void createRestaurant(Restaurant restaurant, int id) {

		restaurant.setEnable(false);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("location", restaurant.getLocation());
		params.put("telNo", restaurant.getTelNo());
		params.put("capacityOfPeple", restaurant.getCapacityOfPeople());
		params.put("capacityOfTable", restaurant.getCapacityOfTable());
		params.put("personID", id);
		params.put("enable", restaurant.isEnable());
		params.put("features", restaurant.getFeaturesInfo());
		params.put("isApproved", 0);
		temp.update(
				"insert into restaurantbooking.restaurant(location,telNo,password,capacityOfPeple,capacityOfTable,personID,enable,features,isApproved) values (:location,:telNo,:password,:capacityOfPeple,:capacityOfTable,:personID,:enable,:features,:isApproved)",
				params);

	}

	public void onlineRestaurant(Restaurant restaurant) {

		Map<String, Object> params = new HashMap<String, Object>();

		String SQL = "update restaurant set enable='1'where restaurantID=:restaurantID";

		params.put("restaurantID", restaurant.getRestaurantID());

		temp.update(SQL, params);

	}

	public void offlineRestaurant(Restaurant restaurant) {
		Map<String, Object> params = new HashMap<String, Object>();

		String SQL = "update restaurant set enable='0'where restaurantID=:restaurantID";

		params.put("restaurantID", restaurant.getRestaurantID());

		temp.update(SQL, params);

	}
}
