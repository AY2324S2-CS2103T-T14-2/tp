package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the severity of a patient's visit.
 * Guarantees: immutable; is valid as declared in {@link #isValidSeverity(String)}
 */
public class Severity {
    public static final String MESSAGE_CONSTRAINTS = "Severity should only be High or Low";

    public final SeverityOption severity;

    /**
     * Constructs a {@code Severity}.
     *
     * @param severity A valid severity.
     */
    public Severity(String severity) {
        requireNonNull(severity);
        checkArgument(isValidSeverity(severity), MESSAGE_CONSTRAINTS);
        this.severity = assignSeverity(severity);
    }

    /**
     * Returns true if a given string is a valid severity.
     */
    public static boolean isValidSeverity(String test) {
        return test.equals("High") || test.equals("Low") ? true : false;
    }

    /**
     * Assign one of SeverityOption, High or Low, depending on the severity input.
     *
     * @param severity severity input.
     * @return one of SeverityOption
     */
    public SeverityOption assignSeverity(String severity) {
        if (severity.equals("High")) {
            return SeverityOption.HIGH;
        } else if (severity.equals("Low")) {
            return SeverityOption.LOW;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return severity.getLabel();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Severity)) {
            return false;
        }

        Severity otherSeverity = (Severity) other;
        return severity.equals(otherSeverity.severity);
    }

    @Override
    public int hashCode() {
        return severity.hashCode();
    }
}
