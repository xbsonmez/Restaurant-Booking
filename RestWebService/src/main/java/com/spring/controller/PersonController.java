package com.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.Model.Person;
import com.spring.Services.LoginService;
import com.spring.dao.PersonDAO;

@Controller
public class PersonController {

	@Autowired
	PersonDAO personDAO;

	@RequestMapping(value = "/user", method = { RequestMethod.GET })
	@ResponseBody
	public List<Person> personList() {

		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();

		person.setName("burak");

		persons.add(person);
		return persons;

	}

	@RequestMapping(value = "/persons", method = { RequestMethod.GET })
	@ResponseBody
	public List<Person> getPerson() {

		
		return personDAO.loadAll();
	}

	@RequestMapping(value = "/addPerson", method = { RequestMethod.POST })
	@ResponseBody
	public HttpStatus savePerson(@RequestBody Person person, ModelMap modelMap) {

		if (personDAO.checkEmail(person) == true) {
			modelMap.put("error", "email already exist");
			System.out.println("email");
		} else if (personDAO.checkUsername(person) == true) {
			modelMap.put("error", "username already exist");
			System.out.println("username");
		} else if (personDAO.checkName(person) == false) {
			modelMap.put("error", "enter a name");
			System.out.println("name");
		} else if (personDAO.checkSurname(person) == false) {
			modelMap.put("error", "enter a surname");
			System.out.println("surname");
		} else if (personDAO.checkRegisterPasswords(person) == false) {
			modelMap.put("error", "confirm password and password are not the same");
			System.out.println("password");
		} else {
			personDAO.save(person);
			return (HttpStatus.CREATED);
		}

		return HttpStatus.CONFLICT;
	}

	@RequestMapping(value = "/accessLogin", method = { RequestMethod.POST })
	@ResponseBody
	public HttpStatus Login(@RequestBody LoginService login) {

		boolean isUser = personDAO.findPerson(login.getUsername(), login.getPassword());
		// "memol12","5542157"

		if (isUser == true) {

			return HttpStatus.OK;

		}

		return HttpStatus.BAD_REQUEST;

	}

	@RequestMapping(value = "/persons/{personID}", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<Person> findPersonByID(@PathVariable("personID") int id) {

		if (personDAO.findPersonID(id) != null) {

			return new ResponseEntity<Person>(personDAO.findPersonID(id), HttpStatus.OK);
		}
		return new ResponseEntity<Person>(personDAO.findPersonID(id), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/persons/update", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<Person> updatePerson(@RequestBody Person person, ModelMap modelMap) {
		Person currentPerson = new Person();
		currentPerson = personDAO.findPersonID(person.getId());

		if (personDAO.checkEmail(person) == true && currentPerson.getEmail().equals(person.getEmail()) != false) {
			modelMap.put("error", "email already exist");
			System.out.println("email");
		} else if (personDAO.checkUsername(person) == true
				&& currentPerson.getUsername().equals(person.getUsername()) != false) {
			modelMap.put("error", "username already exist");
			System.out.println("username");
		} else if (personDAO.checkName(person) == false) {
			modelMap.put("error", "enter a name");
			System.out.println("name");
		} else if (personDAO.checkSurname(person) == false) {
			modelMap.put("error", "enter a surname");
			System.out.println("surname");

		} else {
			personDAO.update(person);

			return new ResponseEntity<Person>(person, HttpStatus.OK);
		}

		return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);

	}

}
