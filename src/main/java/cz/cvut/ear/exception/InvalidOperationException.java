package cz.cvut.ear.exception;

/**
 * Indicates that an invalid operation has been invoked
 */
public class InvalidOperationException extends TaskTrackerException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
