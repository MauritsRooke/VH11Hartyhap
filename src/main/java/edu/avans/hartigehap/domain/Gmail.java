package edu.avans.hartigehap.domain;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Gmail implements Mail {
	final String username = "hartigehaptest";
	final String password = "HartigeHap";
	private Properties props;
	private Session session;
	private Message message;
	
	public Gmail(){
		 props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		 session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
	}

	@Override
	public void setUpMail(String emailadres, String title, String body) {
		
		try {
		message = new MimeMessage(session);
		message.setFrom(new InternetAddress("hartigehaptest@gmail.com"));
		message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(emailadres));
		message.setSubject(title);
		message.setText(body);
		
		}catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void sendEmail() {
		try {
			Transport.send(message);
		}		
		catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
