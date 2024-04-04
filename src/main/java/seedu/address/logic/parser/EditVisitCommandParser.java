package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_VISIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEVERITY;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditVisitCommand;
import seedu.address.logic.commands.EditVisitCommand.EditVisitDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Phone;
/**
 * Parses input arguments and creates a new EditVisitCommand object
 */
public class EditVisitCommandParser implements Parser<EditVisitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditVisitCommand
     * and returns an EditVisitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditVisitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_DATE_OF_VISIT, PREFIX_CONDITION,
                        PREFIX_SEVERITY);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_DATE_OF_VISIT, PREFIX_CONDITION,
                PREFIX_SEVERITY);

        EditVisitDescriptor editVisitDescriptor = new EditVisitDescriptor();

        if (argMultimap.getValue(PREFIX_CONDITION).isPresent()) {
            editVisitDescriptor.setCondition(ParserUtil.parseCondition(argMultimap.getValue(PREFIX_CONDITION).get()));
        }
        if (argMultimap.getValue(PREFIX_SEVERITY).isPresent()) {
            editVisitDescriptor.setSeverity(ParserUtil.parseSeverity(argMultimap.getValue(PREFIX_SEVERITY).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE_OF_VISIT).isPresent()) {
            editVisitDescriptor.setDateOfVisit(
                    ParserUtil.parseDateOfVisit(argMultimap.getValue(PREFIX_DATE_OF_VISIT).get()));
        }

        if (!editVisitDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditVisitCommand.MESSAGE_NOT_EDITED);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE) && !argMultimap.getPreamble().isEmpty()) {
            return commandFromIndex(argMultimap.getPreamble(), editVisitDescriptor);
        } else if (argMultimap.getPreamble().isEmpty() && arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)) {
            return commandFromPrefixes(argMultimap.getValue(PREFIX_NAME).get(),
                    argMultimap.getValue(PREFIX_PHONE).get(), editVisitDescriptor);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditVisitCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private EditVisitCommand commandFromIndex(String arg, EditVisitDescriptor editVisitDescriptor)
            throws ParseException {
        Index index;

        try {
            index = ParserUtil.parseIndex(arg);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditVisitCommand.MESSAGE_USAGE), pe);
        }

        return new EditVisitCommand(index, editVisitDescriptor);
    }

    private EditVisitCommand commandFromPrefixes(String nameArg, String phoneArg,
                                                 EditVisitDescriptor editVisitDescriptor) throws ParseException {
        Name name = ParserUtil.parseName(nameArg);
        Phone phone = ParserUtil.parsePhone(phoneArg);

        return new EditVisitCommand(name, phone, editVisitDescriptor);
    }
}
