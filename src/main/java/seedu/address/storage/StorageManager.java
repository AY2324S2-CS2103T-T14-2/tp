package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyPatientList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of PatientList data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PatientListStorage patientListStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code PatientListStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(PatientListStorage patientListStorage, UserPrefsStorage userPrefsStorage) {
        this.patientListStorage = patientListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ PatientList methods ==============================

    @Override
    public Path getPatientListFilePath() {
        return patientListStorage.getPatientListFilePath();
    }

    @Override
    public Optional<ReadOnlyPatientList> readPatientList() throws DataLoadingException {
        return readPatientList(patientListStorage.getPatientListFilePath());
    }

    @Override
    public Optional<ReadOnlyPatientList> readPatientList(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return patientListStorage.readPatientList(filePath);
    }

    @Override
    public void savePatientList(ReadOnlyPatientList patientList) throws IOException {
        savePatientList(patientList, patientListStorage.getPatientListFilePath());
    }

    @Override
    public void savePatientList(ReadOnlyPatientList patientList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        patientListStorage.savePatientList(patientList, filePath);
    }

}
