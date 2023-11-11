package cz.cvut.ear.rest;

import cz.cvut.ear.service.RegularEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/regular-employee")
public class RegularEmployeeController {
    private final RegularEmployeeService regularEmployeeService;
    private static final Logger LOG = LoggerFactory.getLogger(RegularEmployeeController.class);

    @Autowired
    public RegularEmployeeController(RegularEmployeeService regularEmployeeService) {
        this.regularEmployeeService = regularEmployeeService;
    }
}
