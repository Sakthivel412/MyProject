package com.flipkart.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
	
	private static final String url = "jdbc:postgresql://localhost:5432/Product";
	private static final String userName = "postgres";
	private static final String password = "tiger";

	public static Connection getConnection() {
		
        try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException exception) {
			exception.getMessage();
		}
		try {
			return DriverManager.getConnection(url, userName, password);
		} catch (SQLException exception) {
			exception.getMessage();
		}
		
		return null;		
	}
}
