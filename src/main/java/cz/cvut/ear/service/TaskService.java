package cz.cvut.ear.service;

import cz.cvut.ear.dao.ProjectRepository;
import cz.cvut.ear.dao.SprintRepository;
import cz.cvut.ear.dao.TaskRepository;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.model.enums.TaskStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, SprintRepository sprintRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.sprintRepository = sprintRepository;
        this.projectRepository = projectRepository;
    }

    // ----- OLD -------------

    public Task addTask(Task task) {
        return taskRepository.saveAndFlush(task);
    }


    public void deleteTask(long taskId) {
        taskRepository.deleteById(taskId);
    }


    public Task getByName(String name) {
        return taskRepository.findByTaskName(name).get();
    }


    public Task editTask(Task task) {
        return taskRepository.saveAndFlush(task);
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }


    public List<Task> showTasksByUsername(String username) {
        return taskRepository.findByAssigneeUsername(username);
    }


    public Task setStoryPoints(long taskId, int storyPoints) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.setTaskPoints(storyPoints);
        taskRepository.save(task);
        return task;
    }


    public List<Task> showTasksInSprint(long sprintId) {
        return taskRepository.findBySprintId(sprintId);
    }


    public void closeTask(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.setTaskStatus(TaskStatus.CLOSED);
        taskRepository.save(task);
    }


    public List<Task> showTasksByPriority(TaskPriority taskPriority) {
        return taskRepository.findByPriority(taskPriority);
    }


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

        Set<Task> tasksInSprint = newSprint.getTasksInSprint();

        tasksInSprint.add(task);

        newSprint.setTasksInSprint(tasksInSprint);
        sprintRepository.save(newSprint);
    }

    // we delete reference between task and project
    // TASK to another PROJECT???
    public void moveToAnotherProject(Project project, long taskId) {
//        Task task = taskRepository.findById(taskId)
//                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
//
//        Project currentProject = task.getProject();
//        if (currentProject != null && currentProject.equals(project)) {
//            throw new IllegalArgumentException("Task is already in the specified project");
//        }
//
//        task.setProject(project);
//        taskRepository.save(task);
//
//        List<Task> tasksInProject = new ArrayList<>(currentProject.getTasksInProject());
//        tasksInProject.add(task);
//        currentProject.setTasksInProject(new HashSet<>(tasksInProject));
//
//        projectRepository.save(project);
    }
}
