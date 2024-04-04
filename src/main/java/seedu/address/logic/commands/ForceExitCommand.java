package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Forcefully terminates the program without confirmation check.
 */
public class ForceExitCommand extends Command {
    public static final String COMMAND_WORD = "exit-f";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting MediTrack as requested!";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }
}
