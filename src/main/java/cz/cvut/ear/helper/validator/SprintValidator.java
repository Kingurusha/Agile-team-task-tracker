package cz.cvut.ear.helper.validator;

import cz.cvut.ear.model.Sprint;
import org.springframework.stereotype.Component;

// TODO

@Component
public class SprintValidator {

    public static void validateCreate(Sprint sprint) {
    }

    public static void validateStart(Sprint sprint) {
        // checks that sprint exists
        // checks that sprint.getProject() != null
        // checks the ordinal numbers in incoming sprint and previous
        // checks that the previous sprint in project was closed

    }

    public static void validateEnd(Sprint sprint) {
        // checks that sprint exists
        // checks that all tasks in sprint are RELEASED_TO_PRODUCTION so the sprint can be closed
    }

    public static void validateDelete(Sprint sprint) {
    }
}
