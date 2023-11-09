package cz.cvut.ear.service;

import cz.cvut.ear.model.Task;

import cz.cvut.ear.model.enums.Priority;

import cz.cvut.ear.model.Project;

import java.util.List;

public interface TaskService {
	Task addTask(Task task);
    void deleteTask(long taskId);
    Task getByName(String name);
    Task editTask(Task task);
    List<Task> getAllTasks();
    List<Task> showTasksByUsername(String username);
    Task setStoryPoints(long taskId, int storyPoints);
    List<Task> showTasksInSprint(long sprintId);
    void closeTask(long taskId);
    List<Task> showTasksByPriority(Priority priority);
    void moveToAnotherSprint(long sprintId, long taskId);
    void moveToAnotherProject(Project project, long taskId);
}