package edu.avans.hartigehap.domain.Commands;
/**
 * Sms command to show the command pattern works
 * Doesnt have a real implementation
 * @author Maurits
 *
 */
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsCommand implements Command {
	Notification notify;
	public SmsCommand(Notification notify){
		this.notify = notify;
	}

	@Override
	public void execute(){
		log.info("COMMAND EXECUTE AANGEROEPEN");
		
		notify.setBody("SMS BODY NEERGEZET");
		
	}

}
