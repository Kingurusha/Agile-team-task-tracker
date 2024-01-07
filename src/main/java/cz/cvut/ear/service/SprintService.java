package cz.cvut.ear.service;

import cz.cvut.ear.helper.validator.SprintValidator;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.SprintStatus;
import cz.cvut.ear.model.enums.TaskPriority;
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

@Service
public class SprintService {
    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;
    private final SprintValidator sprintValidator;
    private static final Logger LOG = LoggerFactory.getLogger(SprintService.class);


    @Autowired
    public SprintService(SprintRepository sprintRepository, ProjectRepository projectRepository, TaskRepository taskRepository, SprintValidator sprintValidator) {
        this.sprintRepository = sprintRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.sprintValidator = sprintValidator;
    }


    // done
    @Transactional(readOnly = true)
    public Sprint getSprintById(Long sprintId) {
        sprintValidator.validateSprintExistsById(sprintId);
        return sprintRepository.findById(sprintId).get();
    }

    @Transactional(readOnly = true)
    public Sprint getSprintByOrdinalNumberInProject(Long projectId, Integer sprintOrdinalNumber) {
        sprintValidator.validateGetSprintByOrdinalNumberInProject(projectId, sprintOrdinalNumber);
        return sprintRepository.findByProjectIdAndOrdinalNumberInProject(projectId, sprintOrdinalNumber).get();
    }

    // done
    @Transactional(readOnly = true)
    public List<Task> getTasksInSprintByPriority(Long sprintId, TaskPriority taskPriority) {
        sprintValidator.validateSprintAndPriority(sprintId, taskPriority);
        return taskRepository.findBySprintIdAndTaskPriority(sprintId, taskPriority);
    }

    // done
    @Transactional(readOnly = true)
    public List<Sprint> getAllSprintsWithGoalKeyWord(String goalKeyWord) {
        return sprintRepository.findSprintsByGoalKeyword(goalKeyWord);
    }

    // done
    @Transactional(readOnly = true)
    public List<Sprint> getSprintsByStartDateBetween(LocalDate firstDate, LocalDate secondDate) {
        return sprintRepository.findSprintsByStartDateBetween(firstDate, secondDate);
    }

    // done
    @Transactional
    public void createSprint(Sprint sprint) {
        sprintValidator.validateCreate(sprint);
        Project project = sprint.getProject();
        sprintRepository.saveAndFlush(sprint);
        LOG.debug("Created sprint {}.", sprint);
        project.getSprintsInProject().add(sprint);
        projectRepository.saveAndFlush(project);
    }

    @Transactional
    public void updateSprint(Sprint sprint) {
        sprintValidator.validateSprintExists(sprint);
        sprintRepository.saveAndFlush(sprint);
        LOG.debug("Updated sprint {}.", sprint);
    }

    @Transactional
    public void partialSprintUpdate(Long sprintId, Map<String, Object> updates) {
        sprintValidator.validateSprintExistsById(sprintId);
        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow();

        updates.forEach((key, value) -> {
            switch (key) {
                case "startDate" -> sprint.setStartDate((LocalDate) value);
                case "endDate" -> sprint.setEndDate((LocalDate) value);
                case "sprintStatus" -> sprint.setSprintStatus((SprintStatus) value);
                case "goal" -> sprint.setGoal((String) value);
                case "ordinalNumberInProject" -> sprint.setOrdinalNumberInProject((Integer) value);
                default -> LOG.warn("Unknown field: {}", key);
            }
        });

        sprintRepository.saveAndFlush(sprint);
        LOG.debug("Updated sprint {}.", sprintId);
    }
}