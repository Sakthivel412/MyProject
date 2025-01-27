package com.flipkart.service;

import com.flipkart.database.DataBaseConnection;
import com.flipkart.model.Product;
import com.flipkart.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderServiceImpl {
    private final Connection connection;
    private Integer orderId;

    public OrderServiceImpl() {
        this.connection = DataBaseConnection.getConnection();
    }

    public Map<Integer, Product> placeOrder(final Map<Integer, Product> products , final Integer cartId, final int quantity) {
	    final Map<Integer, Product> orderedProducts = new HashMap<>();
	   
        if (products.isEmpty()) {
              return null;
        }
        if (Objects.isNull(orderId)) {
        	final String query = "INSERT INTO orders (cartId, productid, quantity) VALUES (?, ?, ?) RETURNING orderId";
        	
        	try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        	    for (Integer productId : products.keySet()) {
        	        final Product product = products.get(productId);

        	        preparedStatement.setInt(1, cartId);
        	        preparedStatement.setInt(2, productId);
        	        preparedStatement.setInt(3, quantity);
        	        final ResultSet resultSet = preparedStatement.executeQuery();

        	        while (resultSet.next()) {
        	            orderId = resultSet.getInt("orderid");
        	        }
        	        orderedProducts.put(orderId, product);
        	    }
        	} catch (Exception exception) {
        	     exception.getMessage();
        	}
        }
        
        return orderedProducts;    	  
    }
    
    public Map<Integer, Product> buy(final int productId, final User user, final int quantity) {
    	final Map<Integer, Product> orderedProducts = new HashMap<>();
		final Product products = getById(productId);
		
	    if (Objects.isNull(products)) {
          return null;
        }
        if (Objects.isNull(orderId)) {
    	    final String query = "INSERT INTO orders ( productid, emailid, quantity) VALUES (?, ?, ?) RETURNING orderId";
        
            try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) { 
                preparedStatement.setInt(1, productId);
                preparedStatement.setString(2, user.getEmailId());
                preparedStatement.setInt(3, quantity);
                final ResultSet resultSet = preparedStatement.executeQuery();
                
                while (resultSet.next()) {
                    orderId = resultSet.getInt("orderid");
                }              
                orderedProducts.put(orderId, products); 
            } catch (Exception excception) {
                 excception.getMessage();  
            }
        }
        
	    return orderedProducts;
    }
    
    public Product getById(final int productId) {
        Product product;
        final String query = "SELECT name, price FROM product WHERE id = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,productId);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final String name = resultSet.getString("name");
                final double price = resultSet.getDouble("price");
                product = new Product(productId, name, price);
                
                return product;
            }
        } catch (Exception exception) {
            exception.getMessage();
        }

        return null;
    }
}

          



