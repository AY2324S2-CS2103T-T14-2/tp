package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedVisit.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Condition;
import seedu.address.model.patient.DateOfVisit;
import seedu.address.model.patient.Severity;
import seedu.address.model.patient.Visit;

public class JsonAdaptedVisitTest {

    private static final String INVALID_CONDITION = " ";
    private static final String INVALID_SEVERITY = "low";
    private static final String INVALID_DATEOFVISIT = "24/2022";

    private static final String VALID_CONDITION = "Fever";
    private static final String VALID_DATEOFVISIT = "25/2/2024";
    private static final String VALID_SEVERITY = "Low";
    private static final Visit VALID_VISIT = new Visit(new Condition(VALID_CONDITION), new Severity(VALID_SEVERITY),
            new DateOfVisit(VALID_DATEOFVISIT));

    @Test
    public void toModelType_validVisitDetails_returnsVisit() throws Exception {
        JsonAdaptedVisit visit = new JsonAdaptedVisit(VALID_VISIT);
        assertEquals(VALID_VISIT, visit.toModelType());
    }

    @Test
    public void toModelType_invalidCondition_throwsIllegalValueException() {
        JsonAdaptedVisit visit =
                new JsonAdaptedVisit(INVALID_CONDITION, VALID_SEVERITY, VALID_DATEOFVISIT);
        String expectedMessage = Condition.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullCondition_throwsIllegalValueException() {
        JsonAdaptedVisit visit =
                new JsonAdaptedVisit(null, VALID_SEVERITY, VALID_DATEOFVISIT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Condition.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidDateOfVisit_throwsIllegalValueException() {
        JsonAdaptedVisit visit =
                new JsonAdaptedVisit(VALID_CONDITION, VALID_SEVERITY, INVALID_DATEOFVISIT);
        String expectedMessage = DateOfVisit.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullDateOfVisit_throwsIllegalValueException() {
        JsonAdaptedVisit visit =
                new JsonAdaptedVisit(VALID_CONDITION, VALID_SEVERITY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfVisit.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_invalidSeverity_throwsIllegalValueException() {
        JsonAdaptedVisit visit =
                new JsonAdaptedVisit(VALID_CONDITION, INVALID_SEVERITY, VALID_DATEOFVISIT);
        String expectedMessage = Severity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }

    @Test
    public void toModelType_nullSeverity_throwsIllegalValueException() {
        JsonAdaptedVisit visit =
                new JsonAdaptedVisit(VALID_CONDITION, null, VALID_DATEOFVISIT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Severity.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, visit::toModelType);
    }
}
