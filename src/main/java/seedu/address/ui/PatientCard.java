package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Visit;

/**
 * A UI component that displays information of a {@code Patient}.
 */
public class PatientCard extends UiPart<Region> {

    private static final String FXML = "PatientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */

    public final Patient patient;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label sex;
    @FXML
    private Label appointment;
    @FXML
    private Label visit;
    @FXML
    private Label hasLatestVisit;
    @FXML
    private Label dateOfVisit;
    @FXML
    private Label condition;
    @FXML
    private Label severity;

    /**
     * Creates a {@code PatientCard} with the given {@code Patient} and index to display.
     */
    public PatientCard(Patient patient, int displayedIndex) {
        super(FXML);
        this.patient = patient;
        id.setText(displayedIndex + ". ");
        name.setText(patient.getName().fullName);
        phone.setText(patient.getPhone().value);
        address.setText(patient.getAddress().value);
        email.setText(patient.getEmail().value);
        dateOfBirth.setText(patient.getDateOfBirth().toString());
        sex.setText(patient.getSex().toString());

        if (patient.getAppointment().appointment == null) {
            appointment.setText("");
        } else {
            appointment.setText("Next appointment: " + patient.getAppointment().toString());
        }

        if (!patient.getVisits().isEmpty()) {
            Visit latest = patient.getLatestVisit();
            hasLatestVisit.setText("");
            dateOfVisit.setText("Date: " + latest.getDateOfVisit().toString());
            condition.setText("Condition: " + latest.getCondition().value);
            severity.setText("Severity: " + latest.getSeverity().toString());
        } else {
            hasLatestVisit.setText("None");
            dateOfVisit.setText("");
            condition.setText("");
            severity.setText("");
        }
    }
}
