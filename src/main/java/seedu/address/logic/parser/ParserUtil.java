package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Appointment;
import seedu.address.model.patient.Condition;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.DateOfVisit;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Severity;
import seedu.address.model.patient.Sex;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String stringToParse) throws ParseException {
        requireNonNull(stringToParse);

        String trimmedName = stringToParse.trim();

        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }

        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String stringToParse) throws ParseException {
        requireNonNull(stringToParse);
        String trimmedPhone = stringToParse.trim();

        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }

        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String dateOfBirth} into an {@code DateOfBirth}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateOfBirth} is invalid.
     */
    public static DateOfBirth parseDateOfBirth(String dateOfBirth) throws ParseException {
        requireNonNull(dateOfBirth);
        String trimmedDateOfBirth = dateOfBirth.trim();
        if (!DateOfBirth.isValidDateOfBirth(trimmedDateOfBirth)) {
            throw new ParseException(DateOfBirth.MESSAGE_CONSTRAINTS);
        }
        return new DateOfBirth(trimmedDateOfBirth);
    }

    /**
     * Parses a {@code String sex} into an {@code Sex}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Sex} is invalid.
     */
    public static Sex parseSex(String sex) throws ParseException {
        requireNonNull(sex);
        String trimmedSex = sex.trim();
        if (!Sex.isValidSex(trimmedSex)) {
            throw new ParseException(Sex.MESSAGE_CONSTRAINTS);
        }
        return new Sex(trimmedSex);
    }

    /**
     * Parses a {@code String appointment} into an {@code Appointment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Appointment} is invalid.
     */
    public static Appointment parseAppointment(String appointment) throws ParseException {
        requireNonNull(appointment);
        String trimmedAppointment = appointment.trim();
        if (!Appointment.isValidAppointment(trimmedAppointment)) {
            throw new ParseException(Appointment.MESSAGE_CONSTRAINTS);
        }
        return new Appointment(trimmedAppointment);
    }

    /**
     * Parses a {@code String dateOfVisit} into an {@code DateOfVisit}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateOfVisit} is invalid.
     */
    public static DateOfVisit parseDateOfVisit(String dateOfVisit) throws ParseException {
        requireNonNull(dateOfVisit);
        String trimmedDateOfVisit = dateOfVisit.trim();
        if (!DateOfVisit.isValidDateOfVisit(trimmedDateOfVisit)) {
            throw new ParseException(DateOfVisit.MESSAGE_CONSTRAINTS);
        }
        return new DateOfVisit(trimmedDateOfVisit);
    }

    /**
     * Parses a {@code String severity} into an {@code Severity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Severity} is invalid.
     */
    public static Severity parseSeverity(String severity) throws ParseException {
        requireNonNull(severity);
        String trimmedSeverity = severity.trim();
        if (!Severity.isValidSeverity(trimmedSeverity)) {
            throw new ParseException(Severity.MESSAGE_CONSTRAINTS);
        }
        return new Severity(trimmedSeverity);
    }

    /**
     * Parses a {@code String condition} into an {@code Condition}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Condition} is invalid.
     */
    public static Condition parseCondition(String condition) throws ParseException {
        requireNonNull(condition);
        String trimmedCondition = condition.trim();
        if (!Condition.isValidCondition(trimmedCondition)) {
            throw new ParseException(Condition.MESSAGE_CONSTRAINTS);
        }
        return new Condition(trimmedCondition);
    }
}
