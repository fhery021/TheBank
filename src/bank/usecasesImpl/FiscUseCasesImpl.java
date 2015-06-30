package bank.usecasesImpl;

import java.util.ArrayList;

import bank.fisc.Fisc;
import bank.fisc.FiscEntry;
import bank.pojo.Client;
import bank.usecases.FiscUseCases;

public class FiscUseCasesImpl implements FiscUseCases{

	/**
	 * bankService is the subject
	 */
	private BankUseCasesImpl bankService;
	private Fisc fisc;
	
	public FiscUseCasesImpl(BankUseCasesImpl bankingService){
		this.setBankService(bankService);
		this.fisc = new Fisc(bankingService);
	}
	
	public BankUseCasesImpl getBankService() {
		return bankService;
	}

	public void setBankService(BankUseCasesImpl bankService) {
		this.bankService = bankService;
	}

	public Fisc getFisc() {
		return fisc;
	}

	public void setFisc(Fisc fisc) {
		this.fisc = fisc;
	}
	
	@Override
	public void startMonitoring() {
		bankService.attach(this.fisc);
	}

	@Override
	public void stopMonitoring() {
		bankService.detach(this.fisc);
	}

	@Override
	public ArrayList<FiscEntry> getAllEntries() {
		return fisc.getEntries();
	}

	@Override
	public void showAllInfo() {
		ArrayList <FiscEntry> allEntries = fisc.getEntries();
		for (FiscEntry entry:allEntries){
			System.out.println(entry.toString());
		}
	}
	
	public boolean wasTheClientMonitored(Client client){
		ArrayList<FiscEntry> allEntries = fisc.getEntries();
		for (FiscEntry entry:allEntries){
			if (entry.getClientId() == client.getClientID()){
				return true;
			}
		}
		return false;
	}
	
	public int getNumberOfTransactions(Client client){
		int transactions = 0;
		ArrayList<FiscEntry> allEntries = fisc.getEntries();
		for (FiscEntry entry:allEntries){
			if (entry.getClientId() == client.getClientID()){
				transactions++;
			}
		}
		return transactions;
	}

}
