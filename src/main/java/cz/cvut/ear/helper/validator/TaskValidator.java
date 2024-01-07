package cz.cvut.ear.helper.validator;

import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Label;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.enums.SprintStatus;
import cz.cvut.ear.model.enums.TaskStatus;
import cz.cvut.ear.repository.EmployeeRepository;
import cz.cvut.ear.repository.LabelRepository;
import cz.cvut.ear.repository.SprintRepository;
import cz.cvut.ear.repository.TaskRepository;
import cz.cvut.ear.model.Task;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class TaskValidator {
    private final TaskRepository taskRepository;

    private final SprintRepository sprintRepository;

    private final EmployeeRepository employeeRepository;

    private final LabelRepository labelRepository;
    @Autowired
    public TaskValidator(SprintRepository sprintRepository, TaskRepository taskRepository, EmployeeRepository employeeRepository, LabelRepository labelRepository) {
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.labelRepository = labelRepository;
    }

    public void validateCreate(Task task) {
        if (taskRepository.existsById(task.getId())) {
            throw new IllegalArgumentException("Task with ID " + task.getId() + " already exists.");
        }

        Sprint sprint = task.getSprint();
        if(!sprintRepository.existsById(sprint.getId())){
            throw new IllegalArgumentException("Sprint with ID " + sprint.getId() + " does not exist.");
        }
        if (sprint.getSprintStatus() == SprintStatus.CLOSED) {
            throw new IllegalArgumentException("Cannot create a task in a closed sprint.");
        }

        if (!isFibonacciNumber(task.getTaskPoints()) || task.getTaskPoints() < 1 || task.getTaskPoints() > 25) {
            throw new IllegalArgumentException("Task points must be Fibonacci numbers between 1 and 25.");
        }

        if (task.getTaskStatus() != TaskStatus.TO_DO) {
            throw new IllegalArgumentException("Task status must be set to TO_DO when creating a task.");
        }

        LocalDate dueDate = task.getDueDate();
        if (dueDate != null && dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date must be equal to or after the current date.");
        }

        Employee assignee = task.getAssignee();
        if (assignee == null) {
            throw new IllegalArgumentException("Task must have an assignee.");
        }

        Set<Employee> participants = task.getParticipants();
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("Task must have at least one participant.");
        }
    }

    private static boolean isFibonacciNumber(int number) {
        int a = 0, b = 1;
        while (b < number) {
            int temp = b;
            b += a;
            a = temp;
        }
        return b == number;
    }

    public void validateUpdate(Task task) {
        if (!taskRepository.existsById(task.getId())) {
            throw new IllegalArgumentException("Task with ID " + task.getId() + " does not exist.");
        }
    }
    public void validateTaskExists(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Task not found with ID: " + taskId);
        }
    }

    public void validateDelete(Long taskId) {
        Task taskToDelete = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));
        Sprint sprint = taskToDelete.getSprint();
        Employee assignee = taskToDelete.getAssignee();

        if (!sprintRepository.existsById(sprint.getId())){
            throw new IllegalArgumentException("Sprint with ID " + sprint.getId() + " does not exist.");
        }
        if (taskToDelete.getSprint() != null){
            throw new IllegalStateException("Cannot delete task with ID " + taskId + " because it is associated with a sprint.");
        }
        if (!employeeRepository.existsById(assignee.getId())){
            throw new IllegalArgumentException("Employee with ID " + assignee.getId() + " does not exist.");
        }
        if (taskToDelete.getAssignee() != null) {
            throw new IllegalStateException("Cannot delete task with ID " + taskId + " because it has an assignee.");
        }
    }

    public void validateParticipantDelete(Long taskId, Long employeeId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));
        if (!employeeRepository.existsById(employeeId)) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " does not exist.");
        }
        Set<Employee> participants = task.getParticipants();
        if (participants == null || participants.isEmpty() || !participants.stream().anyMatch(e -> e.getId().equals(employeeId))) {
            throw new IllegalStateException("Employee with ID " + employeeId + " is not a participant in the task with ID " + taskId + ".");
        }
    }

    public void validateAssigneeDelete(Long taskId, Long employeeId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));

        if (!employeeRepository.existsById(employeeId)) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " does not exist.");
        }

        Employee assignee = task.getAssignee();
        if (assignee == null || !assignee.getId().equals(employeeId)) {
            throw new IllegalStateException("Employee with ID " + employeeId + " is not the assignee of the task with ID " + taskId + ".");
        }
    }

    public void validateLabelDelete(Long taskId, Long labelId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));

        if (!labelRepository.existsById(labelId)) {
            throw new IllegalArgumentException("Label with ID " + labelId + " does not exist.");
        }
        Set<Label> taskLabels = task.getLabels();
        if (taskLabels == null || taskLabels.isEmpty() || taskLabels.stream().noneMatch(label -> label.getId().equals(labelId))) {
            throw new IllegalStateException("Label with ID " + labelId + " is not associated with the task with ID " + taskId + ".");
        }
    }
}
