package com.flipkart.service;

import com.flipkart.model.User;

public interface UserService {
	
	User signUp(final User user);
	
	User logIn(final String emailId, final String password);
	
	int changePassword(final String password);
	
	int changeName(final String name);
	
	int changeAddress(final String address);
	
	int ChangeMobileNumber(final String mobileNumber);
	
    User getUserDetails();	 
}

