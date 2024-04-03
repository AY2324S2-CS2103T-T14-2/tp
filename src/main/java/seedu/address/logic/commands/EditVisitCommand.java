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
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Condition;
import seedu.address.model.patient.DateOfVisit;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Severity;
import seedu.address.model.patient.Visit;

/**
 * Edits the latest visit from a Patient.
 */
public class EditVisitCommand extends Command {
    public static final String COMMAND_WORD = "editv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": edits the latest visit from the specified patient.\n"
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
            + PREFIX_CONDITION + "High Fever "
            + PREFIX_SEVERITY + "High";;

    public static final String MESSAGE_SUCCESS = "Visit edited";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "Could not find specified patient";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_NO_VISIT = "This patient does not have a visit to edit";
    public static final String MESSAGE_DUPLICATE_VISIT = "Identical visit already exists for this patient";
    private final EditVisitDescriptor editVisitDescriptor;
    private final boolean useIndex;
    private Index index;
    private Name name;
    private Phone phone;


    /**
     * Creates an edit visit command from {@code index}.
     */
    public EditVisitCommand(Index index, EditVisitDescriptor editVisitDescriptor) {
        requireAllNonNull(index, editVisitDescriptor);

        this.useIndex = true;
        this.index = index;
        this.editVisitDescriptor = editVisitDescriptor;
    }

    /**
     * Creates an edit visit command from {@code name} and {@code phone}.
     */
    public EditVisitCommand(Name name, Phone phone, EditVisitDescriptor editVisitDescriptor) {
        requireAllNonNull(name, phone, editVisitDescriptor);

        this.useIndex = false;
        this.name = name;
        this.phone = phone;
        this.editVisitDescriptor = editVisitDescriptor;
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

        Visit visitToEdit = patient.getLatestVisit();
        Visit editedVisit = createEditedVisit(visitToEdit, editVisitDescriptor);

        if (!visitToEdit.equals(editedVisit) && patient.hasVisit(editedVisit)) {
            throw new CommandException(MESSAGE_DUPLICATE_VISIT);
        }

        patient.setVisit(visitToEdit, editedVisit);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        // Bandaid solution to fix UI not detecting visit changes.
        model.deletePatient(patient);
        model.addPatient(patient);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Creates and returns a {@code Visit} with the details of {@code visitToEdit}
     * edited with {@code editVisitDescriptor}.
     */
    private static Visit createEditedVisit(Visit visitToEdit, EditVisitDescriptor editVisitDescriptor) {
        assert visitToEdit != null;

        Condition updatedCondition = editVisitDescriptor.getCondition().orElse(visitToEdit.getCondition());
        Severity updatedSeverity = editVisitDescriptor.getSeverity().orElse(visitToEdit.getSeverity());
        DateOfVisit updatedDateOfVisit = editVisitDescriptor.getDateOfVisit().orElse(visitToEdit.getDateOfVisit());

        return new Visit(updatedCondition, updatedSeverity, updatedDateOfVisit);
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
        if (!(other instanceof EditVisitCommand)) {
            return false;
        }

        EditVisitCommand otherEditVisitCommand = (EditVisitCommand) other;
        return useIndex == otherEditVisitCommand.useIndex
                && editVisitDescriptor.equals(otherEditVisitCommand.editVisitDescriptor)
                && index.equals(otherEditVisitCommand.index)
                && name.equals(otherEditVisitCommand.name)
                && phone.equals(otherEditVisitCommand.phone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("editVisitDescriptor", editVisitDescriptor)
                .add("useIndex", useIndex)
                .add("index", index)
                .add("name", name)
                .add("phone", phone)
                .toString();
    }

    /**
     * Stores the details to edit the visit with. Each non-empty field value will replace the
     * corresponding field value of the visit.
     */
    public static class EditVisitDescriptor {
        private Condition condition;
        private Severity severity;
        private DateOfVisit dateOfVisit;

        public EditVisitDescriptor() {};

        /**
         * Copy constructor.
         */
        public EditVisitDescriptor(EditVisitCommand.EditVisitDescriptor toCopy) {
            setCondition(toCopy.condition);
            setSeverity(toCopy.severity);
            setDateOfVisit(toCopy.dateOfVisit);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(condition, severity, dateOfVisit);
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        public Optional<Condition> getCondition() {
            return Optional.ofNullable(condition);
        }

        public void setDateOfVisit(DateOfVisit dateOfVisit) {
            this.dateOfVisit = dateOfVisit;
        }

        public Optional<DateOfVisit> getDateOfVisit() {
            return Optional.ofNullable(dateOfVisit);
        }

        public void setSeverity(Severity severity) {
            this.severity = severity;
        }

        public Optional<Severity> getSeverity() {
            return Optional.ofNullable(severity);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditVisitCommand.EditVisitDescriptor)) {
                return false;
            }

            EditVisitCommand.EditVisitDescriptor otherEditVisitDescriptor =
                    (EditVisitCommand.EditVisitDescriptor) other;
            return Objects.equals(condition, otherEditVisitDescriptor.condition)
                    && Objects.equals(severity, otherEditVisitDescriptor.severity)
                    && Objects.equals(dateOfVisit, otherEditVisitDescriptor.dateOfVisit);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("condition", condition)
                    .add("severity", severity)
                    .add("dateOfVisit", dateOfVisit)
                    .toString();
        }
    }
}
