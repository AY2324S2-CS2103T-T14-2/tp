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
import seedu.address.logic.commands.AddVisitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Condition;
import seedu.address.model.patient.DateOfVisit;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Severity;
import seedu.address.model.patient.Visit;

/**
 * Parses input arguments and creates a new AddVisitCommand object
 */
public class AddVisitCommandParser implements Parser<AddVisitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddVisitCommand
     * and returns an AddVisitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddVisitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_DATE_OF_VISIT, PREFIX_CONDITION,
                        PREFIX_SEVERITY);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_DATE_OF_VISIT, PREFIX_CONDITION,
                PREFIX_SEVERITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE_OF_VISIT, PREFIX_CONDITION, PREFIX_SEVERITY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE));
        }

        DateOfVisit dateOfVisit = ParserUtil.parseDateOfVisit(argMultimap.getValue(PREFIX_DATE_OF_VISIT).get());
        Severity severity = ParserUtil.parseSeverity(argMultimap.getValue(PREFIX_SEVERITY).get());
        Condition condition = ParserUtil.parseCondition(argMultimap.getValue(PREFIX_CONDITION).get());

        Visit visit = new Visit(condition, severity, dateOfVisit);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE) && !argMultimap.getPreamble().isEmpty()) {
            return commandFromIndex(argMultimap.getPreamble(), visit);
        } else if (argMultimap.getPreamble().isEmpty() && arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)) {
            return commandFromPrefixes(argMultimap.getValue(PREFIX_NAME).get(),
                    argMultimap.getValue(PREFIX_PHONE).get(), visit);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private AddVisitCommand commandFromIndex(String arg, Visit visit) throws ParseException {
        Index index;

        try {
            index = ParserUtil.parseIndex(arg);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVisitCommand.MESSAGE_USAGE), pe);
        }

        return new AddVisitCommand(index, visit);
    }

    private AddVisitCommand commandFromPrefixes(String nameArg, String phoneArg, Visit visit) throws ParseException {
        Name name = ParserUtil.parseName(nameArg);
        Phone phone = ParserUtil.parsePhone(phoneArg);

        return new AddVisitCommand(name, phone, visit);
    }
}
