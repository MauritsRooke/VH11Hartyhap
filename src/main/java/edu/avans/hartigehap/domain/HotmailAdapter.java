package edu.avans.hartigehap.domain;
/**
 * Fictional adapter to make a "extern" hotmail interface work on the current interface
 * @author Maurits
 *
 */
public class HotmailAdapter implements Mail {
	Hotmail hotmail;
	public HotmailAdapter(Hotmail hotmail){
		this.hotmail = hotmail;
	}

	@Override
	public void setUpMail(String emailadres, String title, String body) {
		// TODO Auto-generated method stub
		hotmail.createProperties();
		hotmail.setupSession();
		hotmail.setMailstuff(emailadres, title, body);			
	}

	@Override
	public void sendEmail() {
		hotmail.sendHotmail();

	}

}
