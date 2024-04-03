package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's condition for a visit.
 * Guarantees: immutable; is valid as declared in {@link #isValidCondition(String)}
 */
public class Condition {

    public static final String MESSAGE_CONSTRAINTS = "Condition can take any values, and it should not be blank";

    /*
     * The first character of the condition must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Condition}.
     *
     * @param condition A valid condition.
     */
    public Condition(String condition) {
        requireNonNull(condition);
        checkArgument(isValidCondition(condition), MESSAGE_CONSTRAINTS);
        value = condition;
    }

    /**
     * Returns true if a given string is a valid condition.
     */
    public static boolean isValidCondition(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Condition)) {
            return false;
        }

        Condition otherCondition = (Condition) other;
        return value.equals(otherCondition.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
