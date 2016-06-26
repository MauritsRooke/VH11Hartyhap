package edu.avans.hartigehap.service;
import edu.avans.hartigehap.domain.Customer;
/**
 * Delivery service interface only with a save method
 * @author Maurits
 */
import edu.avans.hartigehap.domain.Delivery;
import edu.avans.hartigehap.domain.Order;

public interface DeliveryService {

	void saveDelivery(Delivery delivery);
	void addCustomertoOrder(Order order, Customer customer,String restaurantName);
}
