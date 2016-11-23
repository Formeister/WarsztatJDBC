package com.warsztat.jdbc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.warsztat.jdbc.database.DatabaseConnector;
import com.warsztat.jdbc.model.Action;

public class ActionService {

	private Connection connection = DatabaseConnector.getConnection();
	private String createTableAction = "CREATE TABLE \"Action\" (action_id integer primary key, name varchar(20), desc varchar(200), price double)";
	private PreparedStatement addActionSt;
	private PreparedStatement deleteActionSt;
	private PreparedStatement getActionSt;
	private Statement statement;

	public ActionService() {
		try {
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("Action".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists)
				statement.executeUpdate(createTableAction);

			addActionSt = connection
					.prepareStatement("INSERT INTO \"Action\" (action_id, name, desc, price) VALUES (?, ?, ?, ?)");
			deleteActionSt = connection
					.prepareStatement("DELETE FROM \"Action\"");
			getActionSt = connection
					.prepareStatement("SELECT action_id, name, desc, price FROM \"Action\"");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}

	public int addAction(Action act) {
		int count = 0;
		try {
			addActionSt.setInt(1, act.getAction_id());
			addActionSt.setString(2, act.getName());
			addActionSt.setString(3, act.getDesc());
			addActionSt.setDouble(4, act.getPrice());
			
			count = addActionSt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	 public int updateAction(Action action) {
		 	int UpA = 0;
			try {
				PreparedStatement preparedStatement = connection
						.prepareStatement("update \"Action\"" +
	                                        " set name=?, desc=?, price=?" +
								            "where action_id=?");

				preparedStatement.setString(1, action.getName());
				preparedStatement.setString(2, action.getDesc());
				preparedStatement.setDouble(3, action.getPrice());
				preparedStatement.setInt(4, action.getAction_id());
				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return UpA;
		}
	
	void deleteActionAll() {
		try {
			deleteActionSt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int deleteAction(Action action) {
		int idA = 0;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM \"Action\" WHERE action_id=?");

			preparedStatement.setInt(1, action.getAction_id());
			idA = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idA;
	}

	public List<Action> getAction() {
		List<Action> actions = new ArrayList<Action>();

		try {
			ResultSet rs = getActionSt.executeQuery();

			while (rs.next()) {
				Action a = new Action();
				a.setAction_id(rs.getInt("action_id"));
				a.setName(rs.getString("name"));
				a.setDesc(rs.getString("desc"));
				a.setPrice(rs.getDouble("price"));
				actions.add(a);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actions;
	}

}
