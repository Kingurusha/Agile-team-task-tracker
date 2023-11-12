package cz.cvut.ear.dao;

import cz.cvut.ear.model.Project;

import java.util.Optional;

public interface ProjectRepository extends BaseRepository<Project, Long> {
    Optional<Project> findByProjectName(String projectName);
}
