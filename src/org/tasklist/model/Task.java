package org.tasklist.model;

public class Task {
	private int taskId;
	private String task;
	private String createdAt;
	private String modifiedAt;
	
	
	public String getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(String createAt) {
		this.createdAt = createAt;
	}


	public String getModifiedAt() {
		return modifiedAt;
	}


	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}


	public int getTaskId() {
		return taskId;
	}


	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}


	public String getTask() {
		return task;
	}


	public void setTask(String task) {
		this.task = task;
	}


	public Task(int taskId, String taskDetails, String created, String modified) {
		setTaskId(taskId);
		setTask(taskDetails);
		setCreatedAt(created);
		setModifiedAt(modified);
	}

}
