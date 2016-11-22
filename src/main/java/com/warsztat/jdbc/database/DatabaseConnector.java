package com.warsztat.jdbc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
            	String url = "jdbc:hsqldb:hsql://localhost/warsztatjdbc";
            	connection = DriverManager.getConnection(url,"SA","");
             } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }

    }
}