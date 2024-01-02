package cz.cvut.ear.repository;

import cz.cvut.ear.model.Project;

import java.util.Optional;

public interface ProjectRepository extends BaseRepository<Project, Long>, ProjectRepositoryCustom {
    Optional<Project> findByProjectName(String projectName);
}
