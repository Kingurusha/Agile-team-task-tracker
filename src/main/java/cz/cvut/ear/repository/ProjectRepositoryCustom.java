package cz.cvut.ear.repository;

import cz.cvut.ear.model.Project;

import java.util.List;

public interface ProjectRepositoryCustom {
    List<Project> getAllEmployeeProjects(Long employeeId);
}
