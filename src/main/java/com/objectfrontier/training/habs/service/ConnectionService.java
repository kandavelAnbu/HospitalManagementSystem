package com.objectfrontier.training.habs.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionService {

	public static Connection getConnection() {
		try {
			
			String connectionString;
			Class.forName("java.sql.DriverManager");
			connectionString = "jdbc:mysql://PC1583:3306/kandavela?user=kandavela&password=kandavela";
			Connection con = DriverManager.getConnection(connectionString);
			con.setAutoCommit(false);
			return con;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);  
		} 
	}
}
