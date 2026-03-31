package model;

public enum Priority {
    HIGH(1),
    MEDIUM(2),
    LOW(3);

    private final int level;
    Priority(int level) { this.level = level; }
    public int getLevel() { return level; }

    public static Priority fromString(String input) {
        for (Priority p : Priority.values()) {
            if (p.name().equalsIgnoreCase(input)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Invalid category: " + input);
    }
}
