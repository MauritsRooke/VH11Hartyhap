package edu.avans.hartigehap.domain.Commands;
/**
 *  Invoker for the command pattern
 * @author Maurits
 *
 */
import java.util.ArrayList;
import java.util.List;

import edu.avans.hartigehap.domain.NotificationCmdClient;
import edu.avans.hartigehap.domain.Order;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class NotificationInvoker {
	Command command;

	
	
	public NotificationInvoker(){


	}
	
	public void setCommand(Command cmd){
		command = cmd;
	}
	
	public void emailNotification(Order order){
		command.execute(order);
	}
	public void smsNotification(Order order){
		command.execute(order);
	}
	public void fbNotification(Order order){
		command.execute(order);
}

}
