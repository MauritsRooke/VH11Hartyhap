package edu.avans.hartigehap.domain.discount;

import java.util.Calendar;

/**
 * @author Yannick, XOXO.
 * 
 * Factory om verschillende discount strategies mee aan te maken
 * Maakt een discount aan gebaseerd op de dag
 * Deze factory is een singleton daarom is de contructor private
 */

public class DiscountCalculatorFactory {

    private static DiscountCalculatorFactory instance;

    private DiscountCalculatorFactory() {
    }

    public static synchronized DiscountCalculatorFactory getInstance() {
        if (instance == null) {
            instance = new DiscountCalculatorFactory();
        }
        return instance;
    }

    public IDiscountCalculator createDiscount() {

        IDiscountCalculator calculator = null;
        int dayOfTheWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        switch (dayOfTheWeek) {
            case Calendar.MONDAY:
            	calculator = new SubstractDiscountCalculator((float)0.50);
            case Calendar.TUESDAY:
            case Calendar.WEDNESDAY:
            	calculator = new PercentageDiscountCalculator(90);
                break;
            case Calendar.THURSDAY:
            case Calendar.FRIDAY:
            	calculator = new HighestPriceDiscountCalculator();
            	break;
            case Calendar.SATURDAY:
            	calculator = new PercentageDiscountCalculator(85);
            	break;
            case Calendar.SUNDAY:
                calculator = new PercentageDiscountCalculator(75);
                break;
            default:
                calculator = new DefaultDiscountCalculator();
                break;
        }
        return calculator;
    }
}
