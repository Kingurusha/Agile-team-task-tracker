package cz.cvut.ear.exception;

public class NoSuchEntityException extends TaskTrackerException {
    public NoSuchEntityException(String message) {
        super(message);
    }

    public static NoSuchEntityException create(String resourceName, Object identifier) {
        return new NoSuchEntityException(resourceName + " identified by " + identifier + " not found.");
    }
}
