package org.tasklist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.tasklist.model.Task;

public class DBWrapper {
	private static Connection con = null;
	private static String driver = "com.mysql.jdbc.Driver";
	private static String dbUrl = "jdbc:mysql://localhost:3306/";
	private static String dbName = "TaskList";
	private static String userName = "root";
	private static String password = "root";
	
	static {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(dbUrl+dbName,userName,password);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Task getTask(int id) throws Exception {
		
		PreparedStatement ps = con.prepareStatement("SELECT * FROM Task where TASK_ID = ?");
		ps.setInt(1, id);
		
		Task taskObj = null;
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			taskObj = new Task(id, rs.getString("TASK_NAME"), rs.getString("CREATED_DATE"), rs.getString("MODIFIED_DATE"));
		}
		
		rs.close();
		ps.close();
		return taskObj;
	}
	
	public List<Task> getTasks() throws Exception {
		List<Task> taskList = new ArrayList<Task>();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM Task");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Task task = new Task(rs.getInt("TASK_ID"), rs.getString("TASK_NAME"), rs.getString("CREATED_DATE"), rs.getString("MODIFIED_DATE"));
			taskList.add(task);
		}
		
		rs.close();
		ps.close();
		return taskList;
	}
	
	public boolean addTask(String taskName) throws Exception {
		boolean isAdded = false;
		PreparedStatement ps = con.prepareStatement("INSERT INTO Task (TASK_NAME) VALUES (?)");
		ps.setString(1, taskName);
		int count = ps.executeUpdate();
		isAdded = count > 0 ? true: false;
		
		ps.close();
		return isAdded;
	}
	
	public boolean deleteTask(int id) throws Exception {
		boolean isDeleted = false;
		PreparedStatement ps = con.prepareStatement("DELETE FROM Task WHERE TASK_ID = ?");
		ps.setInt(1, id);
		int count = ps.executeUpdate();
		isDeleted = count > 0 ? true: false;

		ps.close();
		return isDeleted;
	}
}
