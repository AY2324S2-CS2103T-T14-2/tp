package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String CONFIRMATION = "Would you like to exit the application? Enter 'yes' or 'no'.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(CONFIRMATION);
    }
}
