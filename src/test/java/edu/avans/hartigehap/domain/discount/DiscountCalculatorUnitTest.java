package edu.avans.hartigehap.domain.discount;


import edu.avans.hartigehap.domain.Meal;
import edu.avans.hartigehap.domain.MenuItem;
import edu.avans.hartigehap.domain.OrderItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Yannick, XOXO.
 */

public class DiscountCalculatorUnitTest {

    private Collection<OrderItem> orderItems;
    private Collection<OrderItem> orderItems2;
    
    private void setUp() {

    	//Testset 1
        MenuItem m1 = new Meal();
        m1.setPrice(10);
        MenuItem m2 = new Meal();
        m2.setPrice(30);

        OrderItem item1 = new OrderItem();
        item1.setMenuItem(m1);
        item1.setQuantity(1);
        OrderItem item2 = new OrderItem();
        item2.setMenuItem(m2);
        item2.setQuantity(1);

        orderItems = new ArrayList<>();
        orderItems.add(item1);
        orderItems.add(item2);
        
        
        //testset 2
        Meal m3 = new Meal();
        m3.setPrice(12);
        
        OrderItem item3 = new OrderItem();
        item3.setMenuItem(m3);
        item3.setQuantity(2);
        
        orderItems2 = new ArrayList<>();
        orderItems2.add(item3);
    }


    @Test
    public void testDefaultDiscountCalculator() {
        setUp();
        IDiscountCalculator calculator = new DefaultDiscountCalculator();
        float price = calculator.calculatePrice(orderItems);
        float price2 = calculator.calculatePrice(orderItems2);
        
        assertEquals(40, price, 0.001);
        assertEquals(24, price2, 0.001);
    }
    
    @Test
    public void testPercentageDiscountCalculatorException() {

        try {
            IDiscountCalculator calculator = new PercentageDiscountCalculator();
        } catch (IllegalArgumentException e){
            assertTrue("true", true);
            return;
        }
        assertTrue("false", false);
    }

    @Test
    public void testPercentageDiscountCalculator() {
        setUp();
        IDiscountCalculator calculator = new PercentageDiscountCalculator(80);
        IDiscountCalculator calculator2 = new PercentageDiscountCalculator(50);
        
        float price = calculator.calculatePrice(orderItems);
        float price2 = calculator.calculatePrice(orderItems2);
        float price3 = calculator2.calculatePrice(orderItems);
        float price4 = calculator2.calculatePrice(orderItems2);
        
        assertEquals(32, price, 0.001);
        assertEquals(19.2, price2, 0.001);
        assertEquals(20, price3, 0.001);
        assertEquals(12, price4, 0.001);
    }

    @Test
    public void testHighestPriceDiscountCalculator() {
        setUp();
        IDiscountCalculator calculator = new HighestPriceDiscountCalculator();
        float price = calculator.calculatePrice(orderItems);
        float price2 = calculator.calculatePrice(orderItems2);
        
        assertEquals(10, price, 0.001);
        assertEquals(24, price2, 0.001);
    }
    
    @Test
    public void testSubstractDiscountCalculatorException() {

        try {
            IDiscountCalculator calculator = new SubstractDiscountCalculator();
        } catch (IllegalArgumentException e){
            assertTrue("true", true);
            return;
        }
        assertTrue("false", false);
    }
    
    @Test
    public void testSubstractDiscountCalculator() {
        setUp();
        IDiscountCalculator calculator = new SubstractDiscountCalculator((float)0.50);
        IDiscountCalculator calculator2 = new SubstractDiscountCalculator((float)1);
        
        float price = calculator.calculatePrice(orderItems);
        float price2 = calculator.calculatePrice(orderItems2);
        float price3 = calculator2.calculatePrice(orderItems);
        float price4 = calculator2.calculatePrice(orderItems2);
        
        assertEquals(39, price, 0.001);
        assertEquals(23.5, price2, 0.001);
        assertEquals(38, price3, 0.001);
        assertEquals(23, price4, 0.001);
    }

}
