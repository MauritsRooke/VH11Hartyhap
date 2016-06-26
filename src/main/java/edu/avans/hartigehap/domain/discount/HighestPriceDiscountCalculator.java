package edu.avans.hartigehap.domain.discount;

import edu.avans.hartigehap.domain.OrderItem;

import java.util.Collection;

/**
 * @author Yannick, XOXO
 * 
 * note; mag geen discount geven als er maar 1 orderitem is
 */
public class HighestPriceDiscountCalculator extends DefaultDiscountCalculator {

    @Override
    public float calculatePrice(Collection<OrderItem> orderList) {
    	float price = super.calculatePrice(orderList);
    	float highestPrice = 0;

        for (OrderItem item : orderList) {
            if (item.getPrice() > highestPrice) {
                highestPrice = item.getPrice();
            }
        }
        
        if (orderList.size() > 1) {
            price = price - highestPrice;
        }

        return price;
    }
}
