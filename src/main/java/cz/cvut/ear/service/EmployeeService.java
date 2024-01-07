package cz.cvut.ear.service;

import cz.cvut.ear.helper.validator.EmployeeValidator;
import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.ProjectStatus;
import cz.cvut.ear.model.enums.Role;
import cz.cvut.ear.model.enums.TaskStatus;
import cz.cvut.ear.repository.EmployeeRepository;
import cz.cvut.ear.repository.ProjectRepository;
import cz.cvut.ear.repository.TaskRepository;
import cz.cvut.ear.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final EmployeeValidator employeeValidator;
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, TaskRepository taskRepository, ProjectRepository projectRepository, EmployeeValidator employeeValidator) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.employeeValidator = employeeValidator;
    }


    // done
    public List<Project> getAllCurrentEmployeeProjects() {
        final Employee currentEmployee = SecurityUtils.getCurrentEmployee();
        assert currentEmployee != null;
        return projectRepository.getAllEmployeeProjects(currentEmployee.getId());
    }

    // done
    public List<Project> getAllCurrentEmployeeProjectsByStatus(ProjectStatus status) {
        final Employee currentEmployee = SecurityUtils.getCurrentEmployee();
        assert currentEmployee != null;
        return projectRepository.findProjectsByEmployeeIdAndStatus(currentEmployee.getId(), status);
    }

    // done
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long employeeId) {
        employeeValidator.validateEmployeeExistsById(employeeId);
        return employeeRepository.findById(employeeId).get();
    }

    // done
    @Transactional(readOnly = true)
    public Employee getEmployeeByUsername(String username) {
        employeeValidator.validateEmployeeExistsByName(username);
        return employeeRepository.findByUsername(username).get();
    }

    // done
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // done
    @Transactional(readOnly = true)
    public List<Project> getAllEmployeeProjects(Long employeeId) {
        employeeValidator.validateEmployeeExistsById(employeeId);
        return projectRepository.getAllEmployeeProjects(employeeId);
    }

    // done
    @Transactional(readOnly = true)
    public List<Task> getAllEmployeeTasks(Long employeeId) {
        employeeValidator.validateEmployeeExistsById(employeeId);
        return taskRepository.getAllEmployeeTasks(employeeId);
    }

    // done
    @Transactional(readOnly = true)
    public List<Task> getAllEmployeeTasksByUsername(String username) {
        employeeValidator.validateEmployeeExistsByName(username);
        return taskRepository.getAllEmployeeTasksByUsername(username);
    }

    // done
    @Transactional(readOnly = true)
    public List<Task> getAllEmployeeTasksByUsernameAndStatus(String username, TaskStatus taskStatus) {
        employeeValidator.validateEmployeeExistsByName(username);
        return taskRepository.findByAssigneeUsernameAndTaskStatus(username, taskStatus);
    }

    // done
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployeesWithOverdueTasks() {
        return employeeRepository.findEmployeesWithOverdueTasks();
    }

    // done
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesEnrolledInProjectsInDateRange(LocalDate startDate, LocalDate endDate) {
        return employeeRepository.findEmployeesEnrolledInProjectsInDateRange(startDate, endDate);
    }

    @Transactional
    public void registerEmployee(Employee employee) {
        employeeValidator.validateEmployeeAlreadyExist(employee);
        employeeRepository.saveAndFlush(employee);
        LOG.debug("Created employee {}.", employee);
    }

    @Transactional
    public void updateEmployee(Employee employee) {
        employeeValidator.validateEmployeeExists(employee);
        employeeRepository.saveAndFlush(employee);
        LOG.debug("Updated employee {}.", employee);
    }

    @Transactional
    public void partialEmployeeUpdate(Long employeeId, Map<String, Object> updates) {
        employeeValidator.validateEmployeeExistsById(employeeId);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> employee.setName((String) value);
                case "surname" -> employee.setSurname((String) value);
                case "username" -> employee.setUsername((String) value);
                case "password" -> employee.setPassword((String) value);
                case "role" -> employee.setRole((Role) value);
                case "email" -> employee.setEmail((String) value);
                default -> LOG.warn("Unknown field: {}", key);
            }
        });

        employeeRepository.saveAndFlush(employee);
        LOG.debug("Updated employee {}.", employee);
    }

    @Transactional
    public void deleteEmployee(Long employeeId) {
        employeeValidator.validateEmployeeExistsById(employeeId);
        employeeValidator.validateEmployeeDelete(employeeId);
        Employee employee = employeeRepository.findById(employeeId).get();
        Set<Project> userProjects = employee.getUserProjects();
        // delete from projects
        for (Project project : userProjects) {
            project.getUsersInProject().remove(employee);
        }
        // delete from tasks and tasks from sprint
        Set<Task> userTasks = employee.getUserTasks();
        for (Task task : userTasks) {
            Sprint sprint = task.getSprint();
            if (sprint != null) {
                sprint.getTasksInSprint().remove(task);
            taskRepository.deleteById(task.getId());
        }
        }
    }
}
