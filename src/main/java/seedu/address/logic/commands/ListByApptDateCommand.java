package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientList;
import seedu.address.model.patient.Patient;

/**
 * Lists patient by appointment dates
 */
public class ListByApptDateCommand extends Command {

    public static final String COMMAND_WORD = "list-by-date";

    public static final String MESSAGE_SUCCESS = "Listed all patients by ascending appointment date";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        List<Patient> sortedList = new ArrayList<Patient>();
        sortedList.addAll(model.getFilteredPatientList());
        Comparator<Patient> comparator = (patient1, patient2) -> {
            if (null != patient1.getAppointment().appointment && null != patient2.getAppointment().appointment) {
                return patient1.getAppointment().appointment.compareTo((patient2.getAppointment().appointment));
            } else if (null != patient1.getAppointment().appointment && null == patient2.getAppointment().appointment) {
                return 1;
            } else if (null == patient1.getAppointment().appointment && null != patient2.getAppointment().appointment) {
                return -1;
            } else if (null == patient1.getAppointment().appointment && null == patient2.getAppointment().appointment) {
                return 0;
            }
            return 0;
        };
        sortedList.sort(comparator);

        for (Patient patient : sortedList) {
            model.setPatientList(new PatientList());
            //model.deletePatient(patient);
        }
        for (Patient patient : sortedList) {
            model.addPatient(patient);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
