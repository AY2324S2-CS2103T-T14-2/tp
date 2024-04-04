package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_VISIT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientList;
import seedu.address.model.patient.ApptDateMatchesPredicate;
import seedu.address.model.patient.Patient;

/**
 * Lists patient by appointment dates on or before given date
 */
public class ListByDateCriteriaCommand extends Command {

    public static final String COMMAND_WORD = "list-until-date";

    public static final String MESSAGE_SUCCESS = "Listed all patients with appointment date on or before date";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all patients with appointment date on or before date "
            + "Parameters: "
            + "[" + PREFIX_DATE_OF_VISIT + "DATEOFVISIT] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE_OF_VISIT + "25/2/2024";

    private final ApptDateMatchesPredicate apptDatePredicate;

    public ListByDateCriteriaCommand(ApptDateMatchesPredicate apptDatePredicate) {
        this.apptDatePredicate = apptDatePredicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        List<Patient> sortedList = new ArrayList<Patient>();
        model.updateFilteredPatientList(apptDatePredicate);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
