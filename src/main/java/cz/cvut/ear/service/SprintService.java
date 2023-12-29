package cz.cvut.ear.service;

import cz.cvut.ear.dao.ProjectRepository;
import cz.cvut.ear.dao.SprintRepository;
import cz.cvut.ear.dao.TaskRepository;
import cz.cvut.ear.exception.NoSuchEntityException;
import cz.cvut.ear.helpers.validators.SprintValidator;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.ProjectStatus;
import cz.cvut.ear.model.enums.SprintStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SprintService {
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public SprintService(SprintRepository sprintRepository, ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.sprintRepository = sprintRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }


    public void startSprintById(Long sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> NoSuchEntityException.create("Sprint" , sprintId));
        baseStartSprint(sprint);
    }

    public void startSprintByOrdinalNumber(Long projectId, Integer sprintOrdinalNumberInProject) {
        Sprint sprint = findByOrdinalNumberInProject(projectId, sprintOrdinalNumberInProject);
        baseStartSprint(sprint);
    }

    public void endSprintById(Long sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> NoSuchEntityException.create("Sprint" , sprintId));
        baseEndSprint(sprint);
    }

    public void endSprintByOrdinalNumber(Long projectId, Integer sprintOrdinalNumberInProject) {
        Sprint sprint = findByOrdinalNumberInProject(projectId, sprintOrdinalNumberInProject);
        baseEndSprint(sprint);
    }

    /**
     * Basic logic for sprint starting
     *
     * @param sprint - NonNull Sprint object which will be started
     */
    private void baseStartSprint(Sprint sprint) {
        SprintValidator.validateStart(sprint);

        Project project = sprint.getProject();

        sprint.setSprintStatus(SprintStatus.IN_PROGRESS);
        sprint.setStartDateTime(LocalDateTime.now());
        project.setCurrentSprint(sprint);

        sprintRepository.save(sprint);
        projectRepository.save(project);
    }

    /**
     * Basic logic for sprint ending
     *
     * @param sprint - NonNull Sprint object which will be started
     */
    private void baseEndSprint(Sprint sprint) {
        SprintValidator.validateEnd(sprint);

        Project project = sprint.getProject();

        sprint.setSprintStatus(SprintStatus.CLOSED);
        sprint.setEndDateTime(LocalDateTime.now());
        project.setCurrentSprint(null);

        sprintRepository.save(sprint);
        projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    public Sprint findByOrdinalNumberInProject(Long projectId, Integer sprintOrdinalNumberInProject) {
        Optional<Sprint> sprintOptional = sprintRepository.findByProjectIdAndOrdinalNumberInProject(projectId, sprintOrdinalNumberInProject);
        if (sprintOptional.isEmpty()) {
            throw new NoSuchEntityException("In the project with the id " + projectId +
                    " was not found sprint with ordinal number " + sprintOrdinalNumberInProject);
        }
        return sprintOptional.get();
    }

    /**
     * Delete the sprint and all related tasks from the tracking system
     *
     */
    @Transactional
    public void deleteSprint(Sprint sprint) {
        SprintValidator.validateDelete(sprint);
        Set<Task> tasksToDelete = new HashSet<>(taskRepository.findBySprintId(sprint.getId()));
        for(Task task: tasksToDelete) {
            taskRepository.deleteById(task.getId());
        }
        sprintRepository.deleteById(sprint.getId());
    }

    @Transactional
    public void createSprint(Sprint sprint) {
        SprintValidator.validateCreate(sprint);

        Project project = sprint.getProject();

        if (project.getProjectStatus() == ProjectStatus.CLOSED) {
            throw new IllegalStateException("Cannot create sprint for a closed project.");
        }

        if (project.getSprintsInProject().stream().anyMatch(existingSprint -> Objects.equals(existingSprint.getOrdinalNumberInProject(), sprint.getOrdinalNumberInProject()))) {
            throw new IllegalStateException("Sprint number must be unique within the project.");
        }

        project.getSprintsInProject().add(sprint);

        sprintRepository.save(sprint);
        projectRepository.save(project);
    }

    public List<Sprint> showSprintsByGoalContainingIgnoreCase(String keyword) {
        return sprintRepository.findSprintsByGoalContainingIgnoreCase(keyword);
    }

    public List<Sprint> showSprintsByStartDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return sprintRepository.findSprintsByStartDateTimeBetween(startDate, endDate);
    }
}
