package cz.cvut.ear.helper.validator;

import cz.cvut.ear.repository.SprintRepository;
import cz.cvut.ear.repository.TaskRepository;
import cz.cvut.ear.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TaskValidator {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SprintRepository sprintRepository;


    // TODO
    public static void validateCreate(Task task) {
        // checks if task with same id not exists in DB
        // checks if sprint exists (if it is not null) and sprintStatus != CLOSED
        // checks that task points are fibonacci numbers and 1 <= taskPoints <= 25
        // checks that task status == TO_DO
        // checks that dueDate >= now (if it is not null)
        // checks that assignee exists
        // check that participants exist
        // DONE
    }

    // TODO
    public static void validateUpdate(Task task) {
    }

    // TODO
    public static void validatePartialUpdate(Long taskId, Map<String, Object> updates) {
    }

    // TODO
    public static void validateDelete(Long taskId) {
        // checks if task with same id exists in DB
        // DONE
    }

    // TODO
    public static void validateParticipantDelete(Long taskId, Long employeeId) {
        // checks if task with same id exists in DB
        // checks if employee with same id exists in DB
        // checks if employee is in task participants
        // DONE
    }

    // TODO
    public static void validateAssigneeDelete(Long taskId, Long employeeId) {
        // checks if task with same id exists in DB
        // checks if employee with same id exists in DB
        // checks if employee is in task assignee
        // DONE
    }

    // TODO
    public static void validateLabelDelete(Long taskId, Long labelId) {
        // checks if task with same id exists in DB
        // checks if label with same id exists in DB
        // checks if label is in task labels
        // DONE
    }
}
