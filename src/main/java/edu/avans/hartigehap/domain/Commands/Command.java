package edu.avans.hartigehap.domain.Commands;

import edu.avans.hartigehap.domain.Order;

/**
 * Command Interface
 * @author Maurits
 *
 */
public interface Command {
	public void execute(Order order);

}
