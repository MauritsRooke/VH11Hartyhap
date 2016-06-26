package edu.avans.hartigehap.domain.discount;

import edu.avans.hartigehap.domain.OrderItem;

import java.util.Collection;

/**
 * @author Yannick, XOXO.
 * 
 * Haalt een bepaald aantal hoeveelheid euro van een orderitem af
 */

public class SubstractDiscountCalculator extends DefaultDiscountCalculator {

    private float substractAmount;

    public SubstractDiscountCalculator(float substractAmount) {
        this.substractAmount = substractAmount;
    }

    public SubstractDiscountCalculator(){
        throw new IllegalArgumentException("geen substractAmount meegegeven");
    }

    @Override
    public float calculatePrice(Collection<OrderItem> orderList) {
    	float price = 0;
        for (OrderItem item : orderList) {
            price += item.getPrice() - (substractAmount * item.getQuantity());
        }
        return price;
    }
}
