package bank.usecases;

import java.util.Collection;

import bank.pojo.Client;
import bank.pojo.TheBank;

public interface BankUseCases {
	
	/**
	 * 
	 * @param bank
	 * The bank you choose
	 * @param client
	 * The client (CNP, name)
	 * It creates a new account in the bank.
	 * @throws Exception 
	 */
	public Client createAccount(int clientId, int bankId) throws Exception;

	public void deleteAccount(int bankId, int clientId) throws Exception;
	
	public Collection<Client> getAllClients(TheBank bank);
	
	public int getBalanceEuro(int clientId);

	public int getBalanceRON(int clientId);
	
	public int getBalanceEuroDeposit(int clientId);
	
	public int getBalanceRONDeposit(int clientId);
	
	// depozitare pe termen lung
	public void storeEuro(int clientId, int sum) throws Exception;
	
	public void storeRON(int clientId, int sum)throws Exception;
	
	//retragere
	public void withdrawalRON(int clientId, int sum) throws Exception;
	
	public void withdrawalEuro(int clientId, int sum) throws Exception;
	
	public void withdrawalRONFromDeposit(int clientId, int sum) throws Exception;
	
	public void withdrawalEuroFromDeposit(int clientId, int sum) throws Exception;
	
	//depunere 
	public void depositEuro(int clientId, int sum);
	
	public void depositRON(int clientId, int sum);
	
	/**
	 * 
	 * @param bank 
	 * The bank monitored
	 * @param client
	 * The Client who is monitored
	 * Fisc attaches to the bank and receives notifications at every transaction.
	 */
	public void attachFiscMonitor(TheBank bank, Client client);
	
	public void notifyFisc();
	
	/**
	 * 
	 * @param bank
	 * @param client
	 * 
	 * Fisc detaches from the Bank's notifications
	 */
	public void detachFiscMonitor(TheBank bank, Client client);

	

	

	
}
