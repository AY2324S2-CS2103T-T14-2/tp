package seedu.address.testutil;

import seedu.address.logic.commands.EditVisitCommand;
import seedu.address.model.patient.Condition;
import seedu.address.model.patient.DateOfVisit;
import seedu.address.model.patient.Severity;
import seedu.address.model.patient.Visit;

/**
 * A utility class to help with building EditVisitDescriptor objects.
 */
public class EditVisitDescriptorBuilder {
    private EditVisitCommand.EditVisitDescriptor descriptor;

    public EditVisitDescriptorBuilder() {
        descriptor = new EditVisitCommand.EditVisitDescriptor();
    }

    public EditVisitDescriptorBuilder(EditVisitCommand.EditVisitDescriptor descriptor) {
        this.descriptor = new EditVisitCommand.EditVisitDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditVisitDescriptor} with fields containing {@code visit}'s details
     */
    public EditVisitDescriptorBuilder(Visit visit) {
        descriptor = new EditVisitCommand.EditVisitDescriptor();
        descriptor.setDateOfVisit(visit.getDateOfVisit());
        descriptor.setCondition(visit.getCondition());
        descriptor.setSeverity(visit.getSeverity());
    }

    /**
     * Sets the {@code DateOfVisit} of the {@code EditVisitDescriptor} that we are building.
     */
    public EditVisitDescriptorBuilder withDateOfVisit(String dateOfVisit) {
        descriptor.setDateOfVisit(new DateOfVisit(dateOfVisit));
        return this;
    }

    /**
     * Sets the {@code Severity} of the {@code EditVisitDescriptor} that we are building.
     */
    public EditVisitDescriptorBuilder withSeverity(String severity) {
        descriptor.setSeverity(new Severity(severity));
        return this;
    }

    /**
     * Sets the {@code Condition} of the {@code EditVisitDescriptor} that we are building.
     */
    public EditVisitDescriptorBuilder withCondition(String condition) {
        descriptor.setCondition(new Condition(condition));
        return this;
    }

    public EditVisitCommand.EditVisitDescriptor build() {
        return descriptor;
    }
}
