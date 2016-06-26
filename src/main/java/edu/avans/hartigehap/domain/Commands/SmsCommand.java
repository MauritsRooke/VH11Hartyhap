package edu.avans.hartigehap.domain.Commands;
import edu.avans.hartigehap.domain.Order;
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
	public void execute(Order order){
		log.info("COMMAND EXECUTE AANGEROEPEN");
		notify.setInfo(order.getBill().getCustomer().getPhone());
		notify.setBody("SMS BODY");
		notify.sendText();
	}

}
