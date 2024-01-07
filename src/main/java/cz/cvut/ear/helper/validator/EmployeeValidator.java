package cz.cvut.ear.helper.validator;

import cz.cvut.ear.model.Employee;
import cz.cvut.ear.repository.EmployeeRepository;
import cz.cvut.ear.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidator {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;


    @Autowired
    public EmployeeValidator(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    public void validateEmployeeExists(Employee employee) {
        if (employee == null || !employeeRepository.existsById(employee.getId())) {
            throw new IllegalArgumentException("Employee with ID " + employee.getId() + " does not exist.");
        }
    }

    public void validateEmployeeExistsById(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " does not exist.");
        }
    }

    public void validateEmployeeExistsByName(String name) {
        if (!employeeRepository.existsByName(name)) {
            throw new IllegalArgumentException("Employee with name " + name + " not found");
        }
    }

    public void validateEmployeeAlreadyExist(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            throw new IllegalArgumentException("Employee with ID " + employee.getId() + " already exists");
        }
    }
    public void validateEmployeeDelete(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow();
        if (taskRepository.existsByAssignee(employee)) {
            throw new IllegalArgumentException("Cannot delete employee assigned to a task");
        }
    }
}
