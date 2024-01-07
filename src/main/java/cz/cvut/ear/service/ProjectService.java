package cz.cvut.ear.service;

import cz.cvut.ear.helper.validator.ProjectValidator;
import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.ProjectStatus;
import cz.cvut.ear.model.enums.SprintStatus;
import cz.cvut.ear.repository.EmployeeRepository;
import cz.cvut.ear.repository.ProjectRepository;
import cz.cvut.ear.repository.SprintRepository;
import cz.cvut.ear.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;

    private final ProjectValidator projectValidator;
    private static final Logger LOG = LoggerFactory.getLogger(ProjectService.class);


    @Autowired
    public ProjectService(ProjectRepository projectRepository, EmployeeRepository employeeRepository, TaskRepository taskRepository, SprintRepository sprintRepository, ProjectValidator projectValidator) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
        this.sprintRepository = sprintRepository;
        this.projectValidator = projectValidator;
    }


    //done
    @Transactional(readOnly = true)
    public List<Project> getAllProjectsInSystem() {
        return projectRepository.findAll();
    }

    // done
    @Transactional(readOnly = true)
    public Project getProjectById(Long projectId) {
        projectValidator.validateProjectExists(projectId);
        return projectRepository.findById(projectId).get();
    }

    // done
    @Transactional(readOnly = true)
    public Project getProjectByName(String projectName) {
        projectValidator.validateProjectExistByName(projectName);
        return projectRepository.findByProjectName(projectName).get();
    }

    @Transactional(readOnly = true)
    public List<Sprint> getAllSprintsInProject(Long projectId) {
        projectValidator.validateProjectExists(projectId);
        return sprintRepository.findAllByProjectId(projectId);
    }

    // done
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployeesInProject(Long projectId) {
        projectValidator.validateProjectExists(projectId);
        return employeeRepository.findEmployeesByProjectId(projectId);
    }

    @Transactional(readOnly = true)
    public List<Sprint> getAllSprintsWithStatusInProject(Long projectId, SprintStatus status) {
        projectValidator.validateProjectExists(projectId);
        return sprintRepository.findAllByProjectIdAndSprintStatus(projectId, status);
    }

    @Transactional(readOnly = true)
    public Sprint getCurrentSprintInProject(Long projectId) {
        projectValidator.validateProjectExists(projectId);
        return projectRepository.findById(projectId).get().getCurrentSprint();
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasksInSprint(Long sprintId) {
        projectValidator.validateSprintExists(sprintId);
        return taskRepository.findBySprintId(sprintId);
    }

    // done
    @Transactional(readOnly = true)
    public List<Project> getAllActiveProjectsInDate(LocalDate date) {
        return projectRepository.findActiveProjectsByDate(date);
    }

    @Transactional
    public void createProject(Project project) {
        projectValidator.validateProjectSame(project);
        projectRepository.saveAndFlush(project);
        LOG.debug("Created project {}.", project);
    }

    @Transactional
    public void updateProject(Project project) {
        projectValidator.validateProjectExists(project.getId());
        projectRepository.saveAndFlush(project);
        LOG.debug("Updated project {}.", project);
    }

    @Transactional
    public void partialProjectUpdate(Long projectId, Map<String, Object> updates) {
        projectValidator.validateProjectExists(projectId);
        Project project = projectRepository.findById(projectId).orElseThrow();

        updates.forEach((key, value) -> {
            switch (key) {
                case "projectName" -> project.setProjectName((String) value);
                case "projectStatus" -> project.setProjectStatus((ProjectStatus) value);
                case "startDate" -> project.setStartDate((LocalDate) value);
                case "endDate" -> project.setEndDate((LocalDate) value);
                case "currentSprint" -> project.setCurrentSprint((Sprint) value);
                case "sprintsInProject" -> project.setSprintsInProject((Set<Sprint>) value);
                case "usersInProject" -> project.setUsersInProject((Set<Employee>) value);
                case "description" -> project.setDescription((String) value);
                default -> LOG.warn("Unknown field: {}", key);
            }
        });

        projectRepository.saveAndFlush(project);
        LOG.debug("Updated project {}.", project);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        projectValidator.validateProjectExists(projectId);
        projectValidator.validateProjectDeletable(projectId);
        Project project = projectRepository.findById(projectId).get();
        Set<Sprint> sprintsInProject = project.getSprintsInProject();

        //delete task
        for (Sprint sprint : sprintsInProject) {
            List<Task> tasksInSprint = taskRepository.findBySprintId(sprint.getId());
            taskRepository.deleteAll(tasksInSprint);
        }

        // delete sprint
        sprintRepository.deleteAll(sprintsInProject);

        projectRepository.deleteById(projectId);
        LOG.debug("Deleted project {}.", project);
    }

    @Transactional
    public void removeEmployeeFromProject(Long projectId, Long employeeId) {
        projectValidator.validateProjectExists(projectId);
        projectValidator.validateEmployeeExists(employeeId);
        Project project = projectRepository.findById(projectId).get();
        Employee employee = employeeRepository.findById(employeeId).get();
        Set<Employee> employees = project.getUsersInProject();
        employees.remove(employee);
        project.setUsersInProject(employees);
        projectRepository.saveAndFlush(project);
        employee.getUserProjects().remove(project);
        employeeRepository.saveAndFlush(employee);
        LOG.debug("Removed employee {} from project {}.", employee, project);
    }
}