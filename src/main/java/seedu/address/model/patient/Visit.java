package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an instance of a Patient's visit.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Visit {

    private final Condition condition;
    private final Severity severity;
    private final DateOfVisit dateOfVisit;

    /**
     * Every field must be present and not null.
     */
    public Visit(Condition condition, Severity severity, DateOfVisit dateOfVisit) {
        requireAllNonNull(condition, severity, dateOfVisit);
        this.condition = condition;
        this.severity = severity;
        this.dateOfVisit = dateOfVisit;
    }

    public Condition getCondition() {
        return condition;
    }

    public Severity getSeverity() {
        return severity;
    }

    public DateOfVisit getDateOfVisit() {
        return dateOfVisit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("condition", condition)
                .add("severity", severity)
                .add("date of visit", dateOfVisit)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Visit)) {
            return false;
        }

        Visit otherVisit = (Visit) other;
        return condition.equals(otherVisit.condition)
                && severity.equals(otherVisit.severity)
                && dateOfVisit.equals(otherVisit.dateOfVisit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, severity, dateOfVisit);
    }

}
