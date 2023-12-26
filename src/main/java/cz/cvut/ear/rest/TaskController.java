package cz.cvut.ear.rest;

import cz.cvut.ear.helper.RestUtils;
import cz.cvut.ear.helper.validator.TaskValidator;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;


    // get task by concrete id
    // all
    @GetMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    // create new task
    // all
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTask(@RequestBody Task task) {
        TaskValidator.validateCreate(task);
        taskService.createTask(task);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", task.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // update the whole task
    // all
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTask(@RequestBody Task task) {
        TaskValidator.validateUpdate(task);
        taskService.updateTask(task);
    }

    // update some parts of task
    // all/admin depends on part
    @RequestMapping(value = "/{taskId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialTaskUpdate(@PathVariable Long taskId,
                                                  @RequestBody Map<String, Object> updates) {
        TaskValidator.validatePartialUpdate(taskId, updates);
        taskService.partialTaskUpdate(taskId, updates);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // delete the whole task
    // admin
    @DeleteMapping(value = "/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        TaskValidator.validateDelete(taskId);
        taskService.deleteTask(taskId);
    }

    // delete employee from participants
    // admin
    @DeleteMapping(value = "/{taskId}/participants/{employeeId}")
    public void deleteEmployeeFromParticipants(@PathVariable Long taskId,
                                               @PathVariable Long employeeId) {
        TaskValidator.validateParticipantDelete(taskId, employeeId);
        taskService.deleteEmployeeFromParticipants(taskId, employeeId);
    }

    // delete employee from task assignee
    // all
    @DeleteMapping(value = "/{taskId}/assignee/{employeeId}")
    public void deleteEmployeeFromAssignee(@PathVariable Long taskId,
                                           @PathVariable Long employeeId) {
        TaskValidator.validateAssigneeDelete(taskId, employeeId);
        taskService.deleteEmployeeFromAssignee(taskId, employeeId);
    }

    // delete label from task
    // all
    @DeleteMapping(value = "/{taskId}/labels/{labelId}")
    public void deleteLabelFromTask(@PathVariable Long taskId,
                                    @PathVariable Long labelId) {
        TaskValidator.validateLabelDelete(taskId, labelId);
        taskService.deleteLabelFromTask(taskId, labelId);
    }
}
