package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Members (Members are considered duplicates if they have the same
 * identity).
 */
public class DuplicateItemException extends RuntimeException {
    public DuplicateItemException() {
        super("Operation would result in duplicate members");
    }
}
