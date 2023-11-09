package cz.cvut.ear.service;

import cz.cvut.ear.model.Project;

import java.util.List;

public interface ProjectService {
    Project addProject(Project project);
    void deleteProject(long projectId);
    Project getByName(String name);
    Project editProject(Project project);
    List<Project> getAllProjects();
}