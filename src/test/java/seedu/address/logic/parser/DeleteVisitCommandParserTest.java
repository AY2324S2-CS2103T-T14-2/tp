package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteVisitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Visit;
import seedu.address.testutil.VisitBuilder;

public class DeleteVisitCommandParserTest {
    private DeleteVisitCommandParser parser = new DeleteVisitCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteVisitCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/name n/anothername", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteVisitCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteVisitCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsDeleteVisitCommand() throws ParseException {
        Visit testVisit = new VisitBuilder().build();
        DeleteVisitCommand actualCommandWithoutindex =
                parser.parse(" n/Alice p/99999999");
        assertTrue(actualCommandWithoutindex instanceof DeleteVisitCommand);
        DeleteVisitCommand actualCommandWithindex =
                parser.parse("  1  ");
        assertTrue(actualCommandWithindex instanceof DeleteVisitCommand);
    }
}
