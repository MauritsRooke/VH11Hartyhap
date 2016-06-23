package edu.avans.hartigehap.service.impl;


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
import edu.avans.hartigehap.repository.MenuItemRepository;
import edu.avans.hartigehap.repository.OrderRepository;

@Service("orderService")
@Repository
@Transactional(rollbackFor = StateException.class)
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

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

        return submittedOrdersList;
    }

    @Transactional(readOnly = true)
    public List<Order> findPlannedOrdersForRestaurant(Restaurant restaurant) {
        // a query created using a repository method name
        return orderRepository.findBymyStateStatusTypeAndBillDiningTableRestaurant(
                "planned", restaurant, new Sort(Sort.Direction.ASC, "plannedTime"));
    }

    @Transactional(readOnly = true)
    public List<Order> findPreparedOrdersForRestaurant(Restaurant restaurant) {
        // a query created using a repository method name
        return orderRepository.findBymyStateStatusTypeAndBillDiningTableRestaurant(
                "prepared", restaurant, new Sort(Sort.Direction.ASC, "preparedTime"));
    }
    
    public void addOrderItem(String onlineOrderID, String menuItemName) {
    	log.info("addOrderItem aangeroepen");
        MenuItem menuItem = menuItemRepository.findOne(menuItemName);
       Order ordertoadd = getOrderByOnlineID(onlineOrderID);   	   
       if(ordertoadd != null){
    	   log.info("er is een order gevonden");
    	   ordertoadd.addOrderItem(menuItem);
       }
       else{
    	   Order order = new Order();
    	   order.setOnlineID(onlineOrderID);
    	   order.addOrderItem(menuItem);
    	   orderRepository.save(order);
    	   log.info("nieuwe order gemaakt");
       }
      
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
}
