package cz.cvut.ear.rest;

import cz.cvut.ear.helper.RestUtils;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@PreAuthorize("hasAnyRole('EMPOWERED_EMPLOYEE', 'REGULAR_EMPLOYEE')")
@RequestMapping("/api/sprints")
public class SprintController {
    private final SprintService sprintService;


    @Autowired
    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }


    // get sprint with concrete id
    @GetMapping(value = "/{sprintId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sprint getSprintById(@PathVariable Long sprintId) {
        // todo: validation
        return sprintService.getSprintById(sprintId);
    }

    // create new sprint
    @PreAuthorize("hasRole('EMPOWERED_EMPLOYEE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSprint(@RequestBody Sprint sprint) {
        // todo: validation
        sprintService.createSprint(sprint);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", sprint.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // update the whole sprint
    @PreAuthorize("hasRole('EMPOWERED_EMPLOYEE')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSprint(@RequestBody Sprint sprint) {
        // todo: validation
        sprintService.updateSprint(sprint);
    }

    // update some parts of sprint
    @PatchMapping(value = "/{sprintId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void partialSprintUpdate(@PathVariable Long sprintId,
                                    @RequestBody Map<String, Object> updates) {
        // todo: validation
        sprintService.partialSprintUpdate(sprintId, updates);
    }
}
