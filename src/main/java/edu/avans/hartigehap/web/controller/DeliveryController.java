package edu.avans.hartigehap.web.controller;
/**
 * Controller for all deliveries
 * @author Maurits
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Delivery;
import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.service.DeliveryService;
import edu.avans.hartigehap.service.OrderService;
import edu.avans.hartigehap.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DeliveryController {
	@Autowired
	 private RestaurantService restaurantService;
	 @Autowired
	 private OrderService orderService;
	 @Autowired
	 private DeliveryService deliveryService;
	
	 @RequestMapping(value = { "/restaurants/{restaurantName}/delivery" }, method = RequestMethod.GET)
	    public String getDelivery(@PathVariable("restaurantName") String restaurantName, Model uimodel,@CookieValue("HartigeHapOrderID") String orderID) {
	    setOrderModel(restaurantName,uimodel,orderID);
	    
    	return "hartigehap/delivery";
    }
	 
	 @RequestMapping(value = { "/restaurants/{restaurantName}/delivery" }, method = RequestMethod.POST)
	 public String setDelivery(@PathVariable("restaurantName") String restaurantName, Model uimodel,@ModelAttribute("customerForm") Customer customer,@CookieValue("HartigeHapOrderID") String orderID) throws StateException {
		 
		Order order = orderService.getOrderByOnlineID(orderID);
		deliveryService.addCustomertoOrder(order, customer,restaurantName);
		orderService.submitOrder(order);
		
		 return "redirect:/restaurants/";
	 }
	 public void setOrderModel(String restName,Model uiModel, String onlineOrderID){
	    	Restaurant restaurant = restaurantService.fetchWarmedUp(restName);
	        uiModel.addAttribute("restaurant", restaurant);
	        Order order = orderService.getOrderByOnlineID(onlineOrderID);
	        uiModel.addAttribute("order", order);
	        Customer customer = new Customer();
	        uiModel.addAttribute("customerForm", customer);
	       
	    }

}
