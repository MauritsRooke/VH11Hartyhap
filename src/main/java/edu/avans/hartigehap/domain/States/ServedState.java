package edu.avans.hartigehap.domain.States;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.StateException;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue(ServedState.DISCRIMINATOR)
@NoArgsConstructor
public class ServedState extends OrderState {

	
	private static final long serialVersionUID = 1L;

	public static final String DISCRIMINATOR = "served";

	public ServedState(Order order) {
		super(order);
	}

	@Override
	public void submit() throws StateException {
		throw new StateException("not allowed to change order state to prepared, if it is not in the planned state");
		
	}

	@Override
	public void plan() throws StateException {
		throw new StateException("not allowed to plan an order that is not in the submitted state");
		
	}

	@Override
	public void prepared() throws StateException {
		throw new StateException("not allowed to change order state to prepared, if it is not in the planned state");
		
	}

	@Override
	public void served() throws StateException {
		throw new StateException("not allowed to change order state to served, if it is not in the prepared state");
		
	}
}
