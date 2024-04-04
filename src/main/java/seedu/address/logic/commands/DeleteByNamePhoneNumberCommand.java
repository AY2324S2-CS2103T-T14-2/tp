package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.InputParser;
import seedu.address.model.Model;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;

/**
 * Deletes a patient identified using name and phone number.
 */
public class DeleteByNamePhoneNumberCommand extends Command {
    public static final String COMMAND_WORD = "delete-p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient identified by the specified name and phone number.\n"
            + "Command format: delete-p n/<Patient Name> p/<Phone number>\n"
            + "Example: " + COMMAND_WORD + " "
            + "n/Eugene Hirose p/90807561";

    public static final String DELETE_PATIENT_SUCCESS_MESSAGE = "Successfully deleted patient %1$s's data";
    private static final Logger logger = LogsCenter.getLogger(InputParser.class);

    private final Name name;
    private final Phone phone;

    /**
     * Constructor for DeleteByNamePhoneNumberCommand.
     *
     * @param name Name of the patient to be deleted.
     * @param phone Phone number of the patient to be deleted.
     */
    public DeleteByNamePhoneNumberCommand(Name name, Phone phone) {
        this.name = name;
        this.phone = phone;
    }

    /**
     * Executes the command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of the command.
     * @throws CommandException Exception thrown if the command fails.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Patient patientToBeDeleted = model.getPatient(name, phone);

        logger.fine("checkpoint");

        if (patientToBeDeleted == null) {
            throw new CommandException(String.format("There is no patient with name %s and phone number %s",
                    name, phone));
        }

        model.deletePatient(patientToBeDeleted);

        return new CommandResult(String.format(DELETE_PATIENT_SUCCESS_MESSAGE, patientToBeDeleted.getName()));
    }
}
