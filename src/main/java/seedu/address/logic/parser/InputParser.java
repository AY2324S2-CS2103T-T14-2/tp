package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAllCommand;
import seedu.address.logic.commands.DeleteByIndexCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ForceDeleteAllCommand;
import seedu.address.logic.commands.ForceExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NoCommand;
import seedu.address.logic.commands.YesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class InputParser {
    private static final String DELETE_ALL_ERROR_MESSAGE =
            "Please give either 'yes' or 'no' after 'delete-all' command!";
    private static final String EXIT_ERROR_MESSAGE = "Please give either 'yes' or 'no' after 'exit' command!";

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(InputParser.class);

    private boolean isPreviousCommandDeleteAll = false; // To check if the previous command was "delete-all"
    private boolean isPreviousCommandExit = false; // To check if the previous command was "exit"

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        // Checking if the previous command was "delete-all"
        if (isPreviousCommandDeleteAll) {
            isPreviousCommandDeleteAll = false;

            if (userInput.equals("yes")) {
                return new YesCommand("delete-all");
            } else if (userInput.equals("no")) {
                return new NoCommand();
            } else {
                throw new ParseException(DELETE_ALL_ERROR_MESSAGE);
            }
        }

        // Checking if the previous command was "exit"
        if (isPreviousCommandExit) {
            isPreviousCommandExit = false;

            if (userInput.equals("yes")) {
                return new YesCommand("exit");
            } else if (userInput.equals("no")) {
                return new NoCommand();
            } else {
                throw new ParseException(EXIT_ERROR_MESSAGE);
            }
        }

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteByIndexCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteAllCommand.COMMAND_WORD:
            isPreviousCommandDeleteAll = true;
            return new DeleteAllCommand();

        case ForceDeleteAllCommand.COMMAND_WORD:
            return new ForceDeleteAllCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            isPreviousCommandExit = true;
            return new ExitCommand();

        case ForceExitCommand.COMMAND_WORD:
            return new ForceExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Returns the error message for the delete-all command.
     *
     * @return The error message for the delete-all command.
     */
    public static String getDeleteAllErrorMessage() {
        return DELETE_ALL_ERROR_MESSAGE;
    }
}
