package edu.avans.hartigehap.service.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.avans.hartigehap.service.*;
import edu.avans.hartigehap.domain.*;
import edu.avans.hartigehap.repository.BillRepository;
import edu.avans.hartigehap.repository.CustomerRepository;
import edu.avans.hartigehap.repository.MenuItemRepository;
import edu.avans.hartigehap.repository.OrderRepository;
/**
 * Due to the current setup, adding a standalone order won't work
 * So the newOrder method makes a new Bill, with an Anonymous customer who is coupled to a Restaurant
 * The Bill automatically makes a new Order and all OrderItems will be added to this
 * createOrder -> createBill -> createCustomer
 * 
 * Edited All the find methods so they include Online orders based on anonymous customers and not by table
 * @author Maurits
 *
 */
@Service("orderService")
@Repository
@Transactional(rollbackFor = StateException.class)
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RestaurantService restServ;

    @Transactional(readOnly = true)
    public Order findById(Long orderId) {
        return orderRepository.findOne(orderId);
    }

    // find all submitted orders (so for complete restaurant), ordered by submit
    // time
    // this method serves as an example of:
    // * a named query (using entityManager)
    // * a query created using a repository method name
    // * a repository with a custom method implementation
    @Transactional(readOnly = true)
    public List<Order> findSubmittedOrdersForRestaurant(Restaurant restaurant) {

        // a repository with a custom method implementation
        // the custom method implementation uses a named query which is
        // invoked using an entityManager
        List<Order> submittedOrdersList = orderRepository.findBymyStateStatusTypeAndBillDiningTableRestaurant("submitted",restaurant,new Sort(Sort.Direction.ASC, "submittedTime"));

        log.info("findSubmittedOrdersForRestaurant using named query");
        ListIterator<Order> it = submittedOrdersList.listIterator();
        while (it.hasNext()) {
            Order order = it.next();
            log.info("submittedOrder = " + order.getId() + ", for table = " + order.getBill().getDiningTable().getId()
                    + ", submitted time = " + order.getSubmittedTime());
        }

        // a query created using a repository method name
        List<Order> submittedOrdersListAlternative = orderRepository.findBymyStateStatusTypeAndBillDiningTableRestaurant(
                "submitted", restaurant, new Sort(Sort.Direction.ASC, "submittedTime"));

        log.info("findSubmittedOrdersForRestaurant using query created using repository method name");
        ListIterator<Order> italt = submittedOrdersListAlternative.listIterator();
        while (italt.hasNext()) {
            Order order = italt.next();
            log.info("submittedOrder = " + order.getId() + ", for table = " + order.getBill().getDiningTable().getId()
                    + ", submitted time = " + order.getSubmittedTime());
        }
        
        	//Adds onlineOrders with the submittedState wich cant be found with 
        	List<Order> allsubmittedOrders =  orderRepository.findBymyStateStatusType("submitted",new Sort(Sort.Direction.ASC, "submittedTime"));
        	for(Order order : allsubmittedOrders){
        		if(order.getOnlineID() != null){
        			Collection<Restaurant> restList =	 order.getBill().getCustomer().getRestaurants();
        			for(Restaurant rest : restList){
        				if(rest.getId().equals(restaurant.getId())){
        					submittedOrdersList.add(order);
        				}
        			}
        			
        		}
        	}
        
        return submittedOrdersList;
    }

    @Transactional(readOnly = true)
    public List<Order> findPlannedOrdersForRestaurant(Restaurant restaurant) {

    	List<Order> plannedOrders = orderRepository.findBymyStateStatusTypeAndBillDiningTableRestaurant(
                "planned", restaurant, new Sort(Sort.Direction.ASC, "plannedTime"));
    	log.info(String.valueOf(plannedOrders.size()));
    	//Adds onlineOrders with the planned State wich cant be found with normal Query
    	List<Order> allPlannedOrders =  orderRepository.findBymyStateStatusType("planned",new Sort(Sort.Direction.ASC, "plannedTime"));
    	for(Order order : allPlannedOrders){
    		if(order.getOnlineID() != null){
    			Collection<Restaurant> restList =	 order.getBill().getCustomer().getRestaurants();
    			for(Restaurant rest : restList){
    				if(rest.getId().equals(restaurant.getId())){
    					log.info("ERGENS IN DE LOOP"+rest.getId());
    					plannedOrders.add(order);
    				}
    			}
    			
    		}
    	}   	
    	
        return plannedOrders;
    }

    @Transactional(readOnly = true)
    public List<Order> findPreparedOrdersForRestaurant(Restaurant restaurant) {
    	List<Order> preparedOrders = orderRepository.findBymyStateStatusTypeAndBillDiningTableRestaurant(
                "prepared", restaurant, new Sort(Sort.Direction.ASC, "preparedTime"));
    	List<Order> allPreparedOrders =  orderRepository.findBymyStateStatusType("prepared",new Sort(Sort.Direction.ASC, "preparedTime"));
    	for(Order order : allPreparedOrders){
    		if(order.getOnlineID() != null){
    			Collection<Restaurant> restList =order.getBill().getCustomer().getRestaurants();
    			for(Restaurant rest : restList){
    				if(rest.getId().equals(restaurant.getId())){

    					preparedOrders.add(order);
    				}
    			}
    			
    		}
    	}
    	
    	
        return preparedOrders;
    }
    
    public void addOrderItem(String onlineOrderID, String menuItemName,String restaurantName) {
        MenuItem menuItem = menuItemRepository.findOne(menuItemName);
       Order ordertoadd = getOrderByOnlineID(onlineOrderID);   	   
       if(ordertoadd != null){
    	   log.info("er is een order gevonden");
    	   ordertoadd.addOrderItem(menuItem);
       }
       else{    	  
    	   Order order = newOrder(onlineOrderID, restaurantName);   	   
    	   order.addOrderItem(menuItem);   	 
    	   List<Order> submittedOrdersList = orderRepository.findBymyStateStatusType("created",new Sort(Sort.Direction.ASC, "submittedTime")); 	
    	   
    	   log.info("AAAAAAAAAAAAYYYYYYYYYYYYY"+String.valueOf(submittedOrdersList.size()));
       }
      
    }
    
    public Order newOrder(String onlineOrderID, String restaurantName){
    	Bill bill = new Bill();
    	Order order = bill.getCurrentOrder();
    	Restaurant rest =  restServ.findById(restaurantName);
    	Customer anon = newCustomer(rest);
    	bill.setCustomer(anon);
    	order.setOnlineID(onlineOrderID);
    	orderRepository.save(order);
    	billRepository.save(bill);
    	return order;
    }
    
    public Customer newCustomer(Restaurant rest){
    	Customer anon = new Customer();
    	anon.setFirstName("Anon");
    	anon.setLastName("ymous");
    	anon.getRestaurants().add(rest);
    	customerRepository.save(anon);
    	return anon;
    }
    
    public void deleteOrderItem(String onlineOrderID, String menuItemName) {
        MenuItem menuItem = menuItemRepository.findOne(menuItemName);
        Order order = getOrderByOnlineID(onlineOrderID);
        order.deleteOrderItem(menuItem);
      
    }
    
    public Order getOrderByOnlineID(String onlineOrderID){
    	for(Order order : orderRepository.findAll()){
     	   if(order.getOnlineID() != null && order.getOnlineID().equals(onlineOrderID) == true ){
     		   return order;
     	   }
        }
		return null;
    }
    public void planOrder(Order order) throws StateException {
        order.plan();
    }

    public void orderPrepared(Order order) throws StateException {
        order.prepared();
    }

    public void orderServed(Order order) throws StateException {
        order.served();
    }

	@Override
	public void submitOrder(Order order) throws StateException {
		order.submit();
		
	}
}
