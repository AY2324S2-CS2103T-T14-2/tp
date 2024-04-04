package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_VISIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEVERITY;
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
import seedu.address.model.patient.Visit;

/**
 * Adds a visit to a Patient.
 */
public class AddVisitCommand extends Command {

    public static final String COMMAND_WORD = "addv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": adds a visit to the specified patient.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATE_OF_VISIT + "DATE OF VISIT] "
            + "[" + PREFIX_CONDITION + "CONDITION] "
            + "[" + PREFIX_SEVERITY + "SEVERITY] \n"
            + "OR "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_DATE_OF_VISIT + "DATE OF VISIT] "
            + "[" + PREFIX_CONDITION + "CONDITION] "
            + "[" + PREFIX_SEVERITY + "SEVERITY] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE_OF_VISIT + "25/2/2024 "
            + PREFIX_CONDITION + "Mild Fever "
            + PREFIX_SEVERITY + "Low";

    public static final String MESSAGE_SUCCESS = "New visit added";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "Could not find specified patient";
    public static final String MESSAGE_DUPLICATE_VISIT = "Identical visit already exists for this patient";
    private final Visit toAdd;
    private final boolean useIndex;
    private Index index;
    private Name name;
    private Phone phone;


    /**
     * Creates an add visit command from {@code index}.
     */
    public AddVisitCommand(Index index, Visit visit) {
        requireAllNonNull(index, visit);

        this.useIndex = true;
        this.index = index;
        this.toAdd = visit;
    }

    /**
     * Creates an add visit command from {@code name} and {@code phone}.
     */
    public AddVisitCommand(Name name, Phone phone, Visit visit) {
        requireAllNonNull(name, phone, visit);

        this.useIndex = false;
        this.name = name;
        this.phone = phone;
        this.toAdd = visit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!hasPatient(model)) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Patient patient = getPatient(model);
        if (patient.hasVisit(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_VISIT);
        }
        patient.addVisit(toAdd);
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
        if (!(other instanceof AddVisitCommand)) {
            return false;
        }

        AddVisitCommand otherAddVisitCommand = (AddVisitCommand) other;
        return toAdd.equals(otherAddVisitCommand.toAdd)
                && useIndex == otherAddVisitCommand.useIndex
                && index.equals(otherAddVisitCommand.index)
                && name.equals(otherAddVisitCommand.name)
                && phone.equals(otherAddVisitCommand.phone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .add("useIndex", useIndex)
                .add("index", index)
                .add("name", name)
                .add("phone", phone)
                .toString();
    }
}
