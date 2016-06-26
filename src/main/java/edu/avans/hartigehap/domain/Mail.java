package edu.avans.hartigehap.domain;
/**
 * Mail interface 
 * @author Maurits
 *
 */
public interface Mail {
	public void setUpMail(String emailadres, String title, String body);
	public void sendEmail();

}
