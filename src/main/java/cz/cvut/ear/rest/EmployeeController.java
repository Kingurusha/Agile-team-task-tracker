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

import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasAnyRole('EMPOWERED_EMPLOYEE', 'REGULAR_EMPLOYEE')")
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
        // todo: validation
        return employeeService.getEmployeeById(employeeId);
    }

    // get employee by username
    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployeeByUsername(@PathVariable String username) {
        // todo: validation
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
        // todo: validation
        return employeeService.getAllEmployeeProjects(employeeId);
    }

    // get all employee tasks by employee id
    @GetMapping(value = "/{employeeId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllEmployeeTasksById(@PathVariable Long employeeId) {
        // todo: validation
        return employeeService.getAllEmployeeTasks(employeeId);
    }

    // get all employee tasks by employee username
    @GetMapping(value = "/{username}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllEmployeeTasksByUsername(@PathVariable String username) {
        // todo: validation
        return employeeService.getAllEmployeeTasksByUsername(username);
    }

    // get all employee tasks by employee username and task status
    @GetMapping(value = "/{username}/tasks/{taskStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllEmployeeTasksByUsernameAndStatus(@PathVariable String username,
                                                             @PathVariable TaskStatus taskStatus) {
        // todo: validation
        return employeeService.getAllEmployeeTasksByUsernameAndStatus(username, taskStatus);
    }

    // register new employee
    @PreAuthorize("hasRole('EMPOWERED_EMPLOYEE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerEmployee(@RequestBody Employee employee) {
        // todo: validation
        employeeService.registerEmployee(employee);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", employee.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // update the whole employee information
    @PreAuthorize("hasRole('EMPOWERED_EMPLOYEE')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmployee(@RequestBody Employee employee) {
        // todo: validation
        employeeService.updateEmployee(employee);
    }

    // update some parts of employee information
    @PatchMapping(value = "/{employeeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void partialEmployeeUpdate(@PathVariable Long employeeId,
                                      @RequestBody Map<String, Object> updates) {
        // todo: validation
        employeeService.partialEmployeeUpdate(employeeId, updates);
    }

    // delete the whole employee
    @PreAuthorize("hasRole('EMPOWERED_EMPLOYEE')")
    @DeleteMapping(value = "/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long employeeId) {
        // todo: validation
        employeeService.deleteEmployee(employeeId);
    }
}
