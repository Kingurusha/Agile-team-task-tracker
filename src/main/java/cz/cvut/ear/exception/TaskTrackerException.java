package cz.cvut.ear.exception;

/**
 * Base for all application-specific exceptions
 */
public class TaskTrackerException extends RuntimeException {
    public TaskTrackerException() {
    }

    public TaskTrackerException(String message) {
        super(message);
    }
}
