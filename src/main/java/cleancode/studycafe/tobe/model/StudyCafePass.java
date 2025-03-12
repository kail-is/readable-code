package cleancode.studycafe.tobe.model;

import cleancode.studycafe.tobe.display.DisplayStrategy;
import cleancode.studycafe.tobe.display.FixedDisplayStrategy;
import cleancode.studycafe.tobe.display.HourlyDisplayStrategy;
import cleancode.studycafe.tobe.display.WeeklyDisplayStrategy;

public class StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final double discountRate;
    private DisplayStrategy displayStrategy;

    private StudyCafePass(StudyCafePassType passType, int duration, int price, double discountRate) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
        setDisplayStrategy(passType);
    }

    public static StudyCafePass of(StudyCafePassType passType, int duration, int price, double discountRate) {
        return new StudyCafePass(passType, duration, price, discountRate);
    }

    public StudyCafePassType getPassType() {
        return passType;
    }

    public int getDuration() {
        return duration;
    }

    public int getPrice() {
        return price;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    private void setDisplayStrategy(StudyCafePassType passType) {
        switch (passType) {
            case HOURLY:
                this.displayStrategy = new HourlyDisplayStrategy();
                break;
            case WEEKLY:
                this.displayStrategy = new WeeklyDisplayStrategy();
                break;
            case FIXED:
                this.displayStrategy = new FixedDisplayStrategy();
                break;
            default:
                this.displayStrategy = (duration, price) -> "";
        }
    }

}
