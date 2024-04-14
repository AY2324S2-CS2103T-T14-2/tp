package seedu.address.testutil;

import seedu.address.model.patient.Condition;
import seedu.address.model.patient.DateOfVisit;
import seedu.address.model.patient.Severity;
import seedu.address.model.patient.Visit;

/**
 * A utility class to help with building Visit objects.
 */
public class VisitBuilder {
    public static final String DEFAULT_DATEOFVISIT = "25/2/2025";
    public static final String DEFAULT_SEVERITY = "Low";
    public static final String DEFAULT_CONDITION = "Fever";

    private DateOfVisit dateOfVisit;
    private Severity severity;
    private Condition condition;

    /**
     * Creates a {@code VisitBuilder} with the default details.
     */
    public VisitBuilder() {
        condition = new Condition(DEFAULT_CONDITION);
        dateOfVisit = new DateOfVisit(DEFAULT_DATEOFVISIT);
        severity = new Severity(DEFAULT_SEVERITY);
    }

    /**
     * Initializes the VisitBuilder with the data of {@code visitToCopy}.
     */
    public VisitBuilder(Visit visitToCopy) {
        condition = visitToCopy.getCondition();
        dateOfVisit = visitToCopy.getDateOfVisit();
        severity = visitToCopy.getSeverity();
    }

    /**
     * Sets the {@code Condition} of the {@code Visit} that we are building.
     */
    public VisitBuilder withCondition(String condition) {
        this.condition = new Condition(condition);
        return this;
    }

    /**
     * Sets the {@code DateOfVisit} of the {@Code Visit} that we are building.
     */
    public VisitBuilder withDateOfVisit(String dateOfVisit) {
        this.dateOfVisit = new DateOfVisit(dateOfVisit);
        return this;
    }

    /**
     * Sets the {@code Severity} of the {@Cose Visit} that we are building.
     */
    public VisitBuilder withSeverity(String severity) {
        this.severity = new Severity(severity);
        return this;
    }

    public Visit build() {
        return new Visit(condition, severity, dateOfVisit);
    }
}
