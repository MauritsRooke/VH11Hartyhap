/**
 * Delivery service implementation only with a save method
 * @author Maurits
 */package edu.avans.hartigehap.service.impl;

import java.awt.List;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Delivery;
import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.repository.*;
import edu.avans.hartigehap.service.*;
import lombok.extern.slf4j.Slf4j;
@Service("DeliveryService")
@Repository
@Transactional
@Slf4j
public class DeliverySerivceImpl implements DeliveryService {
	
	@Autowired
    private DeliveryRepository deliveryRepo;
	 @Autowired
	    private RestaurantService restServ;
	 @Autowired
	  private CustomerRepository customerRepository;
	
	public void saveDelivery(Delivery delivery){
		
		deliveryRepo.save(delivery);
	}
	
	public void addCustomertoOrder(Order order, Customer customer,String restaurantName){
		Restaurant rest =  restServ.findById(restaurantName);
		order.getBill().setCustomer(customer);
		order.getBill().getCustomer().getRestaurants().add(rest);
		customer.getBills().add(order.getBill());
		customerRepository.save(order.getBill().getCustomer());
		customerRepository.save(customer);
		log.info(String.valueOf(order.getBill().getCustomer().getRestaurants().size()));
	}

}
