package edu.avans.hartigehap.domain.Commands;

import edu.avans.hartigehap.domain.Order;
/**
 * Mailcommand sends data to the notification class to send an email
 * @author Maurits
 *
 */
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailCommand implements Command {
	Notification notify;
	
	public MailCommand(Notification notify){
		this.notify = notify;
	}
	
	public void execute(Order order){
		log.info("COMMAND EXECUTE AANGEROEPEN");
		notify.setInfo(order.getBill().getCustomer().getEmail());
		notify.setHeader("Bevestiging Order");
		notify.setBody("Beste Dhr/ Mevr, "+ order.getBill().getCustomer().getLastName()+ " Hierbij bevestig ik uw order");
		notify.sendMail();
	}
}