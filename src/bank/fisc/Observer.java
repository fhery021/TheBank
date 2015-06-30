package bank.fisc;

import bank.usecasesImpl.BankUseCasesImpl;


public abstract class Observer {
	
	protected BankUseCasesImpl subject;
	public abstract void update();

}
