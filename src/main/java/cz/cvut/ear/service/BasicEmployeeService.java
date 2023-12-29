package cz.cvut.ear.service;

import cz.cvut.ear.dao.EmployeeRepository;
import cz.cvut.ear.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// TODO: delete class? or use as basic?
// old implementation
@Service
public class BasicEmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public BasicEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // crud
    public Employee addEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    public void deleteEmployee(long employeeId) {
        employeeRepository.deleteById(employeeId);
    }


    public Employee getByUsername(String name) {
        return employeeRepository.findByUsername(name).get();
    }


    public Employee editEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> showEmployeesByProjectId(Long projectId) {
        return employeeRepository.findEmployeesByProjectId(projectId);
    }

    public List<Employee> showEmployeesWithOverdueTasks() {
        return employeeRepository.findEmployeesWithOverdueTasks();
        // имя сотрудника
    }

    public List<Employee> showEmployeesInProjectsDuringDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return employeeRepository.findEmployeesInProjectsDuringDateRange(startDate, endDate);
    }
}
