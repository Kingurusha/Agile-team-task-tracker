package cz.cvut.ear.service.impl;

import cz.cvut.ear.DAO.ProjectRepository;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project addProject(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    @Override
    public void deleteProject(long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project getByName(String name) {
        return projectRepository.findByName(name);
    }

    @Override
    public Project editProject(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}