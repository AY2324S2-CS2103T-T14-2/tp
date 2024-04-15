package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddVisitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Visit;
import seedu.address.testutil.VisitBuilder;

public class AddVisitCommandParserTest {
    private AddVisitCommandParser parser = new AddVisitCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddVisitCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/name n/anothername", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddVisitCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/name p/phone c/condition v/High",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsAddVisitCommand() throws ParseException {
        Visit testVisit = new VisitBuilder().build();
        AddVisitCommand actualCommandWithoutindex =
                parser.parse(" n/Alice p/99999999 d/2025-2-25 v/Low c/Fever");
        assertTrue(actualCommandWithoutindex instanceof AddVisitCommand);
        AddVisitCommand actualCommandWithindex =
                parser.parse("  1  d/25/2/2025 v/Low c/Fever");
        assertTrue(actualCommandWithindex instanceof AddVisitCommand);
    }
}
