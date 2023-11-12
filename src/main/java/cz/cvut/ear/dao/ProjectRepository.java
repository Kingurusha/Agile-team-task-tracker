package cz.cvut.ear.dao;

import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectName(String projectName);

    List<Task> findAllTasksInProject(long projectId);

    List<Task> findNonClosedTasksInProject(long projectId);
}
