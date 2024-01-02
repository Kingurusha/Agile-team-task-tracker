package cz.cvut.ear.repository;

import cz.cvut.ear.Generator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EmployeeRepositoryTest {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
//
//
//    @Test
//    public void findByUsernameReturnsEmployeeWithMatchingUsername() {
//        final RegularEmployee regularEmployee = Generator.generateRegularEmployee();
//        final EmpoweredEmployee empoweredEmployee = Generator.generateEmpoweredEmployee();
//
//        String regularEmployeeUsername = regularEmployee.getUsername();
//        String empoweredEmployeeUsername = empoweredEmployee.getUsername();
//
//        employeeRepository.saveAndFlush(regularEmployee);
//        employeeRepository.saveAndFlush(empoweredEmployee);
//
//        assertEquals(regularEmployee.getId(), employeeRepository.findByUsername(regularEmployeeUsername).get().getId());
//        assertEquals(empoweredEmployee.getId(), employeeRepository.findByUsername(empoweredEmployeeUsername).get().getId());
//    }
//
//    @Test
//    public void findByUsernameReturnsOptionalEmptyForUnknownUsername() {
//        final RegularEmployee regularEmployee = Generator.generateRegularEmployee();
//        final EmpoweredEmployee empoweredEmployee = Generator.generateEmpoweredEmployee();
//
//        String regularEmployeeUsername = regularEmployee.getUsername();
//        String empoweredEmployeeUsername = empoweredEmployee.getUsername();
//
//        assertEquals(Optional.empty(), employeeRepository.findByUsername(regularEmployeeUsername));
//        assertEquals(Optional.empty(), employeeRepository.findByUsername(empoweredEmployeeUsername));
//    }
}
