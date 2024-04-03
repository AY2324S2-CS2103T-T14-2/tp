package seedu.address.model.patient.exceptions;

/**
 * Signals that the operation will result in duplicate Visits (Visits are considered duplicates if they have the
 * same value in their fields).
 */
public class DuplicateVisitException extends RuntimeException {
    public DuplicateVisitException() {
        super("Operation would result in duplicate visits");
    }
}
