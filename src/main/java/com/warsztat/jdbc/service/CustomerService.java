package com.warsztat.jdbc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.warsztat.jdbc.database.DatabaseConnector;
import com.warsztat.jdbc.model.Customer;

public class CustomerService {
	
	private Connection connection = DatabaseConnector.getConnection();
	private String createTableCustomer = "CREATE TABLE \"Customer\" (customer_id integer primary key, name varchar(20), pesel bigint, address varchar(40))";
	private PreparedStatement addCustomerSt;
	private PreparedStatement deleteCustomerSt;
	private PreparedStatement getCustomerSt;
	private Statement statement;

	public CustomerService() {
		try {
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("Customer".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists)
				statement.executeUpdate(createTableCustomer);

			addCustomerSt = connection
					.prepareStatement("INSERT INTO \"Customer\" (customer_id, name, pesel, address) VALUES (?, ?, ?, ?)");
			deleteCustomerSt = connection
					.prepareStatement("DELETE FROM \"Customer\"");
			getCustomerSt = connection
					.prepareStatement("SELECT customer_id, name, pesel, address FROM \"Customer\"");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}
	
	public int addCustomer(Customer cust) {
		int count = 0;
		try {
			addCustomerSt.setInt(1, cust.getCustomer_id());
			addCustomerSt.setString(2, cust.getName());
			addCustomerSt.setLong(3, cust.getPesel());
			addCustomerSt.setString(4, cust.getAddress());

			count = addCustomerSt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	 public int updateCustomer(Customer customer) {
		 	int UpC = 0;
			try {
				PreparedStatement preparedStatement = connection
						.prepareStatement("update \"Customer\"" +
	                                        " set name=?, pesel=?, address=? " +
								            "where customer_id=?");

				preparedStatement.setString(1, customer.getName());
				preparedStatement.setLong(2, customer.getPesel());
				preparedStatement.setString(3, customer.getAddress());
				preparedStatement.setInt(4, customer.getCustomer_id());
				UpC = preparedStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return UpC;
		}

	void deleteCustomerAll() {
		try {
			deleteCustomerSt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int deleteCustomer(Customer customer) {
		int idC = 0;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM \"Customer\" WHERE customer_id=?");

			preparedStatement.setInt(1, customer.getCustomer_id());
			idC = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idC;
	}

	public List<Customer> getCustomer() {
		List<Customer> customers = new ArrayList<Customer>();

		try {
			ResultSet rs = getCustomerSt.executeQuery();

			while (rs.next()) {
				Customer c = new Customer();
				c.setCustomer_id(rs.getInt("customer_id"));
				c.setName(rs.getString("name"));
				c.setPesel(rs.getLong("pesel"));
				c.setAddress(rs.getString("address"));
				customers.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	public int countCustomer() {
		int count=0;
		List<Customer> customers = getCustomer();
		count = customers.size();
		return count;
	}

}
