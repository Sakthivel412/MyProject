package com.flipkart.model;

public class User {
	
	private final String emailId;
	private final String password;
	private final String name;
	private final String mobileNumber;
	private final String address;
	
	public User(final String emailId, final String password, final String name, final String mobileNumber, final String address) {
		this.emailId = emailId;
		this.password = password;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.address = address;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	@Override
	 public String toString() {
        return String.format(
            "User [emailId=%s, password=*****, name=%s, mobileNumber=%s, address=%s]", emailId, name, mobileNumber, address );
	}
}


