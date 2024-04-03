package seedu.address.model.patient;

/**
 * Represents one of severity, High or Low.
 */
public enum SeverityOption {
    HIGH("High"),
    LOW("Low");

    private final String label;

    SeverityOption(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
