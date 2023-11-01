package cz.cvut.ear.service;

import cz.cvut.ear.model.Task;

import java.util.List;

public interface TaskService {
	Task addTask(Task task);
    void deleteTask(long taskId);
    Task getByName(String name);
    Task editTask(Task task);
    List<Task> getAllTasks();
}
