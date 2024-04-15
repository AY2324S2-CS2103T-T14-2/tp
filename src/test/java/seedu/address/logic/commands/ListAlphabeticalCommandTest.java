package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatientAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPatients.DANIEL;
import static seedu.address.testutil.TypicalPatients.ELLE;
import static seedu.address.testutil.TypicalPatients.FIONA;
import static seedu.address.testutil.TypicalPatients.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientList;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;



/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAlphabeticalCommand.
 */
public class ListAlphabeticalCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        List<Patient> patientList = new ArrayList<>(Arrays.asList(GEORGE, CARL, DANIEL, ALICE, FIONA, BENSON, ELLE));
        List<Patient> expectedPatientList =
                new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
        PatientList patients = new PatientList();
        patients.setPatients(patientList);
        model = new ModelManager(patients, new UserPrefs());
        PatientList expectedPatients = new PatientList();
        expectedPatients.setPatients(expectedPatientList);
        expectedModel = new ModelManager(expectedPatients, new UserPrefs());
    }

    @Test
    void testExecute() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);
        assertCommandSuccess(new ListAlphabeticalCommand(), model,
                ListAlphabeticalCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
