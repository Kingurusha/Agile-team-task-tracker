package cz.cvut.ear.service;

import cz.cvut.ear.dao.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpoweredEmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmpoweredEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
