package cz.cvut.ear.service;

import cz.cvut.ear.model.Task;
import cz.cvut.ear.repository.ProjectRepository;
import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.enums.ProjectStatus;
import cz.cvut.ear.model.enums.SprintStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.PushBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private static final Logger LOG = LoggerFactory.getLogger(ProjectService.class);


    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    //done
    @Transactional(readOnly = true)
    public List<Project> getAllProjectsInSystem() {
        return projectRepository.findAll();
    }

    // done
    @Transactional(readOnly = true)
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).get();
    }

    // done
    @Transactional(readOnly = true)
    public Project getProjectByName(String projectName) {
        return projectRepository.findByProjectName(projectName).get();
    }

    @Transactional(readOnly = true)
    public List<Sprint> getAllSprintsInProject(Long projectId) {
        return null;
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployeesInProject(Long projectId) {
        return null;
    }

    @Transactional(readOnly = true)
    public List<Sprint> getAllSprintsWithStatusInProject(Long projectId, SprintStatus status) {
        return null;
    }

    @Transactional(readOnly = true)
    public Sprint getCurrentSprintInProject(Long projectId) {
        return null;
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasksInSprint(Long projectId, Long sprintId) {
        return null;
    }

    @Transactional
    public void createProject(Project project) {
        LOG.debug("");
    }

    @Transactional
    public void updateProject(Project project) {
        LOG.debug("");
    }

    @Transactional
    public void partialProjectUpdate(Long projectId, Map<String, Object> updates) {
        LOG.debug("");
    }

    @Transactional
    public void deleteProject(Long projectId) {
        LOG.debug("");
    }

    @Transactional
    public void removeEmployeeFromProject(Long projectId, Long employeeId) {
        LOG.debug("");
    }



    // ------------------- old --------------------------

    public void OLD_deleteProject(long projectId) {
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
}
