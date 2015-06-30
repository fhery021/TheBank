package bank.fisc;

import java.util.ArrayList;
import java.util.List;

public class Subject {

	private List<Observer> observers = new ArrayList<Observer>();
	private FiscEntry state;
	
	public FiscEntry getState(){
		return state;
	}
	
	public void setState(FiscEntry state){
		this.state = state;
		notifyAllObservers();
	}
	
	public void attach(Observer observer){
		observers.add(observer);
	}
	
	public void notifyAllObservers(){
		for (Observer observer :observers){
			observer.update();
		}
	}
	
	public void detach(Observer observer){
		observers.remove(observer);
	}
	
	public boolean isAttachedToObserver(Observer observer){
		if (observers.contains(observer)){
			return true;
		}else{
			return false;
		}
	}
	
}
