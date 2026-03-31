package util;

import exceptions.InvalidDateException;
import exceptions.TypeExceptions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateHelper {
    // Formatters
    private static final DateTimeFormatter fullDash = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private static final DateTimeFormatter fullSlash = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter shortDash = DateTimeFormatter.ofPattern("MM-dd");
    private static final DateTimeFormatter shortSlash = DateTimeFormatter.ofPattern("MM/dd");

    // Parse user input
    public static LocalDate parseDate(String input) throws InvalidDateException, TypeExceptions {
        LocalDate result = null;

        try {
        if (input.matches(".*\\d{4}.*")) {
            try {
                return LocalDate.parse(input, fullDash);
            } catch (DateTimeParseException e1) {
                return LocalDate.parse(input, fullSlash);
            }
        } else {
            try {
                return LocalDate.parse(input, shortDash)
                                 .withYear(LocalDate.now().getYear());
            } catch (DateTimeParseException e2) {
                return LocalDate.parse(input, shortSlash)
                                 .withYear(LocalDate.now().getYear());
            }
        }
    } catch (DateTimeParseException e) {
        throw new TypeExceptions("Invalid date format. Use MM-dd, MM/dd, or include year.");
    }
}


    // Format date for display
    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
    }
}