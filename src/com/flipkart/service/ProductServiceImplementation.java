package com.flipkart.service;

import com.flipkart.database.DataBaseConnection;
import com.flipkart.model.Product;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class ProductServiceImplementation implements ProductService {

    private Connection connection;
    private final Map<Integer, Product> product = new HashMap<>();
    final Collection<Product> products = new ArrayList<>();

    public ProductServiceImplementation() {
        this.connection = DataBaseConnection.getConnection();
    }
    
    @Override
    public void initializeProducts() {
        final Properties properties = new Properties();  

        try (final InputStream input = getClass().getClassLoader().getResourceAsStream("products.properties")) {
            if (Objects.nonNull(input)) {
            	   properties.load(input);
                   for (String key : properties.stringPropertyNames()) {
                       if (key.startsWith("product.") && key.endsWith(".name")) {
                           final String idString = key.substring(8, key.lastIndexOf(".name"));
                           final int id = Integer.parseInt(idString);
                           final String name = properties.getProperty(key);
                           final String priceKey = "product." + id + ".price";
                           final double price = Double.parseDouble(properties.getProperty(priceKey));

                           add(new Product(id, name, price));
                       }
                   }
            }
        } catch (Exception exception) {
            exception.getMessage();
        }
    }

    @Override
    public Collection<Product> get(final String name, final int productsInPage, int pageNumber) {
    	products.clear();
    	final int offset = (pageNumber - 1) * productsInPage;
    	
        Product product;
        final String query = "SELECT id, name, price FROM product WHERE name = ? LIMIT ? OFFSET ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, productsInPage);
            preparedStatement.setInt(3, offset);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int id = resultSet.getInt("id");
                final double price = resultSet.getDouble("price");
                product = new Product(id, name, price);
                
                products.add(product);
            }
        } catch (SQLException exception) {
        	exception.getMessage();
        }

        return products;
    }

    private void add(final Product product) {
        final String query = "INSERT INTO product (id, name, price) VALUES (?, ?, ?)";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.getMessage();
        }
    }

    public Map<Integer, Product> get(int pageNumber, final int productsInPage) {
    	product.clear();
        final int offset = (pageNumber - 1) * productsInPage;
        final String query = "SELECT * FROM product LIMIT ? OFFSET ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productsInPage);
            preparedStatement.setInt(2, offset);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int id = resultSet.getInt("id");
                final String name = resultSet.getString("name");
                final double price = resultSet.getDouble("price");

                product.put(id, new Product(id, name, price));
            }
        } catch (SQLException exception) {
              exception.getMessage();
        }

        return product;
    }
    
    public Collection<Product> sort(final String name , final int productsInPage, int pageNumber, final String sortOrder) {
    	final Collection<Product> products = new ArrayList<>();
        Product product = null;
        final int offset = (pageNumber - 1) * productsInPage;
        final String query = "SELECT id, name, price FROM product WHERE name = ? ORDER BY price " + sortOrder + " LIMIT ? OFFSET ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, productsInPage);
            preparedStatement.setInt(3, offset);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int id = resultSet.getInt("id");
                final double price = resultSet.getDouble("price");
                product = new Product(id, name, price);
                products.add(product);
            }
        } catch (SQLException exception) {
            exception.getMessage();
        }

        return products;
    }
    
    public Collection<Product> sort(final int productsInPage, int pageNumber, final String sortOrder) {
    	final Collection<Product> products = new ArrayList<>();
        Product product = null;
        final int offset = (pageNumber - 1) * productsInPage;
        final String query = "SELECT id, name, price FROM product ORDER BY price " + sortOrder + " LIMIT ? OFFSET ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productsInPage);
            preparedStatement.setInt(2, offset);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	final int id = resultSet.getInt("id");
                final String name = resultSet.getString("name");
                final double price = resultSet.getDouble("price");
                product = new Product(id, name, price);
                
                products.add(product);
            }
        } catch (SQLException exception) {
            exception.getMessage();
        }

        return products;
    }   
}

