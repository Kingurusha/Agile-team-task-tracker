package cz.cvut.ear.service;

import cz.cvut.ear.dao.EmployeeRepository;
import cz.cvut.ear.dao.ProjectRepository;
import cz.cvut.ear.dao.SprintRepository;
import cz.cvut.ear.dao.TaskRepository;
import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.model.enums.TaskStatus;
import cz.cvut.ear.model.enums.SprintStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, SprintRepository sprintRepository, ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.sprintRepository = sprintRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    public Task addTask(Task task) {
        Sprint sprint = task.getSprint();
        Employee assignee = task.getAssignee();

        if (sprint != null) {
            Sprint existingSprint = sprintRepository.findById(sprint.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Sprint not found with ID: " + sprint.getId()));

            if (existingSprint.getSprintStatus() == SprintStatus.CLOSED) {
                throw new IllegalStateException("Cannot add task to a closed sprint.");
            }

            existingSprint.getTasksInSprint().add(task);
            sprintRepository.save(existingSprint);
        }

        if (assignee != null) {
            Employee existingAssignee = employeeRepository.findById(assignee.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + assignee.getId()));
            existingAssignee.getUserTasks().add(task);
            employeeRepository.save(existingAssignee);
        }

        return taskRepository.save(task);
    }


    public void deleteTask(long taskId) {
        Task taskToDelete = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));
        // Additional check to ensure the task is closed before deletion
        if (taskToDelete.getTaskStatus() != TaskStatus.CLOSED) {
            throw new IllegalStateException("Task cannot be deleted because it is not closed.");
        }
        Sprint sprint = taskToDelete.getSprint();
        Employee assignee = taskToDelete.getAssignee();

        if (sprint != null) {
            Sprint existingSprint = sprintRepository.findById(sprint.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Sprint not found with ID: " + sprint.getId()));
            existingSprint.getTasksInSprint().remove(taskToDelete);
            sprintRepository.save(existingSprint);
        }

        if (assignee != null) {
            Employee existingAssignee = employeeRepository.findById(assignee.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + assignee.getId()));
            existingAssignee.getUserTasks().remove(taskToDelete);
            employeeRepository.save(existingAssignee);
        }

        taskRepository.deleteById(taskId);
    }


    public Task getByName(String name) {
        return taskRepository.findByTaskName(name).get();
    }


    public Task editTask(Task task) {
        return taskRepository.saveAndFlush(task);
    }

    public List<Task> showTasksByUsername(String username) {
        return taskRepository.findByAssigneeUsername(username);
    }


    public Task setStoryPoints(long taskId, int storyPoints) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (storyPoints < 0) {
            throw new IllegalArgumentException("Story points must not be negative.");
        }

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
        return taskRepository.findByTaskPriority(taskPriority);
    }

    public List<Task> showByAssigneeUsernameAndTaskStatus(String username, TaskStatus taskStatus) {
        return taskRepository.findByAssigneeUsernameAndTaskStatus(username, taskStatus);
    }


    public void moveToAnotherSprint(long sprintId, long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        Sprint newSprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new EntityNotFoundException("Sprint not found"));

        if (newSprint.getSprintStatus() == SprintStatus.CLOSED) {
            throw new IllegalStateException("Cannot move task to a closed sprint.");
        }

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
    public List<Task> showForAssigneeOpenTasks(Employee assignee){
        return taskRepository.findByOpenTasksForAssignee(assignee);
    }

    public List<Task> showOverdueOpenTasks(){
        return taskRepository.findOverdueOpenTasks();
    }

    public List<Task> showTasksDueInNextNDays(int numberOfDays){
        LocalDateTime endDate = LocalDateTime.now().plusDays(numberOfDays);
        return taskRepository.findTasksDueInNextNDays(endDate);
    }
}
