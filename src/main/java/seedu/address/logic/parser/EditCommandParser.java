package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATEOFBIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Phone;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String[] splitMessages = args.trim().split(" ");
        Boolean hasNamePrefix = false;
        if (splitMessages[0].length() >= 2) {
            hasNamePrefix = splitMessages[0].substring(0, 2).equals("n/");
        }

        if (hasNamePrefix) {
            return parseStringWithNameAndPhone(splitMessages);
        } else {
            return parseStringWithIndex(args);
        }
    }

    private EditCommand parseStringWithNameAndPhone(String[] splitMessages) throws ParseException {
        String namePrefix = splitMessages[0].substring(2) + " ";
        String phonePrefix = "";
        String rest = " ";
        Boolean isNameFilled = false;
        Boolean isPhoneFilled = false;

        for (int i = 1; i < splitMessages.length; i++) {
            if (!isNameFilled && splitMessages[i].substring(0, 2).equals("p/") && !isPhoneFilled) {
                isNameFilled = true;
                phonePrefix = splitMessages[i].substring(2);
                isPhoneFilled = true;
            } else if (!isNameFilled && !splitMessages[i].substring(0, 2).equals("p/")) {
                namePrefix += splitMessages[i] + " ";
            } else {
                rest += splitMessages[i] + " ";
            }
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(rest, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_DATEOFBIRTH, PREFIX_SEX, PREFIX_APPOINTMENT);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_DATEOFBIRTH, PREFIX_SEX, PREFIX_APPOINTMENT);

        EditPatientDescriptor editPatientDescriptor = new EditPatientDescriptor();

        setEditPatientDescriptor(editPatientDescriptor, argMultimap);

        return new EditCommand(new Name(namePrefix.trim()), new Phone(phonePrefix.trim()), editPatientDescriptor);
    }

    private EditCommand parseStringWithIndex(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_DATEOFBIRTH, PREFIX_SEX, PREFIX_APPOINTMENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_DATEOFBIRTH, PREFIX_SEX, PREFIX_APPOINTMENT);

        EditPatientDescriptor editPatientDescriptor = new EditPatientDescriptor();

        setEditPatientDescriptor(editPatientDescriptor, argMultimap);

        return new EditCommand(index, editPatientDescriptor);
    }

    private static void setEditPatientDescriptor(EditPatientDescriptor editPatientDescriptor,
                                                 ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPatientDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPatientDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPatientDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPatientDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_DATEOFBIRTH).isPresent()) {
            editPatientDescriptor.setDateOfBirth(ParserUtil.parseDateOfBirth(argMultimap
                    .getValue(PREFIX_DATEOFBIRTH).get()));
        }
        if (argMultimap.getValue(PREFIX_SEX).isPresent()) {
            editPatientDescriptor.setSex(ParserUtil.parseSex(argMultimap.getValue(PREFIX_SEX).get()));
        }
        if (argMultimap.getValue(PREFIX_APPOINTMENT).isPresent()) {
            editPatientDescriptor.setAppointment(ParserUtil.parseAppointment(argMultimap
                    .getValue(PREFIX_APPOINTMENT).get()));
        }

        if (!editPatientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
