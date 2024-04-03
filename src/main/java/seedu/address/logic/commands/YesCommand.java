package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.PatientList;

/**
 * Yes command for 'delete-all' command, force delete all entries.
 */
public class YesCommand extends Command {
    public static final String COMMAND_WORD = "yes";
    public static final String MESSAGE_SUCCESS = "Successfully deleted all data";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.setPatientList(new PatientList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
