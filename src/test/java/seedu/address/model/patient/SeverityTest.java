package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SeverityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Severity(null));
    }

    @Test
    public void constructor_invalidSeverity_throwsIllegalArgumentException() {
        String invalidSeverity = "";
        assertThrows(IllegalArgumentException.class, () -> new Severity(invalidSeverity));
    }

    @Test
    public void isValidSeverity() {
        // null Severity
        assertThrows(NullPointerException.class, () -> Severity.isValidSeverity(null));

        // invalid Severity
        assertFalse(Severity.isValidSeverity("")); // empty string
        assertFalse(Severity.isValidSeverity(" ")); // spaces only
        assertFalse(Severity.isValidSeverity("akldjkldf")); // random string
        assertFalse(Severity.isValidSeverity("LOW")); // similar to valid string

        // valid Severity
        assertTrue(Severity.isValidSeverity("Low"));
        assertTrue(Severity.isValidSeverity("High"));
    }

    @Test
    public void equals() {
        Severity severity = new Severity("High");

        // same values -> returns true
        assertTrue(severity.equals(new Severity("High")));

        // same object -> returns true
        assertTrue(severity.equals(severity));

        // null -> returns false
        assertFalse(severity.equals(null));

        // different types -> returns false
        assertFalse(severity.equals(5.0f));

        // different values -> returns false
        assertFalse(severity.equals(new Severity("Low")));
    }
}
