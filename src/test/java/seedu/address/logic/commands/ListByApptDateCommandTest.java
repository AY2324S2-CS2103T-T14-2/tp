package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientList;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListByApptDateCommandTest {

    private Model model;
    private Model expectedModel;


    @Test
    void testExecute() {
        List<Patient> patientList = new ArrayList<>(
                Arrays.asList(GEORGE, CARL, DANIEL, ALICE, FIONA, BENSON, ELLE));
        List<Patient> expectedPatientList = new ArrayList<>(
                Arrays.asList(BENSON, DANIEL, ELLE, GEORGE, ALICE, CARL, FIONA));
        PatientList patients = new PatientList();
        patients.setPatients(patientList);
        model = new ModelManager(patients, new UserPrefs());
        PatientList expectedPatients = new PatientList();
        expectedPatients.setPatients(expectedPatientList);
        expectedModel = new ModelManager(expectedPatients, new UserPrefs());

        //String expectedMessage = String.format(ListByApptDateCommand.MESSAGE_SUCCESS, 0);
        ListByApptDateCommand command = new ListByApptDateCommand();
        //model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        try {
            CommandResult commandResult = command.execute(model);
            assertEquals(commandResult, new CommandResult(ListByApptDateCommand.MESSAGE_SUCCESS));
            //assertEquals(expectedPatientList, new ArrayList<Patient>(model.getFilteredPatientList()));
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
        //assertCommandSuccess(command, model, expectedMessage, expectedModel);
        //assertEquals(expectedPatientList, model.getFilteredPatientList());
    }
}
