package com.spring.dao;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.Model.Person;

public interface PersonDAO {

	public void save(@RequestBody Person person);

	public List<Person>  loadAll();

	public Person findPerson( String username, String password);

	public boolean checkUsername(@RequestBody Person person);

	public boolean checkEmail(String email);

	public boolean validEmail(String email);
	
	public boolean checkRegisterPasswords(@RequestBody Person person);
	public boolean checkPasswordLength(String s);

	public boolean checkName(@RequestBody Person person);

	
	
	public boolean checkEnabled(@RequestBody Person person);
	
	public Person findPersonID(int id);
	
	public void update(@RequestBody Person person);
}
