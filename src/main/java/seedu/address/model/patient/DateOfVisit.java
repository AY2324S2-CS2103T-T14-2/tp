package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

import seedu.address.logic.parser.DateTimeParser;

/**
 * Represents a patient's date of visit.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfVisit(String)}
 */
public class DateOfVisit {
    public static final String MESSAGE_CONSTRAINTS =
            "Date of visit should only contain numeric values in dd/MM/yyyy or yyyy-MM-dd format, "
                    + "and it should not be left blank";

    public final LocalDate dateOfVisit;

    /**
     * Constructs a {@code DateOfVisit}
     *
     * @param dateOfVisit
     */
    public DateOfVisit(String dateOfVisit) {
        requireNonNull(dateOfVisit);
        checkArgument(isValidDateOfVisit(dateOfVisit), MESSAGE_CONSTRAINTS);
        this.dateOfVisit = DateTimeParser.parseDateTime(dateOfVisit);
    }

    /**
     * Returns true if a given string is a valid date of visit
     */
    public static boolean isValidDateOfVisit(String test) {
        return DateTimeParser.parseDateTime(test) == null ? false : true;
    }

    @Override
    public String toString() {
        return DateTimeParser.outputDateTime(dateOfVisit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof DateOfVisit)) {
            return false;
        }

        DateOfVisit otherDateOfVisit = (DateOfVisit) other;
        return dateOfVisit.equals(otherDateOfVisit.dateOfVisit);
    }

    @Override
    public int hashCode() {
        return dateOfVisit.hashCode();
    }
}
