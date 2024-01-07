package cz.cvut.ear.rest;

import cz.cvut.ear.helper.RestUtils;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.service.TaskService;
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
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;


    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    // get task by concrete id
    @GetMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    // get all overdue tasks that still open
    // done
    @GetMapping(value = "/overdue-and-open", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllOverdueOpenTasks() {
        return taskService.getAllOverdueOpenTasks();
    }

    // get tasks that have deadline before the date
    // done
    @GetMapping(value = "/due-to/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getTasksDueToDate(@PathVariable LocalDate date) {
        return taskService.getTasksDueToDate(date);
    }

    // create new task
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTask(@RequestBody Task task) {
        taskService.createTask(task);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", task.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // update the whole task
    @PreAuthorize("hasRole('ROLE_EMPOWERED')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
    }

    // update some parts of task
    @PatchMapping(value = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void partialTaskUpdate(@PathVariable Long taskId,
                                  @RequestBody Map<String, Object> updates) {
        taskService.partialTaskUpdate(taskId, updates);
    }

    // delete the whole task
    @PreAuthorize("hasRole('ROLE_EMPOWERED')")
    @DeleteMapping(value = "/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    // delete employee from participants
    @PreAuthorize("hasRole('ROLE_EMPOWERED')")
    @DeleteMapping(value = "/{taskId}/participants/{employeeId}")
    public void deleteEmployeeFromParticipants(@PathVariable Long taskId,
                                               @PathVariable Long employeeId) {
        taskService.deleteEmployeeFromParticipants(taskId, employeeId);
    }

    // delete employee from task assignee
    @DeleteMapping(value = "/{taskId}/assignee/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeFromAssignee(@PathVariable Long taskId,
                                           @PathVariable Long employeeId) {
        taskService.deleteEmployeeFromAssignee(taskId, employeeId);
    }

    // delete label from task
    @DeleteMapping(value = "/{taskId}/labels/{labelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelFromTask(@PathVariable Long taskId,
                                    @PathVariable Long labelId) {
        taskService.deleteLabelFromTask(taskId, labelId);
    }
}
