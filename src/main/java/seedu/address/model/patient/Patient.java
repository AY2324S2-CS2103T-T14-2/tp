package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Patient in the list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final DateOfBirth dateOfBirth;
    private final Sex sex;

    // Data fields
    private final Address address;
    private final Appointment appointment;
    private final UniqueVisitList visits;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        visits = new UniqueVisitList();
    }

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, DateOfBirth dateOfBirth, Sex sex,
                   Appointment appointment) {
        requireAllNonNull(name, phone, email, address, dateOfBirth, sex, appointment);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.appointment = appointment;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    // Visit operations
    public ObservableList<Visit> getVisits() {
        return visits.asUnmodifiableObservableList();
    }

    public Visit getLatestVisit() {
        ObservableList<Visit> visits = getVisits();
        return visits.get(visits.size() - 1);
    }

    /**
     * Replaces the list of visits with {@code visits}.
     * {@code visits} must not contain duplicates.
     */
    public void setVisits(List<Visit> visits) {
        this.visits.setVisits(visits);
    }

    /**
     * Returns true if a visit with the same fields as {@code visit} exists for this patient.
     */
    public boolean hasVisit(Visit visit) {
        requireNonNull(visit);
        return visits.contains(visit);
    }

    /**
     * Adds a visit.
     * The visit must not already exist.
     */
    public void addVisit(Visit v) {
        visits.add(v);
    }

    /**
     * Replaces the given visit {@code target} with {@code editedVisit}.
     * {@code target} must exist.
     * {@code editedVisit} must not exist.
     */
    public void setVisit(Visit target, Visit editedVisit) {
        requireNonNull(editedVisit);

        visits.setVisit(target, editedVisit);
    }

    /**
     * Removes {@code visit} from this {@code Patient}.
     * {@code visit} must exist.
     */
    public void removeVisit(Visit visit) {
        visits.remove(visit);
    }

    /**
     * Returns true if both patients have the same name.
     * This defines a weaker notion of equality between two patients.
     */
    public boolean isSamePatient(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getName().equals(getName())
                && otherPatient.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return name.equals(otherPatient.name)
                && phone.equals(otherPatient.phone)
                && email.equals(otherPatient.email)
                && address.equals(otherPatient.address)
                && dateOfBirth.equals(otherPatient.dateOfBirth)
                && sex.equals(otherPatient.sex)
                && appointment.equals(otherPatient.appointment)
                && visits.equals(otherPatient.visits);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, dateOfBirth, sex, appointment, visits);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("date of birth", dateOfBirth)
                .add("sex", sex)
                .add("appointment", appointment)
                .add("visits", visits)
                .toString();
    }

}
