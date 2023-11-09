package cz.cvut.ear.rest;

import cz.cvut.ear.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/manager")
public class ManagerController {
    private final ManagerService managerService;
    private static final Logger LOG = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }
}
