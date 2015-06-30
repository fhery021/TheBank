package bank.usecases;

import java.util.ArrayList;

import bank.fisc.FiscEntry;

public interface FiscUseCases {

	public void startMonitoring();
	
	public void stopMonitoring();
	
	public ArrayList<FiscEntry> getAllEntries();
	
	public void showAllInfo();
	
	
}
