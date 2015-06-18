package bank.usecases;

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
	public void createAccount(TheBank bank, Client client, Account account);
	
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
	
	
}
