package com.shohag.shopping.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataClass {

	private Connection getConnection() {
		Connection con = null;
		try { con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tempDb1", "root", "root");} 
		catch (SQLException e) {e.printStackTrace();}
		return con;
	}

	public String getPassword(String user) {
		String dbPassword = null;
		String queryString = "select password from tempdb1.user_table where uName = '"+user+"'";
		Connection connection = getConnection();
		if (connection == null) return null;
		Statement statement = null;
		ResultSet result = null;
		try{
			statement = connection.createStatement();
			result = statement.executeQuery(queryString);
			try{ if (result.next() != false) dbPassword = result.getString("password") ;	}
			catch (SQLException e) { e.printStackTrace();}
			result.close(); statement.close(); connection.close();
		}
		catch (SQLException e) { e.printStackTrace();}
		return dbPassword;
	}

	public ArrayList<Item> getItemList() {
		ArrayList<Item> items = new ArrayList<>();
		String queryString = "select id, name, price from tempdb1.item";
		Connection connection = getConnection();
		if (connection == null) return null;
		Statement statement = null;
		ResultSet result = null;
		try{
			statement = connection.createStatement();
			result = statement.executeQuery(queryString);
			try{ 
				while(result.next() != false) 
					items.add(new Item(result.getInt("id"),result.getString("name"),result.getInt("price")));
			}
			catch (SQLException e) { e.printStackTrace();}
			result.close(); statement.close(); connection.close();
		}
		catch (SQLException e) { e.printStackTrace();}
		return items;
	}

	public String getEmail(String user) {
		
		String email = null;
		String queryString = "select email from tempdb1.user_table where uName = '"+user+"'";
		Connection connection = getConnection();
		if (connection == null) return null;
		Statement statement = null;
		ResultSet result = null;
		try{
			statement = connection.createStatement();
			result = statement.executeQuery(queryString);
			try{ if (result.next() != false) email = result.getString("email") ;	}
			catch (SQLException e) { e.printStackTrace();}
			result.close(); statement.close(); connection.close();
		}
		catch (SQLException e) { e.printStackTrace();}
		return email;
	}
}
