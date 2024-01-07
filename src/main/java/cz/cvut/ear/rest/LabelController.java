package cz.cvut.ear.rest;

import cz.cvut.ear.helper.RestUtils;
import cz.cvut.ear.model.Label;
import cz.cvut.ear.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('ROLE_EMPOWERED', 'ROLE_REGULAR')")
@RequestMapping("/api/labels")
public class LabelController {
    private final LabelService labelService;


    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    // get all labels
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Label> getAllLabels() {
        return labelService.getAllLabels();
    }


    // get label with concrete id
    @GetMapping(value = "/{labelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Label getLabelById(@PathVariable Long labelId) {
        return labelService.getLabelById(labelId);
    }

    // get label with concrete name
    @GetMapping(value = "/name/{labelName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Label getLabelByName(@PathVariable String labelName) {
        return labelService.getLabelByName(labelName);
    }

    // create new label
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createLabel(@RequestBody Label label) {
        labelService.createLabel(label);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", label.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // update the whole label (with cascade change in all dependencies)
    @PreAuthorize("hasRole('ROLE_EMPOWERED')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@RequestBody Label label) {
        labelService.updateLabel(label);
    }

    // delete the whole label
    @PreAuthorize("hasRole('ROLE_EMPOWERED')")
    @DeleteMapping(value = "/d/{labelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Long labelId) {
        labelService.deleteLabel(labelId);
    }
}
