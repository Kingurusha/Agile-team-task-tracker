package cz.cvut.ear.rest;

import cz.cvut.ear.helper.RestUtils;
import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.TaskStatus;
import cz.cvut.ear.security.EmployeeDetails;
import cz.cvut.ear.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasAnyRole('ROLE_EMPOWERED', 'ROLE_REGULAR')")
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    // get current authorized employee
    // done?
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getCurrent(Authentication auth) {
        assert auth.getPrincipal() instanceof EmployeeDetails;
        return ((EmployeeDetails) auth.getPrincipal()).getEmployee();
    }

    // get employee by id
    @GetMapping(value = "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployeeById(@PathVariable Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    // get employee by username
    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployeeByUsername(@PathVariable String username) {
        return employeeService.getEmployeeByUsername(username);
    }

    // get all employees
    // done
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // get all projects belongs to concrete employee
    @GetMapping(value = "/{employeeId}/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getAllEmployeeProjects(@PathVariable Long employeeId) {
        return employeeService.getAllEmployeeProjects(employeeId);
    }

    // get all employee tasks by employee id
    @GetMapping(value = "/{employeeId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllEmployeeTasksById(@PathVariable Long employeeId) {
        return employeeService.getAllEmployeeTasks(employeeId);
    }

    // get all employee tasks by employee username
    @GetMapping(value = "/{username}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllEmployeeTasksByUsername(@PathVariable String username) {
        return employeeService.getAllEmployeeTasksByUsername(username);
    }

    // get all employee tasks by employee username and task status
    @GetMapping(value = "/{username}/tasks/{taskStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllEmployeeTasksByUsernameAndStatus(@PathVariable String username,
                                                             @PathVariable TaskStatus taskStatus) {
        return employeeService.getAllEmployeeTasksByUsernameAndStatus(username, taskStatus);
    }

    // get all employees with overdue tasks
    // done
    @GetMapping(value = "/tasks/overdue", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployeesWithOverdueTasks() {
        return employeeService.getAllEmployeesWithOverdueTasks();
    }

    // get all employees enrolled in any project between dates
    @GetMapping(value = "/enrolled-between/start-date/{startDate}/end-date/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployeesEnrolledInProjectsInDateRange(@PathVariable LocalDate startDate,
                                                                    @PathVariable LocalDate endDate) {
        return employeeService.getEmployeesEnrolledInProjectsInDateRange(startDate, endDate);
    }

    // register new employee
    @PreAuthorize("hasRole('ROLE_EMPOWERED')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerEmployee(@RequestBody Employee employee) {
        employeeService.registerEmployee(employee);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", employee.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // update the whole employee information
    @PreAuthorize("hasRole('ROLE_EMPOWERED')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
    }

    // update some parts of employee information
    @PatchMapping(value = "/{employeeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void partialEmployeeUpdate(@PathVariable Long employeeId,
                                      @RequestBody Map<String, Object> updates) {
        employeeService.partialEmployeeUpdate(employeeId, updates);
    }

    // delete the whole employee
    @PreAuthorize("hasRole('ROLE_EMPOWERED')")
    @DeleteMapping(value = "/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
