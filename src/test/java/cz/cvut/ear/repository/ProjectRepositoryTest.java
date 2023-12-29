package cz.cvut.ear.repository;

import cz.cvut.ear.Generator;
import cz.cvut.ear.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ProjectRepositoryTest {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectRepositoryTest(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Test
    public void findByProjectNameReturnsProjectWithMatchingProjectName() {
        final Project project = Generator.generateProject();
        String projectName = project.getProjectName();
        projectRepository.saveAndFlush(project);

        assertEquals(project.getId(), projectRepository.findByProjectName(projectName).get().getId());
    }

    @Test
    public void findByProjectNameReturnsOptionalEmptyForUnknownProjectName() {
        final Project project = Generator.generateProject();
        String projectName = project.getProjectName();

        assertEquals(Optional.empty(), projectRepository.findByProjectName(projectName));
    }
}
