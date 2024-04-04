package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.PatientList;

/**
 * Yes command for 'delete-all' command, force delete all entries.
 */
public class YesCommand extends Command {
    public static final String COMMAND_WORD = "yes";
    public static final String DELETE_MESSAGE_SUCCESS = "Successfully deleted all data";
    public static final String EXIT_MESSAGE_SUCCESS = "Successfully exited the application";


    private String previousCommand;

    /**
     * Constructor for YesCommand.
     *
     * @param previousCommand The previous command that was executed.
     */
    public YesCommand(String previousCommand) {
        super();
        this.previousCommand = previousCommand;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (this.previousCommand.equals("delete-all")) {
            model.setPatientList(new PatientList());

            return new CommandResult(DELETE_MESSAGE_SUCCESS);
        } else if (this.previousCommand.equals("exit")) {
            return new CommandResult(EXIT_MESSAGE_SUCCESS, false, true);
        } else {
            return new CommandResult("");
        }
    }
}
