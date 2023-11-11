package cz.cvut.ear.service;

import cz.cvut.ear.dao.ProjectRepository;
import cz.cvut.ear.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void showAllTasksInProject() {

    }

    public void showActiveTasksInProject() {

    }

    // ----- OLD -------------

    public Project addProject(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    public void deleteProject(long projectId) {
        projectRepository.deleteById(projectId);
    }

    public Project getByName(String name) {
        return projectRepository.findByProjectName(name).get();
    }

    public Project editProject(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
