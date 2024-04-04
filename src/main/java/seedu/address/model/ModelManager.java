package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;

/**
 * Represents the in-memory model of the patient list data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final PatientList patientList;
    private final UserPrefs userPrefs;
    private final FilteredList<Patient> filteredPatients;

    /**
     * Initializes a ModelManager with the given patientList and userPrefs.
     */
    public ModelManager(ReadOnlyPatientList patientList, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(patientList, userPrefs);

        logger.fine("Initializing with patient list: " + patientList + " and user prefs " + userPrefs);

        this.patientList = new PatientList(patientList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPatients = new FilteredList<>(this.patientList.getPatientList());
    }

    public ModelManager() {
        this(new PatientList(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getPatientListFilePath() {
        return userPrefs.getPatientListFilePath();
    }

    @Override
    public void setPatientListFilePath(Path patientListFilePath) {
        requireNonNull(patientListFilePath);
        userPrefs.setPatientListFilePath(patientListFilePath);
    }

    //=========== PatientList ================================================================================

    @Override
    public void setPatientList(ReadOnlyPatientList patientList) {
        this.patientList.resetData(patientList);
    }

    @Override
    public ReadOnlyPatientList getPatientList() {
        return patientList;
    }

    @Override
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return patientList.hasPatient(patient);
    }

    @Override
    public boolean hasPatient(Name name, Phone phone) {
        requireAllNonNull(name, phone);
        return patientList.hasPatient(name, phone);
    }

    @Override
    public void deletePatient(Patient target) {
        patientList.removePatient(target);
    }

    @Override
    public void addPatient(Patient patient) {
        patientList.addPatient(patient);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
    }

    @Override
    public void setPatient(Patient target, Patient editedPatient) {
        requireAllNonNull(target, editedPatient);

        patientList.setPatient(target, editedPatient);
    }

    @Override
    public Patient getPatient(Name name, Phone phone) {
        return patientList.getPatient(name, phone);
    }

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the filtered list of {@code Patient}.
     */
    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return filteredPatients;
    }

    @Override
    public void updateFilteredPatientList(Predicate<Patient> predicate) {
        requireNonNull(predicate);
        filteredPatients.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return patientList.equals(otherModelManager.patientList)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPatients.equals(otherModelManager.filteredPatients);
    }

}
