package cz.cvut.ear.rest;

import cz.cvut.ear.helper.RestUtils;
import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.ProjectStatus;
import cz.cvut.ear.model.enums.SprintStatus;
import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.service.EmployeeService;
import cz.cvut.ear.service.ProjectService;
import cz.cvut.ear.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasAnyRole('EMPOWERED_EMPLOYEE', 'REGULAR_EMPLOYEE')")
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final SprintService sprintService;
    private final EmployeeService employeeService;


    @Autowired
    public ProjectController(ProjectService projectService, SprintService sprintService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.sprintService = sprintService;
        this.employeeService = employeeService;
    }


    // get all projects in system
    // done
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getAllProjectsInSystem() {
        return projectService.getAllProjectsInSystem();
    }

    // get all projects for authorized employee
    // done
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getAllCurrentEmployeeProjects() {
        return employeeService.getAllCurrentEmployeeProjects();
    }

    // get all projects with concrete status for authorized employee
    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getAllCurrentEmployeeProjectsByStatus(@PathVariable ProjectStatus status) {
        // todo: validation
        return employeeService.getAllCurrentEmployeeProjectsByStatus(status);
    }

    // get project with concrete id
    @GetMapping(value = "/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Project getProjectById(@PathVariable Long projectId) {
        // todo: validation
        return projectService.getProjectById(projectId);
    }

    // get project with concrete name
    @GetMapping(value = "/name/{projectName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Project getProjectByName(@PathVariable String projectName) {
        // todo: validation
        return projectService.getProjectByName(projectName);
    }

    // get all sprints in project
    @GetMapping(value = "/{projectId}/sprints", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sprint> getAllSprintsInProject(@PathVariable Long projectId) {
        // todo: validation
        return projectService.getAllSprintsInProject(projectId);
    }

    // get all employees in project
    @GetMapping(value = "/{projectId}/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployeesInProject(@PathVariable Long projectId) {
        // todo: validation
        return projectService.getAllEmployeesInProject(projectId);
    }

    // get all sprints with some status in project
    @GetMapping(value = "/{projectId}/sprints/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sprint> getAllSprintsWithStatusInProject(@PathVariable Long projectId,
                                                         @PathVariable SprintStatus status) {
        // todo: validation
        return projectService.getAllSprintsWithStatusInProject(projectId, status);
    }

    // get concrete sprint by its ordinal number in project (primaryKey)
    @GetMapping(value = "/{projectId}/sprints/{sprintOrdinalNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sprint getSprintByOrdinalNumberInProject(@PathVariable Long projectId,
                                                    @PathVariable Integer sprintOrdinalNumber) {
        // todo: validation
        return sprintService.getSprintByOrdinalNumberInProject(projectId, sprintOrdinalNumber);
    }

    // get current sprint in project
    @GetMapping(value = "/{projectId}/sprints/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sprint getCurrentSprintInProject(@PathVariable Long projectId) {
        // todo: validation
        return projectService.getCurrentSprintInProject(projectId);
    }

    // get all tasks in concrete sprint
    @GetMapping(value = "/{projectId}/sprints/{sprintId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllTasksInSprint(@PathVariable Long projectId,
                                          @PathVariable Long sprintId) {
        // todo: validation
        return projectService.getAllTasksInSprint(projectId, sprintId);
    }

    // get tasks in sprint with concrete priority
    @GetMapping(value = "/{projectId}/sprints/{sprintId}/tasks/{taskPriority}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getTasksInSprintByPriority(@PathVariable Long projectId,
                                                 @PathVariable Long sprintId,
                                                 @PathVariable TaskPriority taskPriority) {
        // todo: validation
        return sprintService.getTasksInSprintByPriority(sprintId, taskPriority);
    }

    // create new project
    @PreAuthorize("hasRole('EMPOWERED_EMPLOYEE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createProject(@RequestBody Project project) {
        // todo: validation
        projectService.createProject(project);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", project.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // update the whole project
    @PreAuthorize("hasRole('EMPOWERED_EMPLOYEE')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProject(@RequestBody Project project) {
        // todo: validation
        projectService.updateProject(project);
    }

    // update some parts of project
    @PatchMapping(value = "/{projectId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void partialProjectUpdate(@PathVariable Long projectId,
                                     @RequestBody Map<String, Object> updates) {
        // todo: validation
        projectService.partialProjectUpdate(projectId, updates);
    }

    // delete the whole project
    @PreAuthorize("hasRole('EMPOWERED_EMPLOYEE')")
    @DeleteMapping(value = "/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long projectId) {
        // todo: validation
        projectService.deleteProject(projectId);
    }

    // delete employee from project
    @PreAuthorize("hasRole('EMPOWERED_EMPLOYEE')")
    @DeleteMapping(value = "/{projectId}/employees/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeFromProject(@PathVariable Long projectId,
                                          @PathVariable Long employeeId) {
        // todo: validation
        projectService.removeEmployeeFromProject(projectId, employeeId);
    }
}
