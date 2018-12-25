<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpServletRequest" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="org.tasklist.model.Task" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Task List</title>
<link href="css/main.css" rel="stylesheet" />
</head>
<body>
	<div id="wrapper">
		<div class="container">
			<div class="app-name">
				<h2>Task List</h2>
			</div>
			<div id="new-task" >
				<h3>New Task</h3>
				<form action="task" method="post" class="task-form">
					<div class="form-group">
						<label for="task-name" class="form-label">Task</label>
						<input type="text" name="task-name" class="task-input" placeholder="Enter Your Task Here" />
					</div>
					<input type="hidden" name="method" value="insert" />
					<button type="submit" class="btn-submit">Add Task</button>
				</form>
			</div>
			<div id="task-list">
				<h3>Current Tasks</h3>
				<% 
					if(request != null && request.getAttribute("tasksList") != null) {
						List<Task> tasksList = (List<Task>)request.getAttribute("tasksList");
						Iterator<Task> taskListIterator = tasksList.iterator();
				
				%>
				<h5 class="form-label">Task</h5>
				<ul class="tasks-list">
					<% 
						while(taskListIterator.hasNext()) { 
							Task task = taskListIterator.next();
					%>
					<li class="task-item">
						<span class="task-description"><%= task.getTask() %></span>
						<span class="task-action">
							<form action="task" method="post">
								<input type="hidden" name="taskId" value="<%= task.getTaskId()%>">
								<input type="hidden" name="method" value="delete">
								<button type="submit" class="btn-delete">Delete</button>
							</form>
						</span>
					</li>
					<% } %>
				</ul>
				<% } %>
			</div>
		</div>
	</div>
</body>
</html>