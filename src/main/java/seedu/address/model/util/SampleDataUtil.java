package seedu.address.model.util;

import seedu.address.model.PatientList;
import seedu.address.model.ReadOnlyPatientList;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Sex;

/**
 * Contains utility methods for populating {@code PatientList} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                new DateOfBirth("25/2/2024"), new Sex("Male")),
            new Patient(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new DateOfBirth("2024-2-25"), new Sex("Female")),
            new Patient(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new DateOfBirth("12/12/2024"), new Sex("Female")),
            new Patient(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new DateOfBirth("25/2/2024"), new Sex("Male")),
            new Patient(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                new DateOfBirth("25/2/2024"), new Sex("Male")),
            new Patient(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                new DateOfBirth("25/2/2024"), new Sex("Male")),
        };
    }

    public static ReadOnlyPatientList getSamplePatientList() {
        PatientList sampleList = new PatientList();
        for (Patient samplePatient : getSamplePatients()) {
            sampleList.addPatient(samplePatient);
        }
        return sampleList;
    }
}
