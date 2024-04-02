package seedu.address.testutil;

import seedu.address.model.PatientList;
import seedu.address.model.patient.Patient;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code PatientList ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private PatientList patientList;

    public AddressBookBuilder() {
        patientList = new PatientList();
    }

    public AddressBookBuilder(PatientList patientList) {
        this.patientList = patientList;
    }

    /**
     * Adds a new {@code Patient} to the {@code PatientList} that we are building.
     */
    public AddressBookBuilder withPerson(Patient patient) {
        patientList.addPatient(patient);
        return this;
    }

    public PatientList build() {
        return patientList;
    }
}
