package cz.cvut.ear.repository;

import cz.cvut.ear.Generator;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class SprintRepositoryTest {
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public SprintRepositoryTest(SprintRepository sprintRepository, ProjectRepository projectRepository) {
        this.sprintRepository = sprintRepository;
        this.projectRepository = projectRepository;
    }


    @Test
    public void findByOrdinalNumberInProjectReturnsMatchingSprint() {
        final Sprint sprint = Generator.generateSprint();
        final Project project = sprint.getProject();

        projectRepository.saveAndFlush(project);
        sprintRepository.saveAndFlush(sprint);

        Long projectId = project.getId();
        Integer sprintOrdinalNumberInProject = sprint.getOrdinalNumberInProject();

        assertEquals(sprint.getId(), sprintRepository.findByProjectIdAndOrdinalNumberInProject(projectId, sprintOrdinalNumberInProject).get().getId());
    }

    @Test
    public void findByOrdinalNumberInProjectReturnsOptionalEmpty() {
        final Sprint sprint = Generator.generateSprint();
        final Project project = sprint.getProject();

        Long projectId = project.getId();
        Integer sprintOrdinalNumberInProject = sprint.getOrdinalNumberInProject();

        assertEquals(Optional.empty(), sprintRepository.findByProjectIdAndOrdinalNumberInProject(projectId, sprintOrdinalNumberInProject));
    }
}
