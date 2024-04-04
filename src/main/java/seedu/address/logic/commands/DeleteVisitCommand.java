package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;

/**
 * Deletes the last visit from a Patient.
 */
public class DeleteVisitCommand extends Command {

    public static final String COMMAND_WORD = "deletev";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": deletes the latest visit from the specified patient.\n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "OR "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] \n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Visit deleted";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "Could not find specified patient";
    public static final String MESSAGE_NO_VISIT = "This patient does not have a visit to delete";
    private final boolean useIndex;
    private Index index;
    private Name name;
    private Phone phone;


    /**
     * Creates a delete visit command from {@code index}.
     */
    public DeleteVisitCommand(Index index) {
        requireNonNull(index);

        this.useIndex = true;
        this.index = index;
    }

    /**
     * Creates a delete visit command from {@code name} and {@code phone}.
     */
    public DeleteVisitCommand(Name name, Phone phone) {
        requireAllNonNull(name, phone);

        this.useIndex = false;
        this.name = name;
        this.phone = phone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!hasPatient(model)) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Patient patient = getPatient(model);
        if (patient.getVisits().isEmpty()) {
            throw new CommandException(MESSAGE_NO_VISIT);
        }
        patient.removeVisit(patient.getLatestVisit());
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        // Bandaid solution to fix UI not detecting visit changes.
        model.deletePatient(patient);
        model.addPatient(patient);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    private boolean hasPatient(Model model) {
        if (useIndex) {
            return true;
        }
        return model.hasPatient(name, phone);
    }

    private Patient getPatient(Model model) throws CommandException {
        if (useIndex) {
            return getPatientFromIndex(model);
        } else {
            return model.getPatient(name, phone);
        }
    }

    private Patient getPatientFromIndex(Model model) throws CommandException {
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        return lastShownList.get(index.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteVisitCommand)) {
            return false;
        }

        DeleteVisitCommand otherDeleteVisitCommand = (DeleteVisitCommand) other;
        return useIndex == otherDeleteVisitCommand.useIndex
                && index.equals(otherDeleteVisitCommand.index)
                && name.equals(otherDeleteVisitCommand.name)
                && phone.equals(otherDeleteVisitCommand.phone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("useIndex", useIndex)
                .add("index", index)
                .add("name", name)
                .add("phone", phone)
                .toString();
    }
}
