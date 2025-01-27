package com.flipkart.service;

import com.flipkart.database.DataBaseConnection;
import com.flipkart.model.Product;
import com.flipkart.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CartImplementation {
	private Connection connection;
    private Integer cartId;
	private User user;

	public CartImplementation() {
	    this.connection = DataBaseConnection.getConnection();
	}

	public boolean add(final int productId, final User user) {
		this.user =user;
		
	    try {
            if (Objects.isNull(cartId)) {
	            final String query = "INSERT INTO cart (productId, emailid) VALUES (?, ?) RETURNING cartId";   
	            final PreparedStatement preparedStatement = connection.prepareStatement(query);
	            
	            preparedStatement.setInt(1,  productId);
	            preparedStatement.setString(2, user.getEmailId());
	            final ResultSet resultSet = preparedStatement.executeQuery();
	                
	            if (resultSet.next()) {
	                cartId = resultSet.getInt("cartId");
	                            
	                return true;
	            }
            } else {
            	final String query = "INSERT INTO cart (cartId, productId, emailid) VALUES (?, ?, ?)";
	            final PreparedStatement preparedStatement = connection.prepareStatement(query);
	              
	            preparedStatement.setInt(1, cartId);
	            preparedStatement.setInt(2, productId);
	            preparedStatement.setString(3, user.getEmailId());
	            preparedStatement.executeUpdate();
	                    
	            return true;
            }
	    } catch (SQLException exception) {
	    	exception.getMessage();
	    }
	    
	    return false;
	}
	
	public Map<Integer, Product> view(final User user) {
	    final Map<Integer, Product> products = new HashMap<>();
	    final String query = "SELECT c.cartid,p.id, p.name, p.price FROM product p " +
	                             "JOIN cart c ON p.id = c.productId WHERE c.emailid= ?";
	        
	    try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, user.getEmailId());
	        final ResultSet resultSet = preparedStatement.executeQuery();
	        
	        while (resultSet.next()) {
	            final int id = resultSet.getInt("id");
	            final String name = resultSet.getString("name");
	            final double price = resultSet.getDouble("price");
	            cartId = resultSet.getInt("cartid");
	                    
	            products.put(cartId, new Product(id, name, price));
	        }
	    } catch (SQLException exception) {
	    	exception.getMessage();
	    }
	    
	    return products;
	}
	       
    public void remove() {  	
        final String query = "Delete from cart product WHERE emailid = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getEmailId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.getMessage();
        }
    }
}



