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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasAnyRole('ROLE_EMPOWERED', 'ROLE_REGULAR')")
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
        return sprintService.getSprintById(sprintId);
    }

    // get all sprints where goal contains key word
    // done
    @GetMapping(value = "/goal/{goalKeyWord}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sprint> getAllSprintsWithGoalKeyWord(@PathVariable String goalKeyWord) {
        return sprintService.getAllSprintsWithGoalKeyWord(goalKeyWord);
    }

    // get sprints that were started between two dates
    @GetMapping(value = "/between/first-date/{firstDate}/second-date/{secondDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sprint> getSprintsByStartDateBetween(@PathVariable LocalDate firstDate,
                                                     @PathVariable LocalDate secondDate) {
        return sprintService.getSprintsByStartDateBetween(firstDate, secondDate);
    }

    // create new sprint
    @PreAuthorize("hasRole('ROLE_EMPOWERED')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSprint(@RequestBody Sprint sprint) {
        sprintService.createSprint(sprint);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", sprint.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // update the whole sprint
    @PreAuthorize("hasRole('ROLE_EMPOWERED')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSprint(@RequestBody Sprint sprint) {
        sprintService.updateSprint(sprint);
    }

    // update some parts of sprint
    @PatchMapping(value = "/{sprintId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void partialSprintUpdate(@PathVariable Long sprintId,
                                    @RequestBody Map<String, Object> updates) {
        sprintService.partialSprintUpdate(sprintId, updates);
    }
}
