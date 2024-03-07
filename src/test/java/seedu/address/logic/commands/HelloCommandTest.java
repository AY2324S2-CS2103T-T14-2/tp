package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelloCommand.MESSAGE_HELLO;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelloCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_hello_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_HELLO, false, false);
        assertCommandSuccess(new HelloCommand(), model, expectedCommandResult, expectedModel);
    }
}
