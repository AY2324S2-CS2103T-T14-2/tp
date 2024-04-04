package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses date time input arguments and creates a LocalDate object
 */
public class DateTimeParser {
    public static final DateTimeFormatter INPUTFORMAT1 = DateTimeFormatter.ofPattern("d/M/yyyy");
    public static final DateTimeFormatter INPUTFORMAT2 = DateTimeFormatter.ofPattern("yyyy-M-d");
    public static final DateTimeFormatter OUTPUTFORMAT = DateTimeFormatter.ofPattern("yyyy-M-d");

    /**
     * Parse String date into LocalDate.
     *
     * @param dateTime date input.
     * @return date in LocalDate type.
     */
    public static LocalDate parseDateTime(String dateTime) {
        String cleanDateString = dateTime.strip();
        DateTimeFormatter inputFormat = determineInputFormat(cleanDateString);
        LocalDate date = null;

        try {
            date = LocalDate.parse(cleanDateString, inputFormat);
            return date;
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Determines in which format the date is input.
     *
     * @return the input format of String date.
     */
    private static DateTimeFormatter determineInputFormat(String dateOfBirth) {
        boolean isInputFromat1 = dateOfBirth.split("/").length > 1;
        return isInputFromat1 ? INPUTFORMAT1 : INPUTFORMAT2;
    }

    /**
     * Converts the date into String output format.
     *
     * @return String date in output format.
     */
    public static String outputDateTime(LocalDate date) {
        return date.format(OUTPUTFORMAT);
    }
}
