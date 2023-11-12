package cz.cvut.ear.dao;

import cz.cvut.ear.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends BaseRepository<Project, Long> {
    Optional<Project> findByProjectName(String projectName);

    List<Task> findAllTasksInProject(long projectId);

    List<Task> findNonClosedTasksInProject(long projectId);
}
