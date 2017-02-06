package com.warsztat.jdbc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.warsztat.jdbc.database.DatabaseConnector;
import com.warsztat.jdbc.model.Order;

public class OrderService {

	private Connection connection = DatabaseConnector.getConnection();
	private String createTableOrder = "CREATE TABLE \"Order\" (order_id integer primary key, Customer_id integer, Action_id integer, order_date date, details varchar(200));";
	private String alterTableOrder1 = "ALTER TABLE \"Order\" ADD FOREIGN KEY (Customer_id) REFERENCES Customer (customer_id);";
	private String alterTableOrder2 = "ALTER TABLE \"Order\" ADD FOREIGN KEY (Action_id) REFERENCES Action (action_id);";
	private PreparedStatement addOrderSt;
	private PreparedStatement deleteOrderSt;
	private PreparedStatement getOrderSt;
	private Statement statement;

	public OrderService() {
		try {
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("Order".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists){
				statement.executeUpdate(createTableOrder);
				statement.executeUpdate(alterTableOrder1);
				statement.executeUpdate(alterTableOrder2);
			}
			addOrderSt = connection
					.prepareStatement("INSERT INTO \"Order\" (order_id, Customer_id, Action_id, order_date, details) VALUES (?, ?, ?, ?, ?)");
			deleteOrderSt = connection
					.prepareStatement("DELETE FROM \"Order\"");
			getOrderSt = connection
					.prepareStatement("SELECT order_id, Customer_id, Action_id, order_date, details FROM \"Order\"");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}

	public int addOrder(Order ord) {
		int count = 0;
		try {
			addOrderSt.setInt(1, ord.getOrder_id());
			addOrderSt.setInt(2, ord.getCustomer_id());
			addOrderSt.setInt(3, ord.getAction_id());
			addOrderSt.setDate(4, ord.getOrder_date());
			addOrderSt.setString(5, ord.getDetails());

			count = addOrderSt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	 public int updateOrder(Order order) {
		 	int UpO = 0;
			try {
				PreparedStatement preparedStatement = connection
						.prepareStatement("update \"Order\"" +
	                                        " set customer_id=?, action_id=?, " +
	                                        "order_date=?, details=?" +
								            "where order_id=?");

				preparedStatement.setInt(1, order.getCustomer_id());
				preparedStatement.setInt(2, order.getAction_id());
				preparedStatement.setDate(3, order.getOrder_date());
				preparedStatement.setString(4, order.getDetails());
				preparedStatement.setInt(5, order.getOrder_id());
				UpO = preparedStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return UpO;
		}
	
	void deleteOrderAll() {
		try {
			deleteOrderSt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int deleteOrder(Order order) {
		int idO = 0;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM \"Order\" WHERE order_id=?");

			preparedStatement.setInt(1, order.getOrder_id());
			idO = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idO;
	}

	public List<Order> getOrder() {
		List<Order> orders = new ArrayList<Order>();

		try {
			ResultSet rs = getOrderSt.executeQuery();

			while (rs.next()) {
				Order k = new Order();
				k.setOrder_id(rs.getInt("order_id"));
				k.setCustomer_id(rs.getInt("Customer_id"));
				k.setAction_id(rs.getInt("Action_id"));
				k.setOrder_date(rs.getDate("order_date"));
				k.setDetails(rs.getString("details"));
				orders.add(k);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	public List<Order> getOrder2() {
		List<Order> orders = new ArrayList<Order>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from \"Order\" o  " +
                    "JOIN \"Customer\" c ON o.Customer_id = c.customer_id");
			
			while (rs.next()) {
				Order order = new Order();
				order.setOrder_id(rs.getInt("order_id"));
				order.setCustomer_id(rs.getInt("customer_id"));
				order.setAction_id(rs.getInt("action_id"));
				order.setOrder_date(rs.getDate("order_date"));
				order.setDetails(rs.getString("details"));
				orders.add(order);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}
}
