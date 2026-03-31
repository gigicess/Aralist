package util;

import exceptions.TypeExceptions;

public class InputHelper {

    // Case-insensitive enum lookup
    public static <E extends Enum<E>> E parseEnum(Class<E> enumClass, String input) throws TypeExceptions {
        for (E constant : enumClass.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(input.trim())) {
                return constant;
            }
        }
        throw new TypeExceptions("Invalid value: " + input + ". Expected one of: " +
                java.util.Arrays.toString(enumClass.getEnumConstants()));
    }

    // Safe integer parsing
    public static int parseInt(String input) throws TypeExceptions {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new TypeExceptions("Invalid number: " + input);
        }
    }
}