package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_VISIT;

import java.time.LocalDate;

import seedu.address.logic.commands.ListByDateCriteriaCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.ApptDateMatchesPredicate;

/**
 * Lists patient by appointment dates parser
 */
public class ListByDateCriteriaCommandParser implements Parser<ListByDateCriteriaCommand> {

    @Override
    public ListByDateCriteriaCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE_OF_VISIT);

        if (!argMultimap.getPreamble().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListByDateCriteriaCommand.MESSAGE_USAGE));
        }

        LocalDate dateOfVisit =
                ParserUtil.parseAppointment(argMultimap.getValue(PREFIX_DATE_OF_VISIT).get()).appointment;
        ApptDateMatchesPredicate apptDatePredicate = new ApptDateMatchesPredicate(dateOfVisit);

        return new ListByDateCriteriaCommand(apptDatePredicate);
    }
}
