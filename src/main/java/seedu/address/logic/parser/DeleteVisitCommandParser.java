package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteVisitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Phone;

/**
 * Parses input arguments and creates a new DeleteVisitCommand object
 */
public class DeleteVisitCommandParser implements Parser<DeleteVisitCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteVisitCommand
     * and returns a DeleteVisitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteVisitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE) && !argMultimap.getPreamble().isEmpty()) {
            return commandFromIndex(argMultimap.getPreamble());
        } else if (argMultimap.getPreamble().isEmpty() && arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)) {
            return commandFromPrefixes(argMultimap.getValue(PREFIX_NAME).get(),
                    argMultimap.getValue(PREFIX_PHONE).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteVisitCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private DeleteVisitCommand commandFromIndex(String arg) throws ParseException {
        Index index;

        try {
            index = ParserUtil.parseIndex(arg);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteVisitCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteVisitCommand(index);
    }

    private DeleteVisitCommand commandFromPrefixes(String nameArg, String phoneArg) throws ParseException {
        Name name = ParserUtil.parseName(nameArg);
        Phone phone = ParserUtil.parsePhone(phoneArg);

        return new DeleteVisitCommand(name, phone);
    }
}
