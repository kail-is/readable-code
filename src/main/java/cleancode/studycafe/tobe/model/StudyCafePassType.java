package cleancode.studycafe.tobe.model;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권", "1", "%s시간권 - %d원"),
    WEEKLY("주 단위 이용권", "2", "%s주권 - %d원"),
    FIXED("1인 고정석", "3", "%s주권 - %d원");

    private final String description;
    private final String number;
    private final String formatString;

    StudyCafePassType(String description, String number, String formatString) {
        this.description = description;
        this.number = number;
        this.formatString = formatString;
    }

    public String getFormatString() {
        return formatString;
    }

    public boolean isHourly() {
        return this == HOURLY;
    }

    public boolean isWeekly() {
        return this == WEEKLY;
    }

    public boolean isFixed() {
        return this == FIXED;
    }
    public static StudyCafePassType fromInput(String userInput) {
        return switch (userInput) {
            case "1" -> HOURLY;
            case "2" -> WEEKLY;
            case "3" -> FIXED;
            default -> throw new IllegalArgumentException("Invalid input: " + userInput);
        };
    }
}
