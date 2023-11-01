package cz.cvut.ear.service.impl;

import cz.cvut.ear.DAO.TaskRepository;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

	private final TaskRepository taskRepository;

	@Autowired
	public TaskServiceImpl(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	@Override
	public Task addTask(Task task) {

		return taskRepository.saveAndFlush(task);
	}

	@Override
	public void deleteTask(long taskId) {
		taskRepository.deleteById(taskId);
	}

	@Override
	public Task getByName(String name) {
		return taskRepository.findByName(name);
	}

	@Override
	public Task editTask(Task task) {
		return taskRepository.saveAndFlush(task);
	}

	@Override
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}
}