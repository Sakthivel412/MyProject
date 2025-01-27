package com.flipkart.controller;

import com.flipkart.model.User;
import com.flipkart.service.UserService;
import com.flipkart.service.UserServiceImpl;

public class UserController  {
	
	private final UserService userService = new UserServiceImpl();

	public User signUp(final User user) {
		return userService.signUp(user);	
	}
	
	public User logIn(final String emailId, final String password) {
		return userService.logIn(emailId, password);		
	}
	
	public int changePassword(final String password) {
		return userService.changePassword(password);		
	}

	public int changeName(final String name) {
		return userService.changeName(name);		
	}

	public int changeAddress(final String address) {
		return userService.changeAddress(address);
		
	}
	public int changeMobileNumber(final String mobileNumber) {
		return userService.ChangeMobileNumber(mobileNumber);	
	}
	
	public User getUserDetails() {
		return userService.getUserDetails();
	}
}

