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
import com.spring.Services.Search;

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
				restaurant.setRestaurantName(rs.getString("restaurantName"));
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
				restaurant.setRestaurantName(rs.getString("restaurantName"));
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

	public List<Restaurant> waitingForApproveList() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();

			String SQL = "SELECT  * FROM restaurantbooking.restaurant where enable='0'" +"AND isApproved='0' AND isAnswer='0'AND isFreeze='0'";

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

	// updateRestaurant for admin  set approve true
	public void approveRestaurant(Restaurant restaurant) {

		Map<String, Object> params = new HashMap<String, Object>();

		String SQL = "update restaurant set isApproved='1',isAnswer='1',enable='1' where restaurantID=:restaurantID ";

		params.put("restaurantID", restaurant.getRestaurantID());

		temp.update(SQL, params);

	}
		//Apply delete restaurant
	public void deleteRestaurantRequest(Restaurant restaurant) {

		Map<String, Object> params = new HashMap<String, Object>();

		String SQL = "update restaurant set isApproved='0',isAnswer='1',enable='0',isFreeze='0' where restaurantID=:restaurantID ";

		params.put("restaurantID", restaurant.getRestaurantID());

		temp.update(SQL, params);

	}	
		//Create DELETE restaurant Request
		public void deleteRestaurant(Restaurant restaurant) {

		restaurant.setEnable(false);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("location", restaurant.getLocation());
		params.put("telNo", restaurant.getTelNo());
		params.put("capacityOfPeple", restaurant.getCapacityOfPeople());
		params.put("capacityOfTable", restaurant.getCapacityOfTable());

		params.put("enable", restaurant.isEnable());
		params.put("features", restaurant.getFeaturesInfo());
		params.put("isApproved", 0);
		params.put("isUpdate", 0);
		params.put("isFreeze", 1);
		
		temp.update(
				"insert into restaurantbooking.restaurant(location,telNo,password,capacityOfPeple,capacityOfTable,enable,features,isApproved,isUpdate,isFreeze) values (:location,:telNo,:password,:capacityOfPeple,:capacityOfTable,:enable,:features,:isApproved,:isUpdate,:isFreeze)",
				params);

	}


	// update Restaurant Request
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
		params.put("isUpdate", 1);
		temp.update(
				"insert into restaurantbooking.restaurant(location,telNo,password,capacityOfPeple,capacityOfTable,enable,features,isApproved,isUpdate) values (:location,:telNo,:password,:capacityOfPeple,:capacityOfTable,:enable,:features,:isApproved,:isUpdate)",
				params);

	}

	// create Restaurant 
	public void createRestaurant(Restaurant restaurant) {

		restaurant.setEnable(false);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("location", restaurant.getLocation());
		params.put("telNo", restaurant.getTelNo());
		params.put("capacityOfPeople", restaurant.getCapacityOfPeople());
		params.put("capacityOfTable", restaurant.getCapacityOfTable());
		params.put("personID",restaurant.getOwnerID());
		params.put("enable", restaurant.isEnable());
		params.put("features", restaurant.getFeaturesInfo());
		params.put("isApproved", 0);
		params.put("restaurantName", restaurant.getRestaurantName());
		params.put("isAnswer",0);
		params.put("isFreeze",0);
		params.put("isUpdate", 0);
		temp.update(
				"insert into restaurantbooking.restaurant(location,telNo,capacityOfPeople,capacityOfTable,personID,enable,features,isApproved,restaurantName,isAnswer,isFreeze,isUpdate) values (:location,:telNo,:capacityOfPeople,:capacityOfTable,:personID,:enable,:features,:isApproved,:restaurantName,:isAnswer,:isFreeze,:isUpdate)",
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
	public void Reject(Restaurant restaurant) {
		Map<String, Object> params = new HashMap<String, Object>();
		String SQL = "update restaurant set isAnswer='0'where restaurantID=:restaurantID";
		String SQL2="update restaurant set enable='0'where restaurantID=:restaurantID";
		
		params.put("restaurantID", restaurant.getRestaurantID());

		temp.update(SQL, params);
		temp.update(SQL2, params);

	}
	public void delete(Restaurant restaurant) {
		Map<String, Object> params = new HashMap<String, Object>();
		String SQL = "update restaurant set isAnswer='0'where restaurantID=:restaurantID";
		String SQL2="update restaurant set enable='0'where restaurantID=:restaurantID";
		String SQL3="update restaurant set isFreeze='1'where restaurantID=:restaurantID";
		
		params.put("restaurantID", restaurant.getRestaurantID());

		temp.update(SQL, params);
		temp.update(SQL2, params);
		temp.update(SQL3, params);
		

	}

	public Restaurant viewRestaurant(int id) {
		Restaurant restaurant = new Restaurant();
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();

			String SQL = "SELECT * FROM restaurant where restaurantID='" + id + "'";

			ResultSet rs = stmt.executeQuery(SQL);
			if (rs.next()) {

				restaurant.setApproved(rs.getBoolean("isApproved"));
				restaurant.setCapacityOfPeople(rs.getInt("capacityOfPeople"));
				restaurant.setCapacityOfTable(rs.getInt("capacityOfTable"));
				restaurant.setEnable(rs.getBoolean("enable"));
				restaurant.setFeaturesInfo(rs.getString("features"));
				restaurant.setLocation(rs.getString("location"));
				restaurant.setOwnerID(rs.getInt("personID"));
				restaurant.setRate(rs.getInt("rate"));
				restaurant.setRestaurantID(rs.getInt("restaurantID"));
				restaurant.setTelNo(rs.getString("telNo"));
				restaurant.setRestaurantName(rs.getString("restaurantName"));

			}

			stmt.close();
			conn.close();
		}

		catch (Exception e) {

			System.out.println("ERROR Connection");
		}

		return restaurant;
	}

	public List<Restaurant> searchRestaurant(String s) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();

			String SQL = "SELECT  * FROM restaurantbooking.restaurant where enable='1'" + "AND isApproved='1'"+"AND (restaurantName like '%"+s+"%' OR location like '%"+s+"%')";

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
				restaurant.setRestaurantName(rs.getString("restaurantName"));
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
	
	public List<Restaurant> waitingForUpdateList() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();

			String SQL = "SELECT  * FROM restaurantbooking.restaurant where enable='0'" + "AND isApproved='0'AND isAnswer='0'AND isUpdate='1'";

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

	public List<Restaurant> waitingForDeleteList() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();

			String SQL = "SELECT  * FROM restaurantbooking.restaurant where enable='0'" + "AND isApproved='0'AND isAnswer='0'AND isFreeze='1'";

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

	
	
	
	
	
}
