package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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


public class AddVisitCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index validIndex = Index.fromOneBased(model.getFilteredPatientList().size());
    private Index invalidIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
    private Visit testVisit = new VisitBuilder().build();
    private Name testName = new Name("Bob");
    private Phone testPhone = new Phone("98765432");
    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(validIndex, null));
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(null, testVisit));
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(null, testPhone, testVisit));
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(testName, null, testVisit));
        assertThrows(NullPointerException.class, () -> new AddVisitCommand(testName, testPhone, null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        AddVisitCommand command = new AddVisitCommand(invalidIndex, testVisit);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX, ()
                -> command.execute(model));
    }

    @Test
    public void execute_invalidPatient_throwsCommandException() {
        Model model = new ModelManager(new PatientList(), new UserPrefs());
        Patient testPatient = new PatientBuilder().build();
        AddVisitCommand command = new AddVisitCommand(testPatient.getName(), testPatient.getPhone(), testVisit);
        assertThrows(CommandException.class, AddVisitCommand.MESSAGE_PATIENT_NOT_FOUND, () -> command.execute(model));
    }

    @Test
    public void execute_duplicateVisit_throwsCommandException() {
        Model model = new ModelManager(new PatientList(), new UserPrefs());
        Patient testPatient = new PatientBuilder().build();
        testPatient.addVisit(testVisit);
        model.addPatient(testPatient);
        AddVisitCommand command = new AddVisitCommand(testPatient.getName(), testPatient.getPhone(), testVisit);
        assertThrows(CommandException.class, AddVisitCommand.MESSAGE_DUPLICATE_VISIT, () -> command.execute(model));
        AddVisitCommand alternateCommand = new AddVisitCommand(Index.fromOneBased(1), testVisit);
        assertThrows(CommandException.class, AddVisitCommand.MESSAGE_DUPLICATE_VISIT, ()
                -> alternateCommand.execute(model));
    }

    @Test
    public void execute_validVisit_addSuccessful() throws Exception {
        Model model = new ModelManager(new PatientList(), new UserPrefs());
        Patient testPatient = new PatientBuilder().build();
        model.addPatient(testPatient);
        AddVisitCommand command = new AddVisitCommand(testPatient.getName(), testPatient.getPhone(), testVisit);
        CommandResult commandResult = command.execute(model);

        assertEquals(AddVisitCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertTrue(testPatient.hasVisit(testVisit));
    }
}
