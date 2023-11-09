package cz.cvut.ear.rest;

import cz.cvut.ear.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/project")
public class ProjectController {
    private final ProjectService projectService;
    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
}
