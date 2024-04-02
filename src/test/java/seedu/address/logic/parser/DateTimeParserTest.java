package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DateTimeParserTest {
    @Test
    public void parseDateTime() {
        // invalid date string
        assertEquals(DateTimeParser.parseDateTime(""), null);
        assertEquals(DateTimeParser.parseDateTime(" "), null);
        assertEquals(DateTimeParser.parseDateTime("wdalsjdaj"), null);
        assertEquals(DateTimeParser.parseDateTime("12/2024"), null);
        assertEquals(DateTimeParser.parseDateTime("245/2/2024"), null);
        assertEquals(DateTimeParser.parseDateTime("25/24/2024"), null);
        assertEquals(DateTimeParser.parseDateTime("25/2/22"), null);

        // valid date string
        assertEquals(DateTimeParser.parseDateTime("25/2/2024"),
                LocalDate.parse("25/2/2024", DateTimeFormatter.ofPattern("d/M/yyyy")));
        assertEquals(DateTimeParser.parseDateTime("2024-2-24"), LocalDate.parse("2024-2-24",
                DateTimeFormatter.ofPattern("yyyy-M-d")));
    }

    @Test
    public void outputDateTime() {
        assertEquals(
                DateTimeParser.outputDateTime(
                        LocalDate.parse("25/2/2024", DateTimeFormatter.ofPattern("d/M/yyyy"))),
                "2024-2-25");
        assertEquals(
                DateTimeParser.outputDateTime(
                        LocalDate.parse("2024-2-24", DateTimeFormatter.ofPattern("yyyy-M-d"))),
                "2024-2-24");
    }
}
