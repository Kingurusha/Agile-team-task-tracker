package cz.cvut.ear.service;

import cz.cvut.ear.repository.EmployeeRepository;
import cz.cvut.ear.repository.ProjectRepository;
import cz.cvut.ear.repository.SprintRepository;
import cz.cvut.ear.repository.TaskRepository;
import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.model.enums.TaskStatus;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private static final Logger LOG = LoggerFactory.getLogger(TaskService.class);


    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // done
    @Transactional(readOnly = true)
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).get();
    }

    @Transactional
    public void createTask(Task task) {
        // already implemented in old? - addTask
        LOG.debug("");
    }

    @Transactional
    public void updateTask(Task task) {
        // already implemented in old?
        LOG.debug("");
    }

    @Transactional
    public void partialTaskUpdate(Long taskId, Map<String, Object> updates) {
        LOG.debug("");
    }

    @Transactional
    public void deleteTask(Long taskId) {
        // already implemented in old?
        LOG.debug("");
    }

    @Transactional
    public void deleteEmployeeFromParticipants(Long taskId, Long employeeId) {
        LOG.debug("");
    }

    @Transactional
    public void deleteEmployeeFromAssignee(Long taskId, Long employeeId) {
        LOG.debug("");
    }

    @Transactional
    public void deleteLabelFromTask(Long taskId, Long labelId) {
        LOG.debug("");
    }



    // ------------------- old --------------------------


    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    public Task addTask(Task task) {
        Sprint sprint = task.getSprint();
        Employee assignee = task.getAssignee();

        if (sprint != null) {
            Sprint existingSprint = sprintRepository.findById(sprint.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Sprint not found with ID: " + sprint.getId()));
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


    public void OLD_deleteTask(long taskId) {
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



    public Task editTask(Task task) {
        return taskRepository.saveAndFlush(task);
    }

    public List<Task> showTasksByUsername(String username) {
        return taskRepository.findByAssigneeUsername(username);
    }

    // partial update
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

    // partial update
    public void closeTask(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.setTaskStatus(TaskStatus.CLOSED);
        taskRepository.save(task);
    }

    // will be implemented in project rest /projects/id/sprint/id/tasks/{priority}
//    public List<Task> showTasksByPriority(TaskPriority taskPriority) {
//        return taskRepository.findByTaskPriority(taskPriority);
//    }

    // partial update
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
}
