
package com.flipkart.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.flipkart.database.DataBaseConnection;
import com.flipkart.model.User;

public class UserServiceImpl implements UserService {
	
	private Connection connection;
	private User user;

	public UserServiceImpl() {
		this.connection = DataBaseConnection.getConnection();
	}

	@Override
	public User signUp(final User user) {
		final String query = "Insert into users(emailid, password, name, address, mobilenumber) values (?, ?, ?, ?, ?)";
		
	    try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getEmailId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getMobileNumber());
            final int rowsUpdated = preparedStatement.executeUpdate();
            
            if (rowsUpdated > 0) {
            	return user;
            }   
	    } catch (Exception exception) {
	    	exception.getMessage();
		  }
		  
      return null;          	
	}

	@Override
	public User logIn(final String emailId, final String password) {
		final String query = "Select * from users where emailid = ? and password = ?";
		
	    try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, emailId);
            preparedStatement.setString(2, password);
            final ResultSet resultSet = preparedStatement.executeQuery();
              
            while (resultSet.next()) {
                final String name = resultSet.getString("name");
                final String address = resultSet.getString("address");
                final String mobileNumber = resultSet.getString("mobilenumber"); 
                user = new User(emailId, password, name, mobileNumber, address);
                  
                return user; 
           }   				
		} catch (Exception exception) {
			exception.getMessage();
		  }
		  
	  return null;	
	}

	@Override
	public int changeName(final String name) {
		final String query = "Update users set name = ? where emailid = ?";
		
	    try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, user.getEmailId());
            final int rowsUpdated = preparedStatement.executeUpdate();
              
            return rowsUpdated;          				
		} catch (Exception exception) {
			exception.getMessage();
		  }
	    
		return 0;	
	}

	@Override
	public int changeAddress(final String address) {
		final String query = "Update users set address = ? where emailid = ?";
		
	    try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, address);
            preparedStatement.setString(2, user.getEmailId());
            final int rowsUpdated = preparedStatement.executeUpdate();
            
            return rowsUpdated;           				
    	} catch (Exception exception) {
    		exception.getMessage();
		  }
	    
	   return 0;		
	}

	@Override
	public int ChangeMobileNumber(final String mobileNumber) {
		final String query = "Update users set mobilenumber = ? where emailid = ?";
		
		try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mobileNumber);
            preparedStatement.setString(2, user.getEmailId());
            final int rowsUpdated = preparedStatement.executeUpdate();
          
            return rowsUpdated;           				
		} catch (Exception exception) {
			exception.getMessage();
		  }
		  
		return 0;	
	}

	@Override
	public int changePassword(final String password) {
		final String query = "Update users set password = ? where emailid = ?";
		
	    try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, user.getEmailId());
            final int rowsUpdated = preparedStatement.executeUpdate();
        
            return rowsUpdated;            				
	    } catch (Exception exception) {
		    exception.getMessage();
		  }
	    
		return 0;
	    
	}
	
	public User getUserDetails() {
        final String query = "Select * from users where emailid = ?";
        final String emailId = user.getEmailId();
		
	    try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, emailId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            	final String name = resultSet.getString("name");
            	final String password = resultSet.getString("password");
            	final String mobileNumber = resultSet.getString("mobilenumber");
            	final String address = resultSet.getString("address");
            	final User user = new User(emailId, password, name, mobileNumber, address);
            	
            	return user;
            }     				
	    } catch (Exception exception) {
	    	exception.getMessage();
		  }
	    
		return null;
	}		
}
