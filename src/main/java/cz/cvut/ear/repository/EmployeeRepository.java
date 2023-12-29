package cz.cvut.ear.repository;

import cz.cvut.ear.model.Employee;

import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);
}
