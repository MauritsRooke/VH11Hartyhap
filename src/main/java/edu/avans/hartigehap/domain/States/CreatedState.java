package edu.avans.hartigehap.domain.States;
/**
 * Created state is the first State an Order will be
 * Only has working submit to change State
 * @author Maurits
 *
 */
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.StateException;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue(CreatedState.DISCRIMINATOR)
@NoArgsConstructor
public class CreatedState extends OrderState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String DISCRIMINATOR = "created";
	
	public CreatedState(Order order){
		super(order);
		setStatusType(DISCRIMINATOR);
	}

	@Override
	public void submit() throws StateException {
		order.setSubmittedTime(new Date());
		order.setMyState(new SubmittedState(order));		
		
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
