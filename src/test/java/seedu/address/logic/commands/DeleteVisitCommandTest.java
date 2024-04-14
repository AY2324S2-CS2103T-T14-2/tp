package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientList;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Visit;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.VisitBuilder;

public class DeleteVisitCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index validIndex = Index.fromOneBased(model.getFilteredPatientList().size());
    private Index invalidIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
    private Visit testVisit = new VisitBuilder().build();
    private Name testName = new Name("Bob");
    private Phone testPhone = new Phone("98765432");

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteVisitCommand(testName, null));
        assertThrows(NullPointerException.class, () -> new DeleteVisitCommand(null, testPhone));
        assertThrows(NullPointerException.class, () -> new DeleteVisitCommand(null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        DeleteVisitCommand command = new DeleteVisitCommand(invalidIndex);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX, ()
                -> command.execute(model));
    }

    @Test
    public void execute_invalidPatient_throwsCommandException() {
        Model model = new ModelManager(new PatientList(), new UserPrefs());
        Patient testPatient = new PatientBuilder().build();
        DeleteVisitCommand command = new DeleteVisitCommand(testPatient.getName(), testPatient.getPhone());
        assertThrows(CommandException.class, DeleteVisitCommand.MESSAGE_PATIENT_NOT_FOUND, ()
                -> command.execute(model));
    }

    @Test
    public void execute_noVisit_throwsCommandException() {
        Model model = new ModelManager(new PatientList(), new UserPrefs());
        Patient testPatient = new PatientBuilder().build();
        model.addPatient(testPatient);
        DeleteVisitCommand command = new DeleteVisitCommand(testPatient.getName(), testPatient.getPhone());
        assertThrows(CommandException.class, DeleteVisitCommand.MESSAGE_NO_VISIT, () -> command.execute(model));
        DeleteVisitCommand alternateCommand = new DeleteVisitCommand(Index.fromOneBased(1));
        assertThrows(CommandException.class, DeleteVisitCommand.MESSAGE_NO_VISIT, ()
                -> alternateCommand.execute(model));
    }

    @Test
    public void execute_validVisit_addSuccessful() throws Exception {
        Model model = new ModelManager(new PatientList(), new UserPrefs());
        Patient testPatient = new PatientBuilder().build();
        testPatient.addVisit(testVisit);
        model.addPatient(testPatient);
        DeleteVisitCommand command = new DeleteVisitCommand(testPatient.getName(), testPatient.getPhone());
        CommandResult commandResult = command.execute(model);

        assertEquals(DeleteVisitCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertFalse(testPatient.hasVisit(testVisit));
        assertTrue(testPatient.getVisits().isEmpty());
    }
}
