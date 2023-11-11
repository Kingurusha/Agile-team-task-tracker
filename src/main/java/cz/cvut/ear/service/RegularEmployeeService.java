package cz.cvut.ear.service;

import cz.cvut.ear.dao.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class RegularEmployeeService {
    private final EmployeeRepository employeeRepository;

    public RegularEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
