package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatientAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteByIndexCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

//    @Test
//    public void execute_validIndexUnfilteredList_success() {
//        Patient patientToDelete = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
//        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(INDEX_FIRST_PATIENT);
//
//        String expectedMessage = String.format(DeleteByIndexCommand.MESSAGE_DELETE_PATIENT_SUCCESS,
//                Messages.format(patientToDelete));
//
//        ModelManager expectedModel = new ModelManager(model.getPatientList(), new UserPrefs());
//        expectedModel.deletePatient(patientToDelete);
//
//        assertCommandSuccess(deleteByIndexCommand, model, expectedMessage, expectedModel);
//    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(outOfBoundIndex);

        assertCommandFailure(deleteByIndexCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

//    @Test
//    public void execute_validIndexFilteredList_success() {
//        showPatientAtIndex(model, INDEX_FIRST_PATIENT);
//
//        Patient patientToDelete = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
//        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(INDEX_FIRST_PATIENT);
//
//        String expectedMessage = String.format(DeleteByIndexCommand.MESSAGE_DELETE_PATIENT_SUCCESS,
//                Messages.format(patientToDelete));
//
//        Model expectedModel = new ModelManager(model.getPatientList(), new UserPrefs());
//        expectedModel.deletePatient(patientToDelete);
//        showNoPatient(expectedModel);
//
//        assertCommandSuccess(deleteByIndexCommand, model, expectedMessage, expectedModel);
//    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Index outOfBoundIndex = INDEX_SECOND_PATIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPatientList().getPatientList().size());

        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(outOfBoundIndex);

        assertCommandFailure(deleteByIndexCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteByIndexCommand deleteFirstCommand = new DeleteByIndexCommand(INDEX_FIRST_PATIENT);
        DeleteByIndexCommand deleteSecondCommand = new DeleteByIndexCommand(INDEX_SECOND_PATIENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteByIndexCommand deleteFirstCommandCopy = new DeleteByIndexCommand(INDEX_FIRST_PATIENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(targetIndex);
        String expected = DeleteByIndexCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteByIndexCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPatient(Model model) {
        model.updateFilteredPatientList(p -> false);

        assertTrue(model.getFilteredPatientList().isEmpty());
    }
}
