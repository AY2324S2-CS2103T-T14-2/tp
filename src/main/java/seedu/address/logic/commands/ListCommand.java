package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.patient.Patient;

/**
 * Lists all patients in the patient list arbitrarily to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all patients";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
