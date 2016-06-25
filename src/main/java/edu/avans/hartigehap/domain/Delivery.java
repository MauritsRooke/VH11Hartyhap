package edu.avans.hartigehap.domain;








import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Delivery extends DomainObject {
	private static final long serialVersionUID = 1L;

    private String street;
	
	private int houseNmbr;
	
	private String zipCode;
	
	private String City;

	private String name;
 
	private String email;
	private String phoneNmbr;
	 
	private String deliveryTime;
	 
	private String comments;
	 
	//private Order currentOrder;
}
