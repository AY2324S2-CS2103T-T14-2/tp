package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AppointmentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Appointment(null));
    }

    @Test
    public void constructor_invalidAppointment_throwsIllegalArgumentException() {
        String invalidAppointment = "wdasfsdf";
        assertThrows(IllegalArgumentException.class, () -> new Appointment(invalidAppointment));
    }

    @Test
    public void isValidAppointment() {
        // invalid appointment
        assertFalse(Appointment.isValidAppointment(" ")); // spaces only
        assertFalse(Appointment.isValidAppointment("wdalsjdaj")); // random string
        assertFalse(Appointment.isValidAppointment("12/2024")); // without date
        assertFalse(Appointment.isValidAppointment("245/2/2024")); // invalid date
        assertFalse(Appointment.isValidAppointment("25/24/2024")); // invalid month
        assertFalse(Appointment.isValidAppointment("25/2/22")); // invalid year

        // valid appointment
        assertTrue(Appointment.isValidAppointment("")); // empty string
        assertTrue(Appointment.isValidAppointment("25/2/2024")); // valid first input foramt
        assertTrue(Appointment.isValidAppointment("2024-2-24")); // valid second input format
    }

    @Test
    public void equals() {
        Appointment appointment = new Appointment("2024-2-5");

        // same values -> returns true
        assertTrue(appointment.equals(new Appointment("2024-2-5")));

        // same values -> returns true
        assertTrue(appointment.equals(new Appointment("2024-02-05")));

        // another acceptable input format -> returns true
        assertTrue(appointment.equals(new Appointment("5/2/2024")));

        //another acceptable input format -> returns true
        assertTrue(appointment.equals(new Appointment("05/02/2024")));

        // same object -> returns true
        assertTrue(appointment.equals(appointment));

        // null -> returns false
        assertFalse(appointment.equals(null));

        // different types -> returns false
        assertFalse(appointment.equals(5.0f));

        // different values -> returns false
        assertFalse(appointment.equals(new Appointment("2026-12-5")));
    }
}
