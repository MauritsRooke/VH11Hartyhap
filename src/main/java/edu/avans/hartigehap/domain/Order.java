package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.States.*;

/**
 * 
 * @author Erco
 */
@Entity

// to prevent collision with MySql reserved keyword
@Table(name = "ORDERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = { "orderItems" })
public class Order extends DomainObject {
    private static final long serialVersionUID = 1L;

   
    @OneToOne(orphanRemoval=true, cascade=CascadeType.ALL)
    public OrderState myState;


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
    

    public Order() {
        myState = new CreatedState(this);
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
    }

    public void plan() throws StateException {
      myState.plan();
    }

    public void prepared() throws StateException {

    	myState.prepared();
    }

    public void served() throws StateException {

    	myState.served();
    }

    @Transient
    public int getPrice() {
        int price = 0;
        Iterator<OrderItem> orderItemIterator = orderItems.iterator();
        while (orderItemIterator.hasNext()) {
            price += orderItemIterator.next().getPrice();
        }
        return price;
    }
    

}
