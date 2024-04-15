package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ConditionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Condition(null));
    }

    @Test
    public void constructor_invalidCondition_throwsIllegalArgumentException() {
        String invalidCondition = "";
        assertThrows(IllegalArgumentException.class, () -> new Condition(invalidCondition));
    }

    @Test
    public void isValidCondition() {
        // null Condition
        assertThrows(NullPointerException.class, () -> Condition.isValidCondition(null));

        // invalid Condition
        assertFalse(Condition.isValidCondition("")); // empty string
        assertFalse(Condition.isValidCondition(" ")); // spaces only

        // valid Condition
        assertTrue(Condition.isValidCondition("Fever"));
        assertTrue(Condition.isValidCondition("F")); // one character
        assertTrue(Condition.isValidCondition("Pneumonoultramicroscopicsilicovolcanoconiosis")); // long Condition
    }

    @Test
    public void equals() {
        Condition condition = new Condition("Valid Condition");

        // same values -> returns true
        assertTrue(condition.equals(new Condition("Valid Condition")));

        // same object -> returns true
        assertTrue(condition.equals(condition));

        // null -> returns false
        assertFalse(condition.equals(null));

        // different types -> returns false
        assertFalse(condition.equals(5.0f));

        // different values -> returns false
        assertFalse(condition.equals(new Condition("Other Valid Condition")));
    }
}
