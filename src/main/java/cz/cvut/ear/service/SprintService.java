package cz.cvut.ear.service;

import cz.cvut.ear.dao.ProjectRepository;
import cz.cvut.ear.dao.SprintRepository;
import cz.cvut.ear.exception.InvalidOperationException;
import cz.cvut.ear.exception.NoSuchEntityException;
import cz.cvut.ear.helpers.validators.SprintValidator;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.SprintStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class SprintService {
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;


    @Autowired
    public SprintService(SprintRepository sprintRepository, ProjectRepository projectRepository) {
        this.sprintRepository = sprintRepository;
        this.projectRepository = projectRepository;
    }


    // done (кроме Transactional)
    public void startSprintById(Long sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> NoSuchEntityException.create("Sprint" , sprintId));
        baseStartSprint(sprint);
    }

    // done (кроме Transactional)
    public void startSprintByOrdinalNumber(Long projectId, Integer sprintOrdinalNumberInProject) {
        Sprint sprint = findByOrdinalNumberInProject(projectId, sprintOrdinalNumberInProject);
        baseStartSprint(sprint);
    }

    // done (кроме Transactional)
    public void endSprintById(Long sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> NoSuchEntityException.create("Sprint" , sprintId));
        baseEndSprint(sprint);
    }

    // done (кроме Transactional)
    public void endSprintByOrdinalNumber(Long projectId, Integer sprintOrdinalNumberInProject) {
        Sprint sprint = findByOrdinalNumberInProject(projectId, sprintOrdinalNumberInProject);
        baseEndSprint(sprint);
    }


    // done (кроме Transactional и save/saveAndFlush)
    private void baseStartSprint(Sprint sprint) {
        SprintValidator.validateStart(sprint);

        Project project = sprint.getProject();

        sprint.setSprintStatus(SprintStatus.IN_PROGRESS);
        sprint.setStartDateTime(LocalDateTime.now());
        project.setCurrentSprint(sprint);

        sprintRepository.save(sprint); // saveAndFlush?
        projectRepository.save(project); // saveAndFlush?
    }

    // done (кроме Transactional и save/saveAndFlush)
    private void baseEndSprint(Sprint sprint) {
        SprintValidator.validateEnd(sprint);

        Project project = sprint.getProject();

        sprint.setSprintStatus(SprintStatus.CLOSED);
        sprint.setEndDateTime(LocalDateTime.now());
        project.setCurrentSprint(null);

        sprintRepository.save(sprint); // saveAndFlush?
        projectRepository.save(project); // saveAndFlush?
    }


    // done? (кроме Transactional)
    @Transactional(readOnly = true)
    public Sprint findByOrdinalNumberInProject(Long projectId, Integer sprintOrdinalNumberInProject) {
        Optional<Sprint> sprintOptional = sprintRepository.findByOrdinalNumberInProject(projectId, sprintOrdinalNumberInProject);
        if (sprintOptional.isEmpty()) {
            throw new NoSuchEntityException("In the project with the id " + projectId +
                    " was not found sprint with ordinal number " + sprintOrdinalNumberInProject);
        }
        return sprintOptional.get();
    }










    // ----------------------------------------

    /**
     * Creates a sprint in the project, with a particular sequence number
     *
     * @param projectId id of the project to which the sprint will belong
     */
    public void createSprint(Sprint sprint) {
        SprintValidator.validateCreate(sprint);

        Project project = sprint.getProject();
        // project.sprints.add(sprint)

        sprintRepository.save(sprint); // saveAndFlush?
        projectRepository.save(project); // saveAndFlush?


        // Crud
        // добавить в спринт в колонку проект id проекта
        //порядковый номер спринта в проекте
        // статус спринта
        // tasksInSprint = []
        // springdao.persist(new sprint)
        // startDateTime. endDatetime
        // persist(all)
    }

    /**
     * Basic logic for sprint start
     *
     * @param sprint - NonNull Sprint object which will be started
     * @param project
     */







    /**
     * Set the sprint status to "closed"
     *
     */






    // not done
    @Transactional
    public void deleteSprint(long sprintId) {
        // cruD
        //sprintRepository.deleteById(sprintId);
        // удалить все таски у людей, из проета сложность
        // offtop currentSprint в проекте, цель в спринте
    }





    /**
     * Delete the sprint and all tasks in it from the tracking system
     *
     */




    // ----- HELPERS -----------
    private void validateStarting(Sprint sprint, Project project) {
        if (sprint.getSprintStatus() != SprintStatus.FUTURE
                || project == null
                || false
                || false) {
            throw new InvalidOperationException("ff");
        }
        // он в статусе будущий(иначе ошибка)
        // предыдуший закрыт
        // проверить порядковый номер
    }


    private int generateOrdinalNumber() {
        return 1;
    }
}
