package util;

import exceptions.InvalidDateException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateHelper {
    //Allows both - or / 
    private static final DateTimeFormatter fullDash = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private static final DateTimeFormatter fullSlash = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //User override for automatic year
    private static final DateTimeFormatter shortDash = DateTimeFormatter.ofPattern("MM-dd"); //Automatic year
    private static final DateTimeFormatter shortSlash = DateTimeFormatter.ofPattern("MM/dd");

    //Parse user input
    public static LocalDate parseDate(String input) throws InvalidDateException {
        LocalDate result = null;
        try {
            result = LocalDate.parse(input, fullDash);
        } catch (DateTimeParseException e1) {
            try {
                result = LocalDate.parse(input, fullSlash);
            } catch (DateTimeParseException e2) {
                try {
                    // Short formats (default year = current year)
                    result = LocalDate.parse(input, shortDash)
                                      .withYear(LocalDate.now().getYear());
                } catch (DateTimeParseException e3) {
                    try {
                        result = LocalDate.parse(input, shortSlash)
                                          .withYear(LocalDate.now().getYear());
                    } catch (DateTimeParseException e4) {
                        throw new InvalidDateException(
                            "Invalid date format. Use MM-dd, MM/dd, or include year."
                        );
                    }
                }
            }
        }
        return result;
    }

    

    //Format date for display
    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
    }
}