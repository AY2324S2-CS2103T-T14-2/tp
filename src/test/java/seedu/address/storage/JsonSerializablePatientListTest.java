package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.PatientList;
import seedu.address.testutil.TypicalPatients;

public class JsonSerializablePatientListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePatientListTest");
    private static final Path TYPICAL_PATIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalPatientAddressBook.json");
    private static final Path INVALID_PATIENT_FILE = TEST_DATA_FOLDER.resolve("invalidPatientAddressBook.json");
    private static final Path DUPLICATE_PATIENT_FILE = TEST_DATA_FOLDER.resolve("duplicatePatientAddressBook.json");

    @Test
    public void toModelType_typicalPatientsFile_success() throws Exception {
        JsonSerializablePatientList dataFromFile = JsonUtil.readJsonFile(TYPICAL_PATIENTS_FILE,
                JsonSerializablePatientList.class).get();
        PatientList patientListFromFile = dataFromFile.toModelType();
        PatientList typicalPatientsPatientList = TypicalPatients.getTypicalAddressBook();
        assertEquals(patientListFromFile, typicalPatientsPatientList);
    }

    @Test
    public void toModelType_invalidPatientFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePatientList dataFromFile = JsonUtil.readJsonFile(INVALID_PATIENT_FILE,
                JsonSerializablePatientList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePatients_throwsIllegalValueException() throws Exception {
        JsonSerializablePatientList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PATIENT_FILE,
                JsonSerializablePatientList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePatientList.MESSAGE_DUPLICATE_PATIENT,
                dataFromFile::toModelType);
    }

}
