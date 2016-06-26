package edu.avans.hartigehap.domain.discount;

import java.util.Collection;
import edu.avans.hartigehap.domain.OrderItem;

/**
 * @author Yannick, XOXO.
 *
 * Interface die geimplementeerd wordt om verschillende discounts uit te rekenen
 */

public interface IDiscountCalculator {
	
	float calculatePrice(Collection<OrderItem> orderList);
}
