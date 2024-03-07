package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Prints the user input.
 */
public class PrintCommand extends Command {

    public static final String COMMAND_WORD = "print";

    private String message;

    public PrintCommand(String message) {
        this.message = message;
    }

    @Override
    public CommandResult execute(Model model) {
        String trimmedMessage = message.trim();
        String removedCommand = trimmedMessage.replace("print", "");
        return new CommandResult(removedCommand);
    }
}
