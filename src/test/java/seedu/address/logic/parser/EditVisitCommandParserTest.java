package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditVisitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Visit;
import seedu.address.testutil.VisitBuilder;

public class EditVisitCommandParserTest {
    private EditVisitCommandParser parser = new EditVisitCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "     ", EditVisitCommand.MESSAGE_NOT_EDITED);
        assertParseFailure(parser, "n/name n/anothername", EditVisitCommand.MESSAGE_NOT_EDITED);
        assertParseFailure(parser, "1 n/name p/phone c/condition v/High",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditVisitCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsEditVisitCommand() throws ParseException {
        Visit testVisit = new VisitBuilder().build();
        EditVisitCommand actualCommandWithoutindex =
                parser.parse(" n/Alice p/99999999 d/2025-2-25 v/High c/Fever");
        assertTrue(actualCommandWithoutindex instanceof EditVisitCommand);
        EditVisitCommand actualCommandWithindex =
                parser.parse("  1  d/25/2/2025 v/High c/Fever");
        assertTrue(actualCommandWithindex instanceof EditVisitCommand);
    }
}
