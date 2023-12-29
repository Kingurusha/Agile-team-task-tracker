package cz.cvut.ear.service;

import cz.cvut.ear.dao.ProjectRepository;
import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.enums.ProjectStatus;
import cz.cvut.ear.model.enums.SprintStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project addProject(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    public void deleteProject(long projectId) {
        Project projectToDelete = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + projectId));
        if (projectToDelete.getProjectStatus() != ProjectStatus.CLOSED) {
            throw new IllegalStateException("Cannot delete project because the project is not closed.");
        }
        Set<Sprint> sprintsInProject = projectToDelete.getSprintsInProject();
        Set<Employee> usersInProject = projectToDelete.getUsersInProject();

        if (sprintsInProject != null && sprintsInProject.stream().anyMatch(sprint -> sprint.getSprintStatus() != SprintStatus.CLOSED)) {
            throw new IllegalStateException("Cannot delete project because there are still open sprints.");
        }

        if (usersInProject != null && !usersInProject.isEmpty()) {
            throw new IllegalStateException("Cannot delete project because there are still users assigned to the project.");
        }

        projectRepository.deleteById(projectId);
    }

    public Project getByName(String name) {
        return projectRepository.findByProjectName(name).get();
    }

    public Project editProject(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> showProjectsByDateTime(LocalDateTime dateTime) {
        return projectRepository.findProjectsByDateTime(dateTime);
    }
}
