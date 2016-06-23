package edu.avans.hartigehap.domain.States;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.StateException;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue(SubmittedState.DISCRIMINATOR)
@NoArgsConstructor
public class SubmittedState extends OrderState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String DISCRIMINATOR = "submitted";

	public SubmittedState(Order order) {
		super(order);
	}

	@Override
	public void submit() throws StateException {
		throw new StateException("not allowed to change order state to submitted, if it is not in the created state");
		
	}

	@Override
	public void plan() throws StateException {
		order.setPlannedTime(new Date());
		order.setMyState(new PlannedState(order));	
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
