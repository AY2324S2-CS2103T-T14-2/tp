package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.VisitBuilder;

public class VisitTest {
    private final Visit testVisit = new VisitBuilder().build();
    @Test
    public void equals() {
        // same values -> returns true
        Visit copyVisit = new VisitBuilder(testVisit).build();
        assertTrue(testVisit.equals(copyVisit));

        // same object -> returns true
        assertTrue(testVisit.equals(testVisit));

        // null -> returns false
        assertFalse(testVisit.equals(null));

        // different type -> returns false
        assertFalse(testVisit.equals(5));

        // different condition -> returns false
        assertFalse(testVisit.equals(new VisitBuilder(testVisit).withCondition("other").build()));

        // different severity -> returns false
        assertFalse(testVisit.equals(new VisitBuilder(testVisit).withSeverity("High").build()));

        // different date of visit -> returns false
        assertFalse(testVisit.equals(new VisitBuilder(testVisit).withDateOfVisit("1/1/2002").build()));
    }

    @Test
    public void toStringMethod() {
        String expected = Visit.class.getCanonicalName() + "{condition=" + testVisit.getCondition()
                + ", severity=" + testVisit.getSeverity()
                + ", date of visit=" + testVisit.getDateOfVisit() + "}";
        assertEquals(expected, testVisit.toString());
    }
}
