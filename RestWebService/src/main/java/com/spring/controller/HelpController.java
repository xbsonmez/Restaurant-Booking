package com.spring.controller;


import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.Services.Help;
import com.spring.Services.MessageService;
import com.spring.dao.PersonDAO;

@Controller

public class HelpController {
	
	@Autowired
	PersonDAO personDAO;
	

	    @Autowired
	    private JavaMailSender mailSenderObj;

	   

	    // This Method Is Used To Prepare The Email Message And Send It To The Client
	   
	   
	    @RequestMapping(value = "/help", method = RequestMethod.POST)
	    @ResponseBody 
	    public ResponseEntity<MessageService> sendEmailToClient(@RequestBody Help help ) {
	    	MessageService mess=new MessageService();
  	    	if(personDAO.checkEmail(help.getEmail())==true && personDAO.validEmail(help.getEmail())==true) {
	    	final String emailTo=help.getEmail();
	    	mailSenderObj.send(new MimeMessagePreparator() {
	          
	            public void prepare(MimeMessage mimeMessage) throws Exception {
	            	
	                MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	                mimeMsgHelperObj.setTo(emailTo);
	                mimeMsgHelperObj.setFrom(emailTo);
	                mimeMsgHelperObj.setText("Forgetpassword");
	                mimeMsgHelperObj.setSubject("password");

	            }
	        });
	        System.out.println("\nMessage Send Successfully.... Hurrey!\n");
	        mess.setMessage("Message Send Successfully....");
	        return new ResponseEntity<MessageService>(mess,HttpStatus.OK);
  	    	
  	    	}
  	    	
  	    	
  	    	
  	    	return new ResponseEntity<MessageService>(mess,HttpStatus.BAD_REQUEST);
  	    	
	    }

}
