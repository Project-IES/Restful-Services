package com.yonosbi.helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	private static Properties dbProperties;

	static {
		dbProperties = new Properties();
		try {
			dbProperties.load(ConnectionManager.class.getClassLoader().getResourceAsStream("db.properties"));
			Class.forName(dbProperties.getProperty("db.driverClassname"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection con = null;
		con = DriverManager.getConnection(dbProperties.getProperty("db.url"), dbProperties.getProperty("db.username"),
				dbProperties.getProperty("db.password"));
		con.setAutoCommit(false);
		return con;
	}

}
