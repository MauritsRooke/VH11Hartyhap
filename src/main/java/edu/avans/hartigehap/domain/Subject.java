package edu.avans.hartigehap.domain;

public interface Subject {

	public void registerObserver(OrderStateObserver o);
	public void removeObserver(OrderStateObserver o);
	public void notifyObservers();
}
