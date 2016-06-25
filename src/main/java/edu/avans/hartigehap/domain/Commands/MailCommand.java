package edu.avans.hartigehap.domain.Commands;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailCommand implements Command {
	Notification notify;
	
	public MailCommand(Notification notify){
		this.notify = notify;
	}
	
	public void execute(){
		log.info("COMMAND EXECUTE AANGEROEPEN");
		notify.setInfo("mauritsrooke@gmail.com");
		notify.setHeader("Cooleheader");
		notify.setBody("MAIL BODY VERZONDEN");
		notify.sendMail();
	}
}