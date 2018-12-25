package org.tasklist.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tasklist.dao.DBWrapper;
import org.tasklist.model.Task;

public class TaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Task> tasksList = new DBWrapper().getTasks();
			request.setAttribute("tasksList", tasksList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		switch(method) {
		case "insert":
			String taskName = request.getParameter("task-name");
			boolean isAdded = addTask(taskName);
			if(isAdded) {
				doGet(request, response);
			}
			break;
		case "delete":
			String tId = request.getParameter("taskId");
			if(tId == null) {
				return;
			}
			int taskId = Integer.parseInt(tId);
			boolean isDeleted = deleteTask(taskId);
			if(isDeleted) {
				doGet(request, response);
			}
			break;
		case "read":
			Task task = fetchTask(request);
			break;
		default:
			doGet(request, response);
		}
	}

	private boolean deleteTask(int id) {
		boolean isDeleted = false;
		try {
			isDeleted = new DBWrapper().deleteTask(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isDeleted;
	}

	private boolean addTask(String taskName) {
		boolean isAdded = false;
		String taskDetails = taskName;
		if(taskDetails != null && !taskDetails.trim().equals("")) {
			try {
				isAdded = new DBWrapper().addTask(taskDetails);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return isAdded;
	}

	protected Task fetchTask(HttpServletRequest request) throws ServletException, IOException {
		String ID = request.getParameter("taskId");
		if(ID == null) {
			return null;
		}
		
		int id = Integer.parseInt(ID);
		try {
			Task task = new DBWrapper().getTask(id);
			return task;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
