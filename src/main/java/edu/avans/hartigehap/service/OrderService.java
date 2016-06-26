package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.*;

public interface OrderService {
    Order findById(Long orderId);

    List<Order> findSubmittedOrdersForRestaurant(Restaurant restaurant);

    List<Order> findPlannedOrdersForRestaurant(Restaurant restaurant);

    List<Order> findPreparedOrdersForRestaurant(Restaurant restaurant);
    
    void addOrderItem(String onlineOrderID, String menuItemName,String restaurantName);

    void planOrder(Order order) throws StateException;
    void submitOrder(Order order) throws StateException;

    void orderPrepared(Order order) throws StateException;

    void orderServed(Order order) throws StateException;

    Order getOrderByOnlineID(String onlineOrderID);
    
    void deleteOrderItem(String onlineOrderID, String menuItemName);
}
