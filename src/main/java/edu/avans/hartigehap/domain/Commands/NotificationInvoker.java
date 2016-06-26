package edu.avans.hartigehap.domain.Commands;
/**
 *  Invoker for the command pattern
 * @author Maurits
 *
 */
import java.util.ArrayList;
import java.util.List;

import edu.avans.hartigehap.domain.NotificationCmdClient;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class NotificationInvoker {
	Command mailCommands;

	
	
	public NotificationInvoker(){


	}
	
	public void setCommand(Command cmd){
		mailCommands = cmd;
	}
	
	public void emailNotification(){
		log.info("sINVOKER EMAILNOTIFICATION AANGEROEPEN");
		mailCommands.execute();
	}
	public void smsNotification(){
		
	}
	public void fbNotification(){
	
}

}
