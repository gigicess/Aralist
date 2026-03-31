package model;

public enum Categorize {
    WORK, SCHOOL, PERSONAL, SHOPPING, FITNESS, OTHER;

    public static Categorize fromString(String input) {
        for (Categorize c : Categorize.values()) {
            if (c.name().equalsIgnoreCase(input)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid category: " + input);
    }

}