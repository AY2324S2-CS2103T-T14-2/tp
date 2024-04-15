package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;


class ApptDateMatchesPredicateTest {


    @Test
    void testEquals() {
        String firstApptDate = "01/02/2024";
        LocalDate firstAppt = null;
        String firstApptDate2 = "01/02/2024";
        LocalDate firstAppt2 = null;

        String secondApptDate = "2024-2-1";
        LocalDate secondAppt = null;
        try {
            firstAppt = ParserUtil.parseAppointment(firstApptDate).appointment;
            firstAppt2 = ParserUtil.parseAppointment(firstApptDate2).appointment;
            secondAppt = ParserUtil.parseAppointment(secondApptDate).appointment;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        ApptDateMatchesPredicate firstPredicate = new ApptDateMatchesPredicate(firstAppt);
        ApptDateMatchesPredicate firstPredicate2 = new ApptDateMatchesPredicate(firstAppt2);
        ApptDateMatchesPredicate secondPredicate = new ApptDateMatchesPredicate(secondAppt);
        assertTrue(firstPredicate.equals(firstPredicate2));
        assertTrue(firstPredicate.equals(secondPredicate));
        assertTrue(firstPredicate2.equals(secondPredicate));
    }

    @Test
    void testNotEquals() {
        String firstApptDate = "01/02/2024";
        LocalDate firstAppt = null;

        String secondApptDate = "01/01/2024";
        LocalDate secondAppt = null;
        try {
            firstAppt = ParserUtil.parseAppointment(firstApptDate).appointment;
            secondAppt = ParserUtil.parseAppointment(secondApptDate).appointment;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        ApptDateMatchesPredicate firstPredicate = new ApptDateMatchesPredicate(firstAppt);
        ApptDateMatchesPredicate secondPredicate = new ApptDateMatchesPredicate(secondAppt);
        assertFalse(firstPredicate.equals(secondPredicate));
    }
}
