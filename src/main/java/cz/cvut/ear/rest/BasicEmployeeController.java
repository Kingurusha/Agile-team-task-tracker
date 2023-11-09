package cz.cvut.ear.rest;

import cz.cvut.ear.service.BasicEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/employee")
public class BasicEmployeeController {
    private final BasicEmployeeService basicEmployeeService;
    private static final Logger LOG = LoggerFactory.getLogger(BasicEmployeeController.class);

    @Autowired
    public BasicEmployeeController(BasicEmployeeService basicEmployeeService) {
        this.basicEmployeeService = basicEmployeeService;
    }
}
