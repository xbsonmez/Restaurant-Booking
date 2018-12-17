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
import com.spring.Services.MessageService;
import com.spring.dao.PersonDAO;

@Controller
public class PersonController {

	@Autowired
	PersonDAO personDAO;


	@RequestMapping(value = "/persons", method = { RequestMethod.GET })
	@ResponseBody
	public List<Person> getPerson() {

		return personDAO.loadAll();
	}

	@RequestMapping(value = "/addPerson", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<MessageService> savePerson(@RequestBody Person person, ModelMap modelMap) {
		 MessageService message=new MessageService();
		 
		 
		if (personDAO.checkEmail(person.getEmail()) == true) {	
			modelMap.put("error", "email already exist");
			message.setMessage("email already exist"); 
			System.out.println("email");
			return new ResponseEntity<MessageService>(message,HttpStatus.BAD_REQUEST);
		}else if(personDAO.validEmail(person.getEmail())==false) {
			message.setMessage("invalid email format");
			return new ResponseEntity<MessageService>(message,HttpStatus.BAD_REQUEST);
		} else if (personDAO.checkUsername(person) == true && person.getUsername().length()<3) {
			modelMap.put("error", "username already exist or invalid username");
			message.setMessage("username already exist or invalid username");
			System.out.println("username");
			return new ResponseEntity<MessageService>(message,HttpStatus.BAD_REQUEST);
		} else if (personDAO.checkName(person) == false) {
			modelMap.put("error", "enter a name");
			message.setMessage("invalid name");
			System.out.println("name");
			return new ResponseEntity<MessageService>(message,HttpStatus.BAD_REQUEST);
		} else if (personDAO.checkRegisterPasswords(person) == false) {
			modelMap.put("error", " confirm password and password are not the same");
			message.setMessage("confirm password and password are not the same");
			System.out.println("password");
			
		}else if(personDAO.checkPasswordLength(person.getPassword())==false) {
			 message.setMessage("length of password must be more than 5 ");
			 return new ResponseEntity<MessageService>(message,HttpStatus.BAD_REQUEST);
		}
		
			personDAO.save(person);
			message.setMessage("successful!!!!");
			return new ResponseEntity<MessageService>(message,HttpStatus.CREATED);
		
		
	

	}

	@RequestMapping(value = "/accessLogin", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<Person> Login(@RequestBody LoginService login) {

		Person personLogin = personDAO.findPerson(login.getUsername(), login.getPassword());
		// "memol12","5542157"

		if (personLogin.getId()!=0) {

			 return new ResponseEntity<Person>(personLogin,HttpStatus.OK);
			
		}

		 return new ResponseEntity<Person>(personLogin,HttpStatus.BAD_REQUEST);

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
		
		if(personDAO.checkUsername(person)==true&& currentPerson.getUsername().equals(person.getUsername())!=true) {
			modelMap.put("error", "username already exsist");
			System.out.println("username");
			return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
		}else
		if (personDAO.checkEmail(person.getEmail()) == true && currentPerson.getEmail().equals(person.getEmail()) != true) {
			modelMap.put("error", "email already exist");
			System.out.println(currentPerson.getEmail());
			System.out.println(person.getEmail());
			System.out.println("deneme email");
			return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
		} else if (personDAO.checkRegisterPasswords(person) == false) {
			modelMap.put("error", " confirm password and password are not the same");
			System.out.println("password");
			return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
		}
		else {
			personDAO.update(person);

			return new ResponseEntity<Person>(person, HttpStatus.OK);
		}

		

	}

}
