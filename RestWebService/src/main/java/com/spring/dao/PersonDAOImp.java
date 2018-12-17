package com.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.Model.Person;

@Repository("com/spring/dao")
public class PersonDAOImp implements PersonDAO {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate temp;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		temp = new NamedParameterJdbcTemplate(dataSource);
	}

	public void save(@RequestBody Person person) {

		person.setEnable(true);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", person.getName());
		params.put("email", person.getEmail());
		params.put("username", person.getUsername());
		params.put("telno", person.getTelno());
		params.put("role", person.getRole());
		params.put("password", person.getPassword());
		params.put("enable", person.isEnable());
		temp.update(
				"insert into restaurantbooking.person(email,username,password,role,telno,name,enable) values (:email,:username,:password,:role,:telno,:name,:enable)",
				params);

	}

	public boolean checkEnabled(@RequestBody Person person) {
		if (person.isEnable() == true)
			return true;
		else
			return false;
	}

	public boolean checkRegisterPasswords(@RequestBody Person person) {

		if (person.getConfirmPassword().equals(person.getPassword())) {
			return true;
		} 
		return false;
	}
	 
	public boolean checkPasswordLength(String s) {
		if(s.length()<5)
			return false;
		
		return true;
	}

	public boolean checkUsername(@RequestBody Person person) {
		boolean flag = true;

		try {

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/restaurantbooking", "root", "");
			Statement stmt = con.createStatement();
			// username control
			String SQL1 = "SELECT  username FROM person WHERE  username='" + person.getUsername() + "'";

			ResultSet rs1 = stmt.executeQuery(SQL1);

			if (rs1.next()) {
				System.out.println("Failure! Already exisis username");
				flag = true;

				System.out.println("rs next:" + flag);
			} else {
				System.out.println("there is not exsist. ");
				flag = false;
			}
			stmt.close();
			con.close();
		} catch (Exception e) {

			System.out.println("ERROR");
		}

		return flag;
	}

	public boolean checkEmail(String email) {

		boolean flag = false;

		try {

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/restaurantbooking", "root", "");
			Statement stmt = con.createStatement();

			// email control
			String SQL2 = "SELECT  email FROM person where email='" + email + "'";
			ResultSet rs2 = stmt.executeQuery(SQL2);

			if (rs2.next()) {
				System.out.println("Failure! Already exisis email check maildaoimp");
				flag = true;

				System.out.println("rs next:" + flag);
			} else {
				System.out.println("there is not exsist. ");
				flag = false;
			}
			stmt.close();
			con.close();
		} catch (Exception e) {

			System.out.println("ERROR");
		}

		return flag;
	}

	public List<Person> loadAll() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();

			String SQL = "SELECT  * FROM restaurantbooking.person";

			ResultSet rs = stmt.executeQuery(SQL);

			List<Person> personList = new ArrayList<Person>();

			while (rs.next()) {

				Person person = new Person();

				person.setName(rs.getString("name"));
				person.setId(rs.getInt("personID"));
				person.setEmail(rs.getString("email"));
				person.setRole(rs.getString("role"));
				person.setTelno(rs.getString("telno"));
				person.setUsername(rs.getString("username"));
				person.setPassword(rs.getString("password"));
				person.setEnable(rs.getBoolean("enable"));

				personList.add(person);
			}

			stmt.close();
			conn.close();
			return personList;

		} catch (Exception e) {

			System.out.println("ERROR Connection");
		}
		return null;

	}

	public Person findPerson(String username, String password) {
		Person currentPerson = new Person();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();

			String SQL = "SELECT * FROM restaurantbooking.person WHERE BINARY username='" + username
					+ "' AND BINARY password='" + password + "'";
			ResultSet rs = stmt.executeQuery(SQL);

			if (rs.next()) {
				currentPerson.setName(rs.getString("name"));
				currentPerson.setEmail(rs.getString("email"));
				currentPerson.setRole(rs.getString("role"));
				currentPerson.setTelno(rs.getString("telno"));
				currentPerson.setUsername(rs.getString("username"));
				currentPerson.setPassword(rs.getString("password"));
				currentPerson.setEnable(rs.getBoolean("enable"));

				currentPerson.setId(rs.getInt("personID"));
			}

			stmt.close();
			conn.close();

			System.out.println("deneme" + currentPerson.getEmail());
			return currentPerson;

		}

		catch (Exception e) {

			System.out.println("ERROR Connection");
		}

		return currentPerson;
	}

	public boolean checktelNo(@RequestBody Person person) {
		if (person.getTelno().length() == 11) {
			return true;
		}
		return false;
	}

	public boolean checkName(@RequestBody Person person) {
		if (person.getName().length() > 0 && isAlpha(person.getName())) {
			return true;
		}
		return false;
	}

	public boolean isAlpha(String name) {
		return name.matches("[a-zA-Z]+");
	}

	public boolean validEmail(String email) {
		Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
		Matcher mat = pattern.matcher(email);

		if (mat.matches()) {
			return true;
		} else {

			return false;
		}
	}

	public Person findPersonID(int id) {
		Person currentPerson = new Person();
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantbooking", "root", "");

			Statement stmt = conn.createStatement();

			String SQL = "SELECT * FROM person where personID='" + id + "'";

			ResultSet rs = stmt.executeQuery(SQL);

			if (rs.next()) {
				currentPerson.setName(rs.getString("name"));
				currentPerson.setEmail(rs.getString("email"));
				currentPerson.setRole(rs.getString("role"));
				currentPerson.setTelno(rs.getString("telno"));
				currentPerson.setUsername(rs.getString("username"));
				currentPerson.setPassword(rs.getString("password"));
				currentPerson.setEnable(rs.getBoolean("enable"));

				currentPerson.setId(id);
			}

			stmt.close();
			conn.close();
		}

		catch (Exception e) {

			System.out.println("ERROR Connection");
		}

		return currentPerson;
	}

	public void update(@RequestBody Person person) {
		Map<String, Object> params = new HashMap<String, Object>();

		// String SQL = "UPDATE restaurantbooking.person SET
		// 'name'='"+person.getName()+"','surname'='"+person.getSurname()+"','email'='"+person.getEmail()+"','telno'='"+person.getTelno()+"','username'='"+person.getUsername()+"','password'='"+person.getPassword()+"'
		// WHERE personID='" +person.getId() + "'";

		String SQL = "update person set email=:email,telno=:telno,username=:username,password=:password where personID=:personID ";

		params.put("email", person.getEmail());
		params.put("telno", person.getTelno());
		params.put("username", person.getUsername());
		params.put("password", person.getPassword());
		params.put("personID", person.getId());
		temp.update(SQL, params);

	}

}
