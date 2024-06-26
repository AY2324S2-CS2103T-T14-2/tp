package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteAllCommand;
import seedu.address.logic.commands.DeleteByIndexCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ForceDeleteAllCommand;
import seedu.address.logic.commands.ForceExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NoCommand;
import seedu.address.logic.commands.YesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.EditPatientDescriptorBuilder;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.PatientUtil;

public class PatientListParserTest {

    private final InputParser parser = new InputParser();

    @Test
    public void parseCommand_add() throws Exception {
        Patient patient = new PatientBuilder().build();
        System.out.println(patient);
        System.out.println(PatientUtil.getAddCommand(patient));
        AddCommand command = (AddCommand) parser.parseCommand(PatientUtil.getAddCommand(patient));
        assertEquals(new AddCommand(patient), command);
    }

    /**
     * Test for the delete-all command.
     *
     * @throws Exception Exception thrown by the parseCommand method.
     */
    @Test
    public void parseCommand_deleteAll() throws Exception {
        assertTrue(parser.parseCommand(DeleteAllCommand.COMMAND_WORD) instanceof DeleteAllCommand);
        assertTrue(parser.parseCommand(YesCommand.COMMAND_WORD) instanceof YesCommand);

        assertTrue(parser.parseCommand(DeleteAllCommand.COMMAND_WORD) instanceof DeleteAllCommand);
        assertTrue(parser.parseCommand(NoCommand.COMMAND_WORD) instanceof NoCommand);

        assertTrue(parser.parseCommand(DeleteAllCommand.COMMAND_WORD) instanceof DeleteAllCommand);
        assertThrows(ParseException.class, InputParser.getDeleteAllErrorMessage(), ()
                -> parser.parseCommand(EditCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_forceDeleteAll() throws Exception {
        assertTrue(parser.parseCommand(ForceDeleteAllCommand.COMMAND_WORD) instanceof ForceDeleteAllCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteByIndexCommand command = (DeleteByIndexCommand) parser.parseCommand(
                DeleteByIndexCommand.COMMAND_WORD + " " + INDEX_FIRST_PATIENT.getOneBased());
        assertEquals(new DeleteByIndexCommand(INDEX_FIRST_PATIENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Patient patient = new PatientBuilder().build();
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(patient).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PATIENT.getOneBased() + " " + PatientUtil.getEditPatientDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PATIENT, descriptor), command);
    }

    @Test
    public void parseCommand_forceExit() throws Exception {
        assertTrue(parser.parseCommand(ForceExitCommand.COMMAND_WORD) instanceof ForceExitCommand);
        assertTrue(parser.parseCommand(ForceExitCommand.COMMAND_WORD + " 3") instanceof ForceExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " n/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords),
                PREDICATE_SHOW_ALL_PATIENTS), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
