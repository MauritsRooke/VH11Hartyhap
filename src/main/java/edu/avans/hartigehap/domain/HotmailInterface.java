package edu.avans.hartigehap.domain;
/**
 * Interface of the fictional extern mail library
 * @author Maurits
 *
 */
public interface HotmailInterface {
		public void createProperties();
		public void setupSession();
		public void setMailstuff(String emailadres, String title, String body);
		public void sendHotmail();
}
