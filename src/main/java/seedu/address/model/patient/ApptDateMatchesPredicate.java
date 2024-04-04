package seedu.address.model.patient;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.patient.Appointment;
public class ApptDateMatchesPredicate implements Predicate<Patient> {
    private final LocalDate apptDate;

    public ApptDateMatchesPredicate(LocalDate apptDate) {
        this.apptDate = apptDate;
    }

    @Override
    public boolean test(Patient patient) {
        LocalDate ptApptDate = patient.getAppointment().appointment;
        if (ptApptDate == null) {
            return false;
        }
        return ptApptDate.isBefore(this.apptDate) || ptApptDate.isEqual(this.apptDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ApptDateMatchesPredicate)) {
            return false;
        }

        ApptDateMatchesPredicate otherApptMatchesPredicate = (ApptDateMatchesPredicate) other;
        return apptDate.equals(otherApptMatchesPredicate.apptDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("appointment", apptDate).toString();
    }
}
