package cz.cvut.ear.rest;

import cz.cvut.ear.service.SprintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/sprint")
public class SprintController {
    private final SprintService sprintService;
    private static final Logger LOG = LoggerFactory.getLogger(SprintController.class);

    @Autowired
    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }
}
