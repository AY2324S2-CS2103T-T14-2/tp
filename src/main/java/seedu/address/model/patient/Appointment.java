package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

import seedu.address.logic.parser.DateTimeParser;

/**
 * Represents a Patient's next appointment date
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointment(String)}
 */
public class Appointment {
    public static final String MESSAGE_CONSTRAINTS =
            "Appointment should only contain numeric values in dd/MM/yyyy or yyyy-MM-dd format, "
                    + "and it should not be left blank";

    public final LocalDate appointment;

    /**
     * Constructs a {@code Appointment}
     *
     * @param appointment
     */
    public Appointment(String appointment) {
        requireNonNull(appointment);
        checkArgument(isValidAppointment(appointment), MESSAGE_CONSTRAINTS);
        if (!appointment.isEmpty()) {
            this.appointment = DateTimeParser.parseDateTime(appointment);
        } else {
            this.appointment = null;
        }
    }

    /**
     * Returns true if a given string is a valid appointment
     */
    public static boolean isValidAppointment(String test) {
        if (test.isEmpty()) {
            return true;
        } else if (!test.isEmpty()) {
            return DateTimeParser.parseDateTime(test) == null ? false : true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (appointment != null) {
            return DateTimeParser.outputDateTime(appointment);
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        if (appointment == null && otherAppointment.appointment == null) {
            return true;
        }
        if (appointment != null && otherAppointment.appointment != null) {
            return appointment.equals(otherAppointment.appointment);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return appointment.hashCode();
    }
}
