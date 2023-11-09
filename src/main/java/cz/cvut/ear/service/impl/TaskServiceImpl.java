package cz.cvut.ear.service.impl;

import cz.cvut.ear.DAO.ProjectRepository;
import cz.cvut.ear.DAO.TaskRepository;
import cz.cvut.ear.DAO.SprintRepository;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.Priority;
import cz.cvut.ear.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cz.cvut.ear.model.enums.TaskStatus;
import cz.cvut.ear.model.Sprint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

	private final TaskRepository taskRepository;
	private final SprintRepository sprintRepository;
	private final ProjectRepository projectRepository;

	@Autowired
	public TaskServiceImpl(TaskRepository taskRepository, SprintRepository sprintRepository, ProjectRepository projectRepository) {
		this.taskRepository = taskRepository;
		this.sprintRepository = sprintRepository;
		this.projectRepository = projectRepository;
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

	@Override
	public List<Task> showTasksByUsername(String username) {
		return taskRepository.findByAssigneeUsername(username);
	}

	@Override
	public Task setStoryPoints(long taskId, int storyPoints) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new EntityNotFoundException("Task not found"));
		task.setTaskPoints(storyPoints);
		taskRepository.save(task);
		return task;
	}

	@Override
	public List<Task> showTasksInSprint(long sprintId) {
		return taskRepository.findBySprintId(sprintId);
	}

	@Override
	public void closeTask(long taskId) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new EntityNotFoundException("Task not found"));
		task.setTaskStatus(TaskStatus.DONE);
		taskRepository.save(task);
	}

	@Override
	public List<Task> showTasksByPriority(Priority priority) {
		return taskRepository.findByPriority(priority);
	}

	@Override
	public void moveToAnotherSprint(long sprintId, long taskId) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new EntityNotFoundException("Task not found"));
		Sprint newSprint = sprintRepository.findById(sprintId)
				.orElseThrow(() -> new EntityNotFoundException("Sprint not found"));

		if (task.getSprint() != null && task.getSprint().equals(newSprint)) {
			throw new IllegalArgumentException("Task is already in the specified sprint");
		}

		task.setSprint(newSprint);
		taskRepository.save(task);

		List<Task> tasksInSprint = newSprint.getTasksInSprint();

		tasksInSprint.add(task);

		newSprint.setTasksInSprint(tasksInSprint);
		sprintRepository.save(newSprint);
	}

	@Override
	public void moveToAnotherProject(Project project, long taskId) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new EntityNotFoundException("Task not found"));

		Project currentProject = task.getProject();
		if (currentProject != null && currentProject.equals(project)) {
			throw new IllegalArgumentException("Task is already in the specified project");
		}

		task.setProject(project);
		taskRepository.save(task);

		List<Task> tasksInProject = new ArrayList<>(currentProject.getTasksInProject());
		tasksInProject.add(task);
		currentProject.setTasksInProject(new HashSet<>(tasksInProject));

		projectRepository.save(project);
	}

}