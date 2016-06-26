package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.States.*;
import edu.avans.hartigehap.domain.discount.DiscountCalculatorFactory;
import edu.avans.hartigehap.domain.discount.IDiscountCalculator;

/**
 * Order object Now has a State pattern to keep track of states
 * Observer pattern added
 * @author Maurits
 *
 */
@Entity

// to prevent collision with MySql reserved keyword
@Table(name = "ORDERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = { "orderItems" })
@Slf4j
public class Order extends DomainObject implements Subject {
    private static final long serialVersionUID = 1L;

   
    @OneToOne(orphanRemoval=true, cascade=CascadeType.ALL)
    private OrderState myState;


    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date plannedTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date servedTime;

    // unidirectional one-to-many relationship.
    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    private Collection<OrderItem> orderItems = new ArrayList<OrderItem>();

    @ManyToOne()
    private Bill bill;

    private String OnlineID;
    @Transient
    private ArrayList<OrderStateObserver> observers;

    public Order() {
        myState = new CreatedState(this);
        observers = new ArrayList<>();
        NotificationCmdClient not = new NotificationCmdClient(this);
    }

    
    /* business logic */

    @Transient
    public boolean isSubmittedOrSuccessiveState() {
    	
        return myState.getStatusType() != "created";
    }

    // transient annotation, because methods starting with are recognized by JPA
    // as properties
    @Transient
    public boolean isEmpty() {
        return orderItems.isEmpty();
    }

    public void addOrderItem(MenuItem menuItem) {
        Iterator<OrderItem> orderItemIterator = orderItems.iterator();
        boolean found = false;
        while (orderItemIterator.hasNext()) {
            OrderItem orderItem = orderItemIterator.next();
            if (orderItem.getMenuItem().equals(menuItem)) {
                orderItem.incrementQuantity();
                found = true;
                break;
            }
        }
        if (!found) {
            OrderItem orderItem = new OrderItem(menuItem, 1);
            orderItems.add(orderItem);
        }
    }

    public void deleteOrderItem(MenuItem menuItem) {
        Iterator<OrderItem> orderItemIterator = orderItems.iterator();
        boolean found = false;
        while (orderItemIterator.hasNext()) {
            OrderItem orderItem = orderItemIterator.next();
            if (orderItem.getMenuItem().equals(menuItem)) {
                found = true;
                if (orderItem.getQuantity() > 1) {
                    orderItem.decrementQuantity();
                } else {
                    // orderItem.getQuantity() == 1
                    orderItemIterator.remove();
                }
                break;
            }
        }
        if (!found) {
            // do nothing
        }
    }

    public void submit() throws StateException {
        if (isEmpty()) {
            throw new StateException("not allowed to submit an empty order");
        }
        myState.submit();
        if(OnlineID != null){
          notifyObservers();
        }
      
    }

    public void plan() throws StateException {
      myState.plan();
     
    }

    public void prepared() throws StateException {

    	myState.prepared();
    	 if(OnlineID != null){
             notifyObservers();
           }
    }

    public void served() throws StateException {

    	myState.served();
   
    }

    @Transient
    public float getPrice() {
        IDiscountCalculator dc = DiscountCalculatorFactory.getInstance().createDiscount();
    	return dc.calculatePrice(orderItems);
    }


	@Override
	public void registerObserver(OrderStateObserver o) {
		observers.add(o);
		
	}


	@Override
	public void removeObserver(OrderStateObserver o) {
		int i = observers.indexOf(o);
		if (i >= 0) {
		observers.remove(i);
		}
	}


	@Override
	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			OrderStateObserver observer = (OrderStateObserver)observers.get(i);
			observer.update(this);
			}
		
	}
    

}
