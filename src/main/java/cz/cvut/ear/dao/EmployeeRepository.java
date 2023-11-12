package cz.cvut.ear.dao;

import cz.cvut.ear.model.Employee;

import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);

    // TODO in CP2: findByProjectId
}
