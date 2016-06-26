package edu.avans.hartigehap.domain.discount;

import java.util.Collection;
import edu.avans.hartigehap.domain.OrderItem;

/**
 * @author Yannick, XOXO.
 * 
 * Gewone prijsberekening
 */
public class DefaultDiscountCalculator implements IDiscountCalculator {

    @Override
    public float calculatePrice(Collection<OrderItem> orderList) {
    	
    	float price = 0;
        for (OrderItem item : orderList) {
            price += item.getPrice();
        }
        return price;
    }
}
