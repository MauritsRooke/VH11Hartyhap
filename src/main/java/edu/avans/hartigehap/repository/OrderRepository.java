package edu.avans.hartigehap.repository;
/**
 * Custom Order repositories deleted, these became obsolete because the order changed to State pattern
 * First method changed to work with the new State pattern
 * @author Maurits
 */
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.*;
import edu.avans.hartigehap.domain.*;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    // Spring creates an implementation based one the method name
    // in this case all orders for which:
    // order.getOrderStatus() == orderStatus
    // AND
    // order.getBill().getDiningTable().getRestaurant() == restaurant
   // List<Order> findByOrderStatusAndBillDiningTableRestaurant(Order.OrderStatus orderStatus, Restaurant restaurant,
   //         Sort sort);
    
    List<Order> findBymyStateStatusTypeAndBillDiningTableRestaurant(String orderStatus, Restaurant restaurant,Sort sort);
    
    //Returns all Orders with a specific State
    List<Order> findBymyStateStatusType(String orderStatus,Sort sort);

}
