package edu.avans.hartigehap.domain;


import java.util.ArrayList;
import java.util.List;

import edu.avans.hartigehap.domain.Commands.Command;
import edu.avans.hartigehap.domain.Commands.FacebookCommand;
import edu.avans.hartigehap.domain.Commands.MailCommand;
import edu.avans.hartigehap.domain.Commands.Notification;
import edu.avans.hartigehap.domain.Commands.NotificationInvoker;
import edu.avans.hartigehap.domain.Commands.SmsCommand;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationCmdClient implements OrderStateObserver{
	private Subject order;
	private NotificationInvoker invoker;
	Notification notify;
	MailCommand mailCMD;
	SmsCommand smsCMD;
	FacebookCommand fbCMD;
	
	public NotificationCmdClient(Subject order){
		this.order = order;
		order.registerObserver(this);
		invoker = new NotificationInvoker();
		notify = new Notification();
		mailCMD = new MailCommand(notify);
		smsCMD = new SmsCommand(notify);
		fbCMD = new FacebookCommand(notify);
	}

	@Override
	public void update(Order order) {
		// TODO Auto-generated method stub
		log.info("UPDATE METHODE AANGEROEPEN"+ order.getMyState().getStatusType());
		switch (order.getMyState().getStatusType()){
		case "submitted":
			setupMailCommands(order);
			break;	
		case "prepared":
			setupSmsCommands(order);
		break;
		default:
			setupFbCommands(order);
		}
	}
	
	public void setupMailCommands(Order order){
		log.info("CLIENT  setupMAIL COMMANDS AANGEREOEPEN");
		invoker.setCommand(mailCMD);
		invoker.emailNotification(order);
	}
	
	public void setupSmsCommands(Order order){
		log.info("CLIENT  setupSMS COMMANDS AANGEREOEPEN");
		invoker.setCommand(smsCMD);
		invoker.smsNotification(order);
	}
	
	public void setupFbCommands(Order order){
		log.info("CLIENT  setupFB COMMANDS AANGEREOEPEN");
		invoker.setCommand(fbCMD);
		invoker.fbNotification(order);
	}
	
	
}
