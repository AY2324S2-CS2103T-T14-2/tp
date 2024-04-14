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
import seedu.address.testutil.EditVisitDescriptorBuilder;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.VisitBuilder;

public class EditVisitCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index validIndex = Index.fromOneBased(model.getFilteredPatientList().size());
    private Index invalidIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
    private Visit testVisit = new VisitBuilder().build();
    private EditVisitCommand.EditVisitDescriptor testEditVisitDescriptor =
            new EditVisitDescriptorBuilder(testVisit).build();
    private Name testName = new Name("Bob");
    private Phone testPhone = new Phone("98765432");

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditVisitCommand(validIndex, null));
        assertThrows(NullPointerException.class, () -> new EditVisitCommand(null, testEditVisitDescriptor));
        assertThrows(NullPointerException.class, () -> new EditVisitCommand(null, testPhone,
                testEditVisitDescriptor));
        assertThrows(NullPointerException.class, () -> new EditVisitCommand(testName, null,
                testEditVisitDescriptor));
        assertThrows(NullPointerException.class, () -> new EditVisitCommand(testName, testPhone, null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        EditVisitCommand command = new EditVisitCommand(invalidIndex, testEditVisitDescriptor);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX, ()
                -> command.execute(model));
    }

    @Test
    public void execute_invalidPatient_throwsCommandException() {
        Model model = new ModelManager(new PatientList(), new UserPrefs());
        Patient testPatient = new PatientBuilder().build();
        EditVisitCommand command = new EditVisitCommand(testPatient.getName(), testPatient.getPhone(),
                testEditVisitDescriptor);
        assertThrows(CommandException.class, EditVisitCommand.MESSAGE_PATIENT_NOT_FOUND, () -> command.execute(model));
    }

    @Test
    public void execute_noVisit_throwsCommandException() {
        Model model = new ModelManager(new PatientList(), new UserPrefs());
        Patient testPatient = new PatientBuilder().build();
        model.addPatient(testPatient);
        EditVisitCommand command = new EditVisitCommand(testPatient.getName(), testPatient.getPhone(),
                testEditVisitDescriptor);
        assertThrows(CommandException.class, EditVisitCommand.MESSAGE_NO_VISIT, () -> command.execute(model));
        EditVisitCommand alternateCommand = new EditVisitCommand(Index.fromOneBased(1),
                testEditVisitDescriptor);
        assertThrows(CommandException.class, EditVisitCommand.MESSAGE_NO_VISIT, () -> alternateCommand.execute(model));
    }

    @Test
    public void execute_duplicateVisit_throwsCommandException() {
        Model model = new ModelManager(new PatientList(), new UserPrefs());
        Patient testPatient = new PatientBuilder().build();
        testPatient.addVisit(testVisit);
        Visit visitToEdit = new VisitBuilder().withCondition("toEdit").withSeverity("Low").build();
        testPatient.addVisit(visitToEdit);
        model.addPatient(testPatient);
        EditVisitCommand command = new EditVisitCommand(testPatient.getName(), testPatient.getPhone(),
                testEditVisitDescriptor);
        assertThrows(CommandException.class, EditVisitCommand.MESSAGE_DUPLICATE_VISIT, () -> command.execute(model));
        EditVisitCommand alternateCommand = new EditVisitCommand(Index.fromOneBased(1),
                testEditVisitDescriptor);
        assertThrows(CommandException.class, EditVisitCommand.MESSAGE_DUPLICATE_VISIT, ()
                -> alternateCommand.execute(model));
    }

    @Test
    public void execute_unchangedVisit_editSuccessful() throws Exception {
        Model model = new ModelManager(new PatientList(), new UserPrefs());
        Patient testPatient = new PatientBuilder().build();
        testPatient.addVisit(testVisit);
        model.addPatient(testPatient);
        EditVisitCommand command = new EditVisitCommand(testPatient.getName(), testPatient.getPhone(),
                testEditVisitDescriptor);
        assertEquals(EditVisitCommand.MESSAGE_SUCCESS, command.execute(model).getFeedbackToUser());
        assertTrue(testPatient.hasVisit(testVisit));
    }

    @Test
    public void execute_validVisit_addSuccessful() throws Exception {
        Model model = new ModelManager(new PatientList(), new UserPrefs());
        Patient testPatient = new PatientBuilder().build();
        Visit visitToBeEdited = new VisitBuilder().withDateOfVisit("1/1/2001").withSeverity("Low")
                .withCondition("toBeEdited").build();
        Visit visitToEdit = new VisitBuilder().withDateOfVisit("2/2/2002").withSeverity("High")
                .withCondition("toEdit").build();
        EditVisitCommand.EditVisitDescriptor descriptor = new EditVisitDescriptorBuilder(visitToEdit).build();
        testPatient.addVisit(visitToBeEdited);
        model.addPatient(testPatient);
        EditVisitCommand command = new EditVisitCommand(testPatient.getName(), testPatient.getPhone(), descriptor);
        CommandResult commandResult = command.execute(model);
        assertEquals(EditVisitCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertTrue(testPatient.hasVisit(visitToEdit));
        assertFalse(testPatient.hasVisit(visitToBeEdited));
    }
}
