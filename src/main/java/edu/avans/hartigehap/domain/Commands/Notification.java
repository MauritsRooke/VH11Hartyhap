package edu.avans.hartigehap.domain.Commands;


import edu.avans.hartigehap.domain.Gmail;
import edu.avans.hartigehap.domain.Hotmail;
import edu.avans.hartigehap.domain.HotmailAdapter;
import edu.avans.hartigehap.domain.Mail;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Notification {
	private String messageInfo;
	private String header;
	private String body;
	private Mail mail;
	
	public void setInfo(String info){
		messageInfo = info;
	}
	public void setHeader(String header){
		this.header = header;
	}
	public void setBody(String body){
		this.body = body;
	}
	public void sendMail(){
		//Adapter added mailing functionality
		Hotmail hotmail = new Hotmail();
		Mail adapter = new HotmailAdapter(hotmail);
		adapter.setUpMail(messageInfo, header, body);
		adapter.sendEmail();
		//Standard mailing functionality
		mail = new Gmail();
		mail.setUpMail(messageInfo, header, body);
		//mail.sendEmail();
	}
	
	public void sendText(){
		log.info("SMS VERZONDEN NAAR"+ messageInfo +"MET ALS TEKST" + body);
	}
	
	public void sendFacebook(){
		log.info("FACEBOOK BERICHT VERZONDEN NAAR"+ messageInfo +"MET ALS TEKST" + body);
	}

}

