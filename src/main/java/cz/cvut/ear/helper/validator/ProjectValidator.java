package cz.cvut.ear.helper.validator;

import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.enums.ProjectStatus;
import cz.cvut.ear.model.enums.SprintStatus;
import cz.cvut.ear.repository.EmployeeRepository;
import cz.cvut.ear.repository.ProjectRepository;
import cz.cvut.ear.repository.SprintRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class ProjectValidator {
    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ProjectValidator(ProjectRepository projectRepository, SprintRepository sprintRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.sprintRepository = sprintRepository;
        this.employeeRepository = employeeRepository;
    }

    public void validateProjectExists(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }
    }
    public void validateProjectExistByName(String projectName) {
        if (!projectRepository.existsByProjectName(projectName)) {
            throw new IllegalArgumentException("Project with name " + projectName + " does not exist.");
        }
    }

    public void validateSprintExists(Long sprintId) {
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if (sprint.isEmpty()) {
            throw new EntityNotFoundException("Sprint not found with ID: " + sprintId);
        }
    }

    public void validateProjectSame(Project project) {
        if (projectRepository.existsByProjectName(project.getProjectName())) {
            throw new IllegalArgumentException("Project with name " + project.getProjectName() + " already exists.");
        }
    }

    public void validateProjectDeletable(Long projectId) {
        Project projectToDelete = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + projectId));

        if (projectToDelete.getProjectStatus() != ProjectStatus.CLOSED) {
            throw new IllegalStateException("Cannot delete project because the project is not closed.");
        }

        Set<Sprint> sprintsInProject = projectToDelete.getSprintsInProject();
        Set<Employee> usersInProject = projectToDelete.getUsersInProject();

        if (sprintsInProject.stream().anyMatch(sprint -> sprint.getSprintStatus() != SprintStatus.CLOSED)) {
            throw new IllegalStateException("Cannot delete project because there are still open sprints.");
        }

        if (usersInProject != null && !usersInProject.isEmpty()) {
            throw new IllegalStateException("Cannot delete project because there are still users assigned to the project.");
        }
    }
    public void validateEmployeeExists(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with ID: " + employeeId);
        }
    }
}
