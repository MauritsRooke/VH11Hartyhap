package edu.avans.hartigehap.domain;

public interface HotmailInterface {
		public void createProperties();
		public void setupSession();
		public void setMailstuff(String emailadres, String title, String body);
		public void sendHotmail();
}
