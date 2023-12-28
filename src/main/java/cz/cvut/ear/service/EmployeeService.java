package cz.cvut.ear.service;

import cz.cvut.ear.dao.EmployeeRepository;
import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    // done
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    // done
    @Transactional(readOnly = true)
    public Employee getEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username).get();
    }

    // done
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Project> getAllEmployeeProjects(Long employeeId) {
        return null;
    }

    @Transactional(readOnly = true)
    public List<Task> getAllEmployeeTasksById(Long employeeId) {
        return null;
    }

    @Transactional(readOnly = true)
    public List<Task> getAllEmployeeTasksByUsername(String username) {
        return null;
    }

    @Transactional(readOnly = true)
    public List<Task> getAllEmployeeTasksByUsernameAndStatus(String username, TaskStatus taskStatus) {
        return null;
    }

    @Transactional
    public void registerEmployee(Employee employee) {
        LOG.debug("");
    }

    @Transactional
    public void updateEmployee(Employee employee) {
        LOG.debug("");
    }

    @Transactional
    public void partialEmployeeUpdate(Long employeeId, Map<String, Object> updates) {
        LOG.debug("");
    }

    @Transactional
    public void deleteEmployee(Long employeeId) {
        LOG.debug("");
    }


    // ------------------- old --------------------------
    // crud
    public Employee addEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }


    public Employee editEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    public void OLDdeleteEmployee(long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
