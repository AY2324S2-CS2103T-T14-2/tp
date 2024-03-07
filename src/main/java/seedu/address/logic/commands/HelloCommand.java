package seedu.address.logic.commands;

import seedu.address.model.Model;
/**
 * Greets the user back.
 */
public class HelloCommand extends Command {

    public static final String COMMAND_WORD = "hello";

    public static final String MESSAGE_HELLO = "Hello :D";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_HELLO, false, false);
    }
}
