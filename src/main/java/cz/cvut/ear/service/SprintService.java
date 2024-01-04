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
    private static final Logger LOG = LoggerFactory.getLogger(SprintService.class);


    @Autowired
    public SprintService(SprintRepository sprintRepository, ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.sprintRepository = sprintRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }


    // done
    @Transactional(readOnly = true)
    public Sprint getSprintById(Long sprintId) {
        return sprintRepository.findById(sprintId).get();
    }

    @Transactional(readOnly = true)
    public Sprint getSprintByOrdinalNumberInProject(Long projectId, Integer sprintOrdinalNumber) {
        return null;
    }

    // done
    @Transactional(readOnly = true)
    public List<Task> getTasksInSprintByPriority(Long sprintId, TaskPriority taskPriority) {
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
        Project project = sprint.getProject();

        // add the transferred sprint to the corresponding project
        project.getSprintsInProject().add(sprint);

        // set the correct ordinal number in project
        Integer correctOrdinalNumberInProject = project.getSprintsInProject().size() + 1;
        if (!sprint.getOrdinalNumberInProject().equals(correctOrdinalNumberInProject)) {
            LOG.warn("Incorrect sprint ordinal number in project transmitted, number {} will be overwriting to {}",
                    sprint.getOrdinalNumberInProject(), correctOrdinalNumberInProject);
        }
        sprint.setOrdinalNumberInProject(correctOrdinalNumberInProject);

        // set the correct sprint status
        SprintStatus correctSprintStatus = SprintStatus.FUTURE;
        if (!sprint.getSprintStatus().equals(correctSprintStatus)) {
            LOG.warn("Incorrect sprint status transmitted, status {} will be overwriting to {}",
                    sprint.getSprintStatus(), correctSprintStatus);
        }
        sprint.setSprintStatus(correctSprintStatus);

        projectRepository.save(project);
        sprintRepository.save(sprint);

        LOG.debug("Created sprint {}.", sprint);
    }

    @Transactional
    public void updateSprint(Sprint sprint) {
        LOG.debug("");
    }

    @Transactional
    public void partialSprintUpdate(Long sprintId, Map<String, Object> updates) {
        LOG.debug("");
    }


    // ------------------- old --------------------------

//    public void startSprintById(Long sprintId) {
//        Sprint sprint = sprintRepository.findById(sprintId)
//                .orElseThrow(() -> NoSuchEntityException.create("Sprint" , sprintId));
//        baseStartSprint(sprint);
//    }
//
//    public void startSprintByOrdinalNumber(Long projectId, Integer sprintOrdinalNumberInProject) {
//        Sprint sprint = findByOrdinalNumberInProject(projectId, sprintOrdinalNumberInProject);
//        baseStartSprint(sprint);
//    }
//
//    public void endSprintById(Long sprintId) {
//        Sprint sprint = sprintRepository.findById(sprintId)
//                .orElseThrow(() -> NoSuchEntityException.create("Sprint" , sprintId));
//        baseEndSprint(sprint);
//    }
//
//    public void endSprintByOrdinalNumber(Long projectId, Integer sprintOrdinalNumberInProject) {
//        Sprint sprint = findByOrdinalNumberInProject(projectId, sprintOrdinalNumberInProject);
//        baseEndSprint(sprint);
//    }

    /**
     * Basic logic for sprint starting
     *
     * @param sprint - NonNull Sprint object which will be started
     */
    private void baseStartSprint(Sprint sprint) {
        SprintValidator.validateStart(sprint);

        Project project = sprint.getProject();

        sprint.setSprintStatus(SprintStatus.IN_PROGRESS);
        sprint.setStartDate(LocalDate.now());
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
        sprint.setEndDate(LocalDate.now());
        project.setCurrentSprint(null);

        sprintRepository.save(sprint);
        projectRepository.save(project);
    }

//    @Transactional(readOnly = true)
//    public Sprint findByOrdinalNumberInProject(Long projectId, Integer sprintOrdinalNumberInProject) {
//        Optional<Sprint> sprintOptional = sprintRepository.findByProjectIdAndOrdinalNumberInProject(projectId, sprintOrdinalNumberInProject);
//        if (sprintOptional.isEmpty()) {
//            throw new NoSuchEntityException("In the project with the id " + projectId +
//                    " was not found sprint with ordinal number " + sprintOrdinalNumberInProject);
//        }
//        return sprintOptional.get();
//    }

//    /**
//     * Delete the sprint and all related tasks from the tracking system
//     *
//     */
//    @Transactional
//    public void deleteSprint(Sprint sprint) {
//        SprintValidator.validateDelete(sprint);
//        Set<Task> tasksToDelete = new HashSet<>(taskRepository.findBySprintId(sprint.getId()));
//        for(Task task: tasksToDelete) {
//            taskRepository.deleteById(task.getId());
//        }
//        sprintRepository.deleteById(sprint.getId());
//    }
}
