package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Condition;
import seedu.address.model.patient.DateOfVisit;
import seedu.address.model.patient.Severity;
import seedu.address.model.patient.Visit;

/**
 * Jackson-friendly version of {@link Visit}.
 */
class JsonAdaptedVisit {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Visit's %s field is missing!";

    private final String condition;
    private final String severity;
    private final String dateOfVisit;

    /**
     * Constructs a {@code JsonAdaptedVisit} with the given visit details.
     */
    @JsonCreator
    public JsonAdaptedVisit(@JsonProperty("condition") String condition, @JsonProperty("severity") String severity,
                              @JsonProperty("dateOfVisit") String dateOfVisit) {
        this.condition = condition;
        this.severity = severity;
        this.dateOfVisit = dateOfVisit;
    }

    /**
     * Converts a given {@code Visit} into this class for Jackson use.
     */
    public JsonAdaptedVisit(Visit source) {
        condition = source.getCondition().value;
        severity = source.getSeverity().severity.getLabel();
        dateOfVisit = source.getDateOfVisit().dateOfVisit.toString();
    }

    /**
     * Converts this Jackson-friendly adapted visit object into the model's {@code Visit} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted visit.
     */
    public Visit toModelType() throws IllegalValueException {

        if (condition == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Condition.class.getSimpleName()));
        }
        if (!Condition.isValidCondition(condition)) {
            throw new IllegalValueException(Condition.MESSAGE_CONSTRAINTS);
        }
        final Condition modelCondition = new Condition(condition);

        if (severity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Severity.class.getSimpleName()));
        }
        if (!Severity.isValidSeverity(severity)) {
            throw new IllegalValueException(Severity.MESSAGE_CONSTRAINTS);
        }
        final Severity modelSeverity = new Severity(severity);


        if (dateOfVisit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateOfVisit.class.getSimpleName()));
        }
        if (!DateOfVisit.isValidDateOfVisit(dateOfVisit)) {
            throw new IllegalValueException(DateOfVisit.MESSAGE_CONSTRAINTS);
        }
        final DateOfVisit modelDateOfVisit = new DateOfVisit(dateOfVisit);

        return new Visit(modelCondition, modelSeverity, modelDateOfVisit);
    }

}

