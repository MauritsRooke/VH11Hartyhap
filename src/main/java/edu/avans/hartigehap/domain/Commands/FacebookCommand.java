package edu.avans.hartigehap.domain.Commands;

import edu.avans.hartigehap.domain.Order;

/**
 * Facebook command, Doesnt have a implementation
 * Just to show that command pattern works
 * 
 * @author Maurits
 *
 */
public class FacebookCommand implements Command {
	Notification notify;
	public FacebookCommand(Notification notify){
		this.notify = notify;
	}
	
	public void execute(Order order){

		notify.setBody("FACEBOOK BODY VERZONDEN");

	}

}
