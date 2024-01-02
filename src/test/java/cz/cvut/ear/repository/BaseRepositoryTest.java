package cz.cvut.ear.repository;

import cz.cvut.ear.Generator;
import cz.cvut.ear.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BaseRepositoryTest {
    private final ProjectRepository projectRepository;

    @Autowired
    public BaseRepositoryTest(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Test
    public void existsByIdReturnsTrueForExistingIdentifier() {
        Project project = Generator.generateProject();
        project = projectRepository.saveAndFlush(project);

        assertTrue(projectRepository.existsById(project.getId()));
        assertFalse(projectRepository.existsById(-1L));
    }

    @Test
    public void saveUpdatesSpecifiedInstance() {
        Project project = Generator.generateProject();
        project = projectRepository.save(project);

        Project projectUpdate = new Project();
        projectUpdate.setId(project.getId());
        projectUpdate.setProjectName("Test_name");
        projectUpdate.setProjectStatus(project.getProjectStatus());
        projectRepository.save(projectUpdate);

        final Project result = projectRepository.findById(project.getId()).get();
        assertEquals(project.getProjectName(), result.getProjectName());
    }

    @Test
    public void findByIdRetrievesInstanceByIdentifier() {
        Project project = Generator.generateProject();
        project = projectRepository.saveAndFlush(project);
        assertNotNull(project.getId());

        Project resultProject = projectRepository.findById(project.getId()).get();
        assertEquals(project.getId(), resultProject.getId());
    }

    @Test
    public void findByIdReturnsOptionalEmptyWhenInstanceDoesNotExist() {
        assertEquals(Optional.empty(), projectRepository.findById(-1L));
    }

    @Test
    public void findAllRetrievesAllInstancesOfType() {
        Project project;

        for(int i = 0; i < 10; i++) {
            project = Generator.generateProject();
            projectRepository.saveAndFlush(project);
        }

        final List<Project> resultList = projectRepository.findAll();

        assertNotNull(resultList);
        assertEquals(10, resultList.size());
    }

    @Test
    public void findAllRetrievesEmptyListWhenNoSingleEntityExists() {
        final List<Project> resultList = projectRepository.findAll();

        assertEquals(0, resultList.size());
    }


    @Test
    public void deleteRemovesSpecifiedInstance() {
        Project project = Generator.generateProject();
        project = projectRepository.saveAndFlush(project);
        assertEquals(project.getId(), projectRepository.findById(project.getId()).get().getId());

        projectRepository.delete(project);
        assertEquals(Optional.empty(), projectRepository.findById(project.getId()));
    }

    @Test
    public void deleteDoesNothingWhenInstanceDoesNotExist() {
        Project project = Generator.generateProject();
        project.setId(-1L);
        assertEquals(Optional.empty(), projectRepository.findById(project.getId()));

        projectRepository.delete(project);
        assertEquals(Optional.empty(), projectRepository.findById(project.getId()));

    }
}
