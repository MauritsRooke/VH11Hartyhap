package edu.avans.hartigehap.service;
/**
 * Delivery service interface only with a save method
 * @author Maurits
 */
import edu.avans.hartigehap.domain.Delivery;

public interface DeliveryService {

	void saveDelivery(Delivery delivery);
}
