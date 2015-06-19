package bank.usecases;

import java.util.List;

import bank.pojo.Account;
import bank.pojo.Client;
import bank.pojo.TheBank;

public interface BankUseCases {
	
	/**
	 * 
	 * @param bank
	 * The bank you choose
	 * @param client
	 * The client (CNP, name)
	 * @param account
	 * The account you want to create
	 * It creates a new account in the bank.
	 */
	public Account createAccount(TheBank bank, Client client);
	
	public List getAllAccounts(TheBank bank);
	
	/**
	 * 
	 * @param bank
	 * @param client
	 */
	public Account getBalance(TheBank bank, Client client);
	
	public void deleteAccount(TheBank bank, Client client, Account account);
	
	// depozitare pe termen lung
	public void store(TheBank bank, Client client, Account account, long sum);
	
	//retragere
	public void withdrawal(TheBank bank, Client client, Account account, long sum);
	
	//deounere 
	public void deposit(TheBank bank, Client client, Account account, long sum);
	
	/**
	 * 
	 * @param bank 
	 * The bank monitored
	 * @param client
	 * The Client who is monitored
	 * @param account
	 * The account monitored by Fist
	 * Fisc attaches to the bank and receives notifications at every transaction.
	 */
	public void attachFiscMonitor(TheBank bank, Client client, Account account);
	
	public void notifyFisc();
	
	/**
	 * 
	 * @param bank
	 * @param client
	 * @param account
	 * 
	 * Fisc detaches from the Bank's notifications
	 */
	public void detachFiscMonitor(TheBank bank, Client client, Account account);
	
	
}
