package cz.cvut.ear.repository;

import cz.cvut.ear.model.Task;

import java.util.List;

public interface TaskRepositoryCustom {
    List<Task> getAllEmployeeTasks(Long employeeId);
    List<Task> getAllEmployeeTasksByUsername(String username);
}
