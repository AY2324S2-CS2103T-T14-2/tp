package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteByNamePhoneNumberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Phone;

/**
 * Parses input arguments and creates a new DeleteByNamePhoneNumberCommand object.
 */
public class DeleteByNamePhoneNumberCommandParser implements Parser<DeleteByNamePhoneNumberCommand> {
    /**
     * Parses the given argument String into a DeleteByNamePhoneNumberCommand.
     *
     * @param args String of arguments.
     * @return DeleteByNamePhoneNumberCommand object.
     * @throws ParseException Exception thrown if the user input does not conform the expected format.
     */
    public DeleteByNamePhoneNumberCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteByNamePhoneNumberCommand.MESSAGE_USAGE));
        } else if (argMultimap.getPreamble().isEmpty() && arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)) {
            String nameArg = argMultimap.getValue(PREFIX_NAME).get();
            String phoneArg = argMultimap.getValue(PREFIX_PHONE).get();

            Name name = ParserUtil.parseName(nameArg);
            Phone phone = ParserUtil.parsePhone(phoneArg);

            return new DeleteByNamePhoneNumberCommand(name, phone);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNamePhoneNumberCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given.
     *
     * @param argumentMultimap ArgumentMultimap object.
     * @param prefixes Prefix objects.
     * @return True if none of the prefixes contains empty {@code Optional} values in the given.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
