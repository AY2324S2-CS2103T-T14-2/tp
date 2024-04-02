package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

import seedu.address.logic.parser.DateTimeParser;
/**
 * Represents a Patient's date of birth.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS =
            "Date of birth should only contain numeric values in dd/MM/yyyy or yyyy-MM-dd format, and it should not "
                    + "be left blank";

    public final LocalDate dateOfBirth;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dateOfBirth A valid date of birth.
     */
    public DateOfBirth(String dateOfBirth) {
        requireNonNull(dateOfBirth);
        checkArgument(isValidDateOfBirth(dateOfBirth), MESSAGE_CONSTRAINTS);
        this.dateOfBirth = DateTimeParser.parseDateTime(dateOfBirth);
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        return DateTimeParser.parseDateTime(test) == null ? false : true;
    }

    @Override
    public String toString() {
        return DateTimeParser.outputDateTime(dateOfBirth);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateOfBirth)) {
            return false;
        }

        DateOfBirth otherDateOfBirth = (DateOfBirth) other;
        return dateOfBirth.equals(otherDateOfBirth.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return dateOfBirth.hashCode();
    }
}
