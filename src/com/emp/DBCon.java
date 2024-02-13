package com.emp;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBCon {
	private static final Properties props = new Properties();
	static {
		try {
			FileInputStream path = new FileInputStream("resources/config.properties");
			props.load(path);
		} catch (Exception e) {
			System.out.println("Error loading configuration file: " + e.getMessage());
			System.exit(1);
		}
	}

	public static Connection getDB() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = props.getProperty("db.url");
			String user = props.getProperty("db.username");
			String passowrd = props.getProperty("db.password");
			con = DriverManager.getConnection(url, user, passowrd);
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC driver not found: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error connecting to database: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
		return con;
	}
}
