package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PatientList;
import seedu.address.model.ReadOnlyPatientList;
import seedu.address.model.patient.Patient;

/**
 * An Immutable PatientList that is serializable to JSON format.
 */
@JsonRootName(value = "patientlist")
class JsonSerializablePatientList {

    public static final String MESSAGE_DUPLICATE_PATIENT = "Patient list contains duplicate patient(s).";

    private final List<JsonAdaptedPatient> patients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePatientList} with the given patients.
     */
    @JsonCreator
    public JsonSerializablePatientList(@JsonProperty("patients") List<JsonAdaptedPatient> patients) {
        this.patients.addAll(patients);
    }

    /**
     * Converts a given {@code ReadOnlyPatientList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePatientList}.
     */
    public JsonSerializablePatientList(ReadOnlyPatientList source) {
        patients.addAll(source.getPatientList().stream().map(JsonAdaptedPatient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this patient list into the model's {@code PatientList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PatientList toModelType() throws IllegalValueException {
        PatientList patientList = new PatientList();
        for (JsonAdaptedPatient jsonAdaptedPatient : patients) {
            Patient patient = jsonAdaptedPatient.toModelType();
            if (patientList.hasPatient(patient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PATIENT);
            }
            patientList.addPatient(patient);
        }
        return patientList;
    }

}
