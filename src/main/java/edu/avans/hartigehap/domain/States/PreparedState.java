package edu.avans.hartigehap.domain.States;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.StateException;
import lombok.NoArgsConstructor;
@Entity
@DiscriminatorValue(PreparedState.DISCRIMINATOR)
@NoArgsConstructor
public class PreparedState extends OrderState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String DISCRIMINATOR = "prepared";

	public PreparedState(Order order) {
		super(order);
	}

	@Override
	public void submit() throws StateException {
		throw new StateException("not allowed to change order state to submitted, if it is not in the created state");
		
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
		 order.setServedTime(new Date());
		 order.setMyState(new ServedState(order));
		
	}
}
