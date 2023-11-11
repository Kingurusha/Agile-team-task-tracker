package cz.cvut.ear.helpers.validators;

import cz.cvut.ear.exception.TaskTrackerException;
import cz.cvut.ear.model.Sprint;
import org.springframework.stereotype.Component;

@Component
public class SprintValidator {

    public static void validateCreate(Sprint sprint) {
        // ...
    }

    public static void validateStart(Sprint sprint) {
        // checks that sprint exists
        // checks that sprint.getProject() != null
        // ...
    }

    public static void validateEnd(Sprint sprint) {
        // checks that all tasks in sprint are RELEASED_TO_PRODUCTION so the sprint can be closed
        // ...
    }

    public static void validateDelete(Sprint sprint) {
        // ...
    }
}
