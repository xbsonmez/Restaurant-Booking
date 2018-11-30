package com.spring.dao;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.Model.Person;

public interface PersonDAO {

	public void save(@RequestBody Person person);

	public List<Person>  loadAll();

	public boolean findPerson( String username, String password);

	public boolean checkUsername(@RequestBody Person person);

	public boolean checkEmail(@RequestBody Person person);

	public boolean checkRegisterPasswords(@RequestBody Person person);

	public boolean checkName(@RequestBody Person person);

	public boolean checkSurname(@RequestBody Person person);
	
	public boolean checkEnabled(@RequestBody Person person);
	
	public Person findPersonID(int id);
	
	public void update(@RequestBody Person person);
}
