package cz.cvut.ear.service;

import cz.cvut.ear.dao.EmployeeRepository;
import cz.cvut.ear.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // поменять на объект Employee?
    // удадить из Project.usersInProject? удалить из Task.assignee, Task.participants?
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
}
