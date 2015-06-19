package bank.usecasesImpl;

import java.util.List;

import bank.pojo.Account;
import bank.pojo.Client;
import bank.pojo.TheBank;
import bank.usecases.BankUseCases;
import javax.persistence.EntityManager;

public class BankUseCasesImpl implements BankUseCases{

	private EntityManager em;
	
	public BankUseCasesImpl(EntityManager entityManager){
		this.em = entityManager;
	}
	
	@Override
	public Account createAccount(TheBank bank, Client client) {
		client.setBank(bank);
		Account newAccount = new Account(bank);
		newAccount.setClient(client);
		
		em.persist(client);
		em.persist(newAccount);
		return newAccount;
	}
	
	@Override
	public List<Account> getAllAccounts(TheBank bank) {
		/*return em.createQuery(
				"SELECT DISTINCT c from Clent c WHERE c.bank_id LIKE :bankid")
				.setParameter("bankid", bank.get)
				.getResultList();
				*/
		return em.createQuery("SELECT DISTINCT p FROM Account p")
				.getResultList();
	}

	@Override
	public Account getBalance(TheBank bank, Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAccount(TheBank bank, Client client, Account account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store(TheBank bank, Client client, Account account, long sum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void withdrawal(TheBank bank, Client client, Account account,
			long sum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deposit(TheBank bank, Client client, Account account, long sum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attachFiscMonitor(TheBank bank, Client client, Account account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyFisc() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detachFiscMonitor(TheBank bank, Client client, Account account) {
		// TODO Auto-generated method stub
		
	}

}
