package cz.cvut.ear.helper.validator;

import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.enums.SprintStatus;
import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.repository.ProjectRepository;
import cz.cvut.ear.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SprintValidator {

    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;

    @Autowired
    public SprintValidator(SprintRepository sprintRepository, ProjectRepository projectRepository) {
        this.sprintRepository = sprintRepository;
        this.projectRepository = projectRepository;
    }

    public void validateSprintExistsById(Long sprintId) {
        if (!sprintRepository.existsById(sprintId)) {
            throw new IllegalArgumentException("Sprint with ID " + sprintId + " does not exist.");
        }
    }

    public void validateSprintExists(Sprint sprint) {
        if (sprint == null || sprint.getId() == null || !sprintRepository.existsById(sprint.getId())) {
            throw new IllegalArgumentException("Sprint does not exist.");
        }
    }



    public void validateGetSprintByOrdinalNumberInProject(Long projectId, Integer sprintOrdinalNumber) {
        if (!sprintRepository.existsByOrdinalNumberInProjectAndProjectId(sprintOrdinalNumber, projectId)) {
            throw new IllegalArgumentException("Sprint with ordinal number " + sprintOrdinalNumber +
                    " does not exist in project with ID " + projectId);
        }
    }

    public void validateSprintAndPriority(Long sprintId, TaskPriority taskPriority) {
        if (!sprintRepository.existsById(sprintId)) {
            throw new IllegalArgumentException("Sprint with ID " + sprintId + " does not exist.");
        }

        if (!sprintRepository.existsByTaskPriority(sprintId, taskPriority)) {
            throw new IllegalArgumentException("No tasks with priority " + taskPriority + " found in the sprint with ID " + sprintId + ".");
        }
    }

    public void validateCreate(Sprint sprint) {
        if (sprint == null) {
            throw new IllegalArgumentException("Sprint is null.");
        }
        if (sprintRepository.existsById(sprint.getId())) {
            throw new IllegalArgumentException("Sprint with ID " + sprint.getId() + " already exists.");
        }
        Project project = sprint.getProject();
        if (project == null || project.getId() == null || !projectRepository.existsById(project.getId())) {
            throw new IllegalArgumentException("Project associated with the sprint does not exist.");
        }
        if (!sprint.getOrdinalNumberInProject().equals(project.getSprintsInProject().size() + 1)) {
            throw new IllegalArgumentException("Incorrect sprint ordinal number in project.");
        }
        if (!sprint.getSprintStatus().equals(SprintStatus.FUTURE)) {
            throw new IllegalArgumentException("Incorrect sprint status.");
        }
    }
}
