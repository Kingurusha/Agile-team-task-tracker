package cz.cvut.ear.rest;

import cz.cvut.ear.service.EmpoweredEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/empowered-employee")
public class EmpoweredEmployeeController {
    private final EmpoweredEmployeeService empoweredEmployeeService;
    private static final Logger LOG = LoggerFactory.getLogger(EmpoweredEmployeeController.class);

    @Autowired
    public EmpoweredEmployeeController(EmpoweredEmployeeService empoweredEmployeeService) {
        this.empoweredEmployeeService = empoweredEmployeeService;
    }
}
