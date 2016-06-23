package edu.avans.hartigehap.domain.States;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DiscriminatorOptions;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.DomainObject;
import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.StateException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Inheritance
@DiscriminatorColumn(name = "type")
@DiscriminatorOptions(force = true)
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public abstract class OrderState extends DomainObject {
	private static final long serialVersionUID = 1L;
	
	// Map the discriminator value as a read-only property
		@Column(name="type", nullable=false, updatable=false, insertable=false)
		private String statusType;
		
		
		@OneToOne(mappedBy="myState")
		protected Order order;
		
		public OrderState(Order order){
			this.order = order;
			
		}
		
		public abstract void submit() throws StateException;
		public abstract void plan() throws StateException;
		public abstract void prepared() throws StateException;
		public abstract void served() throws StateException;
}
