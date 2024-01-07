package cz.cvut.ear.service;

import cz.cvut.ear.helper.validator.TaskValidator;
import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Label;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.model.enums.TaskStatus;
import cz.cvut.ear.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final LabelRepository labelRepository;
    private final EmployeeRepository employeeRepository;
    private final SprintRepository sprintRepository;
    private final TaskValidator taskValidator;
    private static final Logger LOG = LoggerFactory.getLogger(TaskService.class);


    @Autowired
    public TaskService(TaskRepository taskRepository, LabelRepository labelRepository, EmployeeRepository employeeRepository, SprintRepository sprintRepository, TaskValidator taskValidator) {
        this.taskRepository = taskRepository;
        this.labelRepository = labelRepository;
        this.employeeRepository = employeeRepository;
        this.sprintRepository = sprintRepository;
        this.taskValidator = taskValidator;
    }


    // done
    @Transactional(readOnly = true)
    public Task getTaskById(Long taskId) {
        taskValidator.validateTaskExists(taskId);
        return taskRepository.findById(taskId).get();
    }

    // done
    @Transactional(readOnly = true)
    public List<Task> getAllOverdueOpenTasks() {
        return taskRepository.findOverdueOpenTasks();
    }

    // done
    @Transactional(readOnly = true)
    public List<Task> getTasksDueToDate(LocalDate date) {
        return taskRepository.findTasksDueToDate(date);
    }

    @Transactional
    public void createTask(Task task) {
        taskValidator.validateCreate(task);
        taskRepository.saveAndFlush(task);
        LOG.debug("Created task {}.", task);
    }

    @Transactional
    public void updateTask(Task task) {
        taskValidator.validateUpdate(task);
        task.setLastUpdateDate(LocalDateTime.now());
        taskRepository.saveAndFlush(task);
        LOG.debug("Updated task {}.", task);
    }

    @Transactional
    public void partialTaskUpdate(Long taskId, Map<String, Object> updates) {
        taskValidator.validateTaskExists(taskId);
        Task task = taskRepository.findById(taskId).orElseThrow();
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            switch (fieldName) {
                case "taskName" -> task.setTaskName((String) value);
                case "taskPoints" -> task.setTaskPoints((Integer) value);
                case "taskStatus" -> task.setTaskStatus((TaskStatus) value);
                case "taskPriority" -> task.setTaskPriority((TaskPriority) value);
                default -> throw new IllegalArgumentException("Unknown field: " + fieldName);
            }
        }

        task.setLastUpdateDate(LocalDateTime.now());

        taskRepository.saveAndFlush(task);
        LOG.debug("Updated task with ID {}.", taskId);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        taskValidator.validateDelete(taskId);
        Task task = taskRepository.findById(taskId).get();
        Sprint sprint = task.getSprint();
        Employee assignee = task.getAssignee();
        // delete from sprint
        Sprint existingSprint = sprintRepository.findById(sprint.getId()).orElseThrow();
        existingSprint.getTasksInSprint().remove(task);
        sprintRepository.save(existingSprint);
        // delete from employee
        Employee existingAssignee = employeeRepository.findById(assignee.getId()).orElseThrow();
        existingAssignee.getUserTasks().remove(task);
        employeeRepository.save(existingAssignee);
        taskRepository.deleteById(taskId);
        LOG.debug("Deleted task {}.", task);
    }

    @Transactional
    public void deleteEmployeeFromParticipants(Long taskId, Long employeeId) {
        taskValidator.validateParticipantDelete(taskId, employeeId);
        Task task = taskRepository.findById(taskId).orElseThrow();
        Set<Employee> participants = task.getParticipants();
        participants.removeIf(e -> e.getId().equals(employeeId));
        task.setParticipants(participants);
        taskRepository.saveAndFlush(task);
        LOG.debug("Removed employee with ID {} from participants in task with ID {}.", employeeId, taskId);
    }

    @Transactional
    public void deleteEmployeeFromAssignee(Long taskId, Long employeeId) {
        taskValidator.validateAssigneeDelete(taskId, employeeId);
        Task task = taskRepository.findById(taskId).orElseThrow();
        task.setAssignee(null);
        taskRepository.saveAndFlush(task);
        LOG.debug("Removed assignee from task with ID {}.", taskId);
    }

    @Transactional
    public void deleteLabelFromTask(Long taskId, Long labelId) {
        taskValidator.validateLabelDelete(taskId, labelId);
        Task task = taskRepository.findById(taskId).orElseThrow();
        Label label = labelRepository.findById(labelId).orElseThrow();

        task.getLabels().remove(label);
        taskRepository.saveAndFlush(task);

        LOG.debug("Removed label with ID {} from task with ID {}.", labelId, taskId);
    }
}