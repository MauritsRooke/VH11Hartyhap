package edu.avans.hartigehap.domain.Commands;

public class FacebookCommand implements Command {
	Notification notify;
	public FacebookCommand(Notification notify){
		this.notify = notify;
	}
	
	public void execute(){

		notify.setBody("FACEBOK BODY VERZONDEN");

	}

}
