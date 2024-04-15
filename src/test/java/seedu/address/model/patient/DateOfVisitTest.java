package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateOfVisitTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateOfVisit(null));
    }

    @Test
    public void constuctor_invalidDateOfVisit_throwsIllegalArgumentException() {
        String invalidDateOfVisit = "";
        assertThrows(IllegalArgumentException.class, () -> new DateOfVisit(invalidDateOfVisit));
    }

    @Test
    public void isValidDateOfVisit() {
        // null date of visit
        assertThrows(NullPointerException.class, () -> DateOfVisit.isValidDateOfVisit(null));

        // invalid date of visit
        assertFalse(DateOfVisit.isValidDateOfVisit("")); // empty string
        assertFalse(DateOfVisit.isValidDateOfVisit(" ")); // spaces only
        assertFalse(DateOfVisit.isValidDateOfVisit("wdalsjdaj")); // random string
        assertFalse(DateOfVisit.isValidDateOfVisit("12/2024")); // without date
        assertFalse(DateOfVisit.isValidDateOfVisit("245/2/2024")); // invalid date
        assertFalse(DateOfVisit.isValidDateOfVisit("25/24/2024")); // invalid month
        assertFalse(DateOfVisit.isValidDateOfVisit("25/2/22")); // invalid year

        // valid date of visit
        assertTrue(DateOfVisit.isValidDateOfVisit("25/2/2024")); // valid first input foramt
        assertTrue(DateOfVisit.isValidDateOfVisit("2024-2-24")); // valid second input format
    }

    @Test
    public void equals() {
        DateOfVisit dateOfVisit = new DateOfVisit("2024-2-5");

        // same values -> returns true
        assertTrue(dateOfVisit.equals(new DateOfVisit("2024-2-5")));

        // same values -> returns true
        assertTrue(dateOfVisit.equals(new DateOfVisit("2024-02-05")));

        // another acceptable input format -> returns true
        assertTrue(dateOfVisit.equals(new DateOfVisit("5/2/2024")));

        //another acceptable input format -> returns true
        assertTrue(dateOfVisit.equals(new DateOfVisit("05/02/2024")));

        // same object -> returns true
        assertTrue(dateOfVisit.equals(dateOfVisit));

        // null -> returns false
        assertFalse(dateOfVisit.equals(null));

        // different types -> returns false
        assertFalse(dateOfVisit.equals(5.0f));

        // different values -> returns false
        assertFalse(dateOfVisit.equals(new DateOfVisit("2026-12-5")));
    }
}
