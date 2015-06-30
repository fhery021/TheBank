package bank.fisc;

import java.util.ArrayList;

import bank.usecasesImpl.BankUseCasesImpl;

public class Fisc extends Observer{

	ArrayList<FiscEntry> entries;
	
	public Fisc(BankUseCasesImpl bankUseCases){
		this.subject = bankUseCases;
		//this.subject.attach(this);
		entries = new ArrayList<FiscEntry>();
	}
	
	@Override
	public void update() {
		System.out.println(">> new transaction: " + this.subject.getState());
		entries.add(this.subject.getState());
	}

	public ArrayList<FiscEntry> getEntries(){
		return entries;
	}
}
