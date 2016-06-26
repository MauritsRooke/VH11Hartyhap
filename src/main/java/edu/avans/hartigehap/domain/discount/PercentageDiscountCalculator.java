package edu.avans.hartigehap.domain.discount;

import edu.avans.hartigehap.domain.OrderItem;

import java.util.Collection;

/**
 * @author Yannick, XOXO.
 */

public class PercentageDiscountCalculator extends DefaultDiscountCalculator {

    private int discountPercentage;

    public PercentageDiscountCalculator(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public PercentageDiscountCalculator(){
        throw new IllegalArgumentException("geen discountPercentage meegegeven");
    }

    @Override
    public float calculatePrice(Collection<OrderItem> orderList) {
        //BigDecimal price = super.calculatePrice(orderList);
        //return price.multiply(new BigDecimal(discountPercentage));
    	float price = super.calculatePrice(orderList) * discountPercentage / 100;
    	return price;
    }
}
