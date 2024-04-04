package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.HOON;
import static seedu.address.testutil.TypicalPatients.IDA;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.PatientList;
import seedu.address.model.ReadOnlyPatientList;

public class JsonPatientListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPatientListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyPatientList> readAddressBook(String filePath) throws Exception {
        return new JsonPatientListStorage(Paths.get(filePath)).readPatientList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPatientAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidPatientAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPatientAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidAndValidPatientAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        PatientList original = getTypicalAddressBook();
        JsonPatientListStorage jsonAddressBookStorage = new JsonPatientListStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.savePatientList(original, filePath);
        ReadOnlyPatientList readBack = jsonAddressBookStorage.readPatientList(filePath).get();
        assertEquals(original, new PatientList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatient(HOON);
        original.removePatient(ALICE);
        jsonAddressBookStorage.savePatientList(original, filePath);
        readBack = jsonAddressBookStorage.readPatientList(filePath).get();
        assertEquals(original, new PatientList(readBack));

        // Save and read without specifying file path
        original.addPatient(IDA);
        jsonAddressBookStorage.savePatientList(original); // file path not specified
        readBack = jsonAddressBookStorage.readPatientList().get(); // file path not specified
        assertEquals(original, new PatientList(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyPatientList addressBook, String filePath) {
        try {
            new JsonPatientListStorage(Paths.get(filePath))
                    .savePatientList(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new PatientList(), null));
    }
}
