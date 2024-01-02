package cz.cvut.ear.service;

import cz.cvut.ear.repository.ProjectRepository;
import cz.cvut.ear.model.Project;
import org.junit.jupiter.api.Test;
import cz.cvut.ear.model.enums.ProjectStatus;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

//    @Test
//    void addProject() {
//        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
//        ProjectService projectService = new ProjectService(projectRepository);
//
//        Project project = new Project();
//        project.setProjectName("Test Project");
//
//        when(projectRepository.saveAndFlush(project)).thenReturn(project);
//
//        Project resultProject = projectService.addProject(project);
//
//        assertEquals(project, resultProject);
//        verify(projectRepository, times(1)).saveAndFlush(project);
//    }
//
//    @Test
//    void deleteProject() {
//        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
//        ProjectService projectService = new ProjectService(projectRepository);
//
//        long projectId = 1L;
//        Project projectToDelete = new Project();
//        projectToDelete.setProjectStatus(ProjectStatus.CLOSED);
//        projectToDelete.setId(projectId);
//
//        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectToDelete));
//        doNothing().when(projectRepository).deleteById(projectId);
//
//        assertDoesNotThrow(() -> projectService.deleteProject(projectId));
//        verify(projectRepository, times(1)).deleteById(projectId);
//    }
//
//    @Test
//    void getByName() {
//        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
//        ProjectService projectService = new ProjectService(projectRepository);
//
//        String projectName = "Test Project";
//        Project project = new Project();
//        project.setProjectName(projectName);
//
//        when(projectRepository.findByProjectName(projectName)).thenReturn(Optional.of(project));
//
//        Project resultProject = projectService.getByName(projectName);
//
//        assertEquals(project, resultProject);
//    }
//
//    @Test
//    void editProject() {
//        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
//        ProjectService projectService = new ProjectService(projectRepository);
//
//        Project project = new Project();
//        project.setProjectName("Test Project");
//
//        when(projectRepository.saveAndFlush(project)).thenReturn(project);
//
//        Project resultProject = projectService.editProject(project);
//
//        assertEquals(project, resultProject);
//        verify(projectRepository, times(1)).saveAndFlush(project);
//    }
//
//    @Test
//    void getAllProjects() {
//        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
//        ProjectService projectService = new ProjectService(projectRepository);
//
//        List<Project> projects = new ArrayList<>();
//        projects.add(new Project());
//        projects.add(new Project());
//        projects.add(new Project());
//
//        when(projectRepository.findAll()).thenReturn(projects);
//
//        List<Project> resultProjects = projectService.getAllProjects();
//
//        assertEquals(projects.size(), resultProjects.size());
//    }
}