package edu.avans.hartigehap.domain;

public interface Mail {
	public void setUpMail(String emailadres, String title, String body);
	public void sendEmail();

}
