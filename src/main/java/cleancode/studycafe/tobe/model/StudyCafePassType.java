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


    public static StudyCafePassType fromInput(String userInput) {
        switch (userInput) {
            case "1": return HOURLY;
            case "2": return WEEKLY;
            case "3": return FIXED;
            default: throw new IllegalArgumentException("Invalid input: " + userInput);
        }
    }
}
