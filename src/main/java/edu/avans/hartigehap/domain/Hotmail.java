package edu.avans.hartigehap.domain;


import lombok.extern.slf4j.Slf4j;

/**Implementatie van een hotmail verzenden
 * Geen werkende implementatie van de hotmail interface
 * Klasse is uitgewerkt om de werking van de adapter weer te geven
 * 
 * @author Maurits
 *
 */
@Slf4j
public class Hotmail implements HotmailInterface  {
	String adres;
	String header;
	String body;
	
	@Override
	public void createProperties() {
		// TODO Auto-generated method stub
		log.info("properties aangemaakt");
	}

	@Override
	public void setupSession() {
		// TODO Auto-generated method stub
		log.info("sessie instellingen aangemaakt");
	}

	@Override
	public void setMailstuff(String emailadres, String title, String body) {
		adres=emailadres;
		header= title;
		this.body = body;
	}

	@Override
	public void sendHotmail() {
		// TODO Auto-generated method stub
		log.info("HOTMAIL HEEFT EEN MAIL VERSTUURD AAN" + adres + "MET ALS HEADER"+ header+ "EN DE BODY MESSAGE"+body);
	}
	
}
