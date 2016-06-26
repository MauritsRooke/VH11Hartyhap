package edu.avans.hartigehap.domain.Commands;
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
	
	public void execute(){

		notify.setBody("FACEBOOK BODY VERZONDEN");

	}

}
