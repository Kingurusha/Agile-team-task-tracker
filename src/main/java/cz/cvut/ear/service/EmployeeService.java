package cz.cvut.ear.service;

import cz.cvut.ear.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    void deleteEmployee(long employeeId);
    Employee getByUsername(String name);
    Employee editEmployee(Employee employee);
    List<Employee> getAllEmployees();
}