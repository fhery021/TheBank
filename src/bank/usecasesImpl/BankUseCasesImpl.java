package bank.usecasesImpl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import utils.HibernateUtil;
import bank.pojo.Client;
import bank.pojo.TheBank;
import bank.usecases.BankUseCases;

public class BankUseCasesImpl implements BankUseCases{

	private EntityManager em;
	
	public BankUseCasesImpl(EntityManager entityManager){
		this.em = entityManager;
	}
	
	public TheBank createBank(String name) throws Exception{
		List <TheBank> banks= 
				em.createQuery("select b from TheBank b where b.bankName = :name")
					.setParameter("name", name)
					.getResultList();

		if (banks.isEmpty()){
			TheBank bank = new TheBank();
			bank.setBankName(name);
			em.persist(bank);
			return bank;
		}else{
			return banks.get(0);
		}
	}
	
	public Collection<TheBank> getAllBanks(){
		Query query = em.createQuery("SELECT d FROM TheBank d");
		return (Collection<TheBank>)query.getResultList();
	}
	
	public Client createClient(String name, String cnp, int bankId){
		List <Client> clients =
				em.createQuery("select c from Client c where c.CNP= :cnp")
				.setParameter("cnp", cnp)
				.getResultList();
		
		if (clients.isEmpty()){
			Client client = new Client();
			client.setName(name);
			client.setCNP(cnp);
			TheBank bank = em.find(TheBank.class, bankId);
			client.setTheBank(bank);
			em.persist(client);
			
			return client;
		}else{
			return clients.get(0);
		}
	}
	
	@Override
	public Client createAccount(int clientId, int bankId) throws Exception{
		Client client = em.find(Client.class, clientId);
		TheBank bank = em.find(TheBank.class, bankId);
		bank.addClient(client);
		return client;
	}

	@Override
	public Collection<Client> getAllClients(TheBank bank) {
		Query q = em.createQuery("SELECT c from Client c");
		return (Collection<Client>)q.getResultList();
	}

	@Override
	public int getBalanceEuro(int clientId) {
		Client client = em.find(Client.class, clientId);
		return client.getBalanceEuro();
	}

	@Override
	public int getBalanceRON(int clientId) {
		Client client = em.find(Client.class, clientId);
		return client.getBalanceRON();
	}
	
	@Override
	public int getBalanceEuroDeposit(int clientId) {
		Client client = em.find(Client.class, clientId);
		return client.getBalanceEuroDeposit();
	}

	@Override
	public int getBalanceRONDeposit(int clientId) {
		Client client = em.find(Client.class, clientId);
		return client.getBalanceRONDeposit();
	}

	@Override
	public void deleteAccount(int bankId, int clientId) throws Exception {
		Client client = em.find(Client.class, clientId);
		if ((client.getBalanceEuro() != 0)
				|| (client.getBalanceEuroDeposit() != 0)
				|| (client.getBalanceRON() != 0)
				|| (client.getBalanceRONDeposit() != 0)){
				throw new Exception("The client cannot delete its account until has money on the account");
			}else{
				TheBank bank = em.find(TheBank.class, bankId);
				bank.removeClient(client);
				em.remove(client);
			}
	}

	@Override
	public void storeEuro(int clientId, int sum) throws Exception{
		int newSum =0;
		Client client = em.find(Client.class, clientId);
		newSum = client.getBalanceEuroDeposit() + sum;
		if (newSum < 1000){
			throw new Exception("Client: "+client.getName()+" cannot have deposit less than 1000 EUR on an account");
		}else{
			client.setBalanceEuroDeposit(newSum);
		}
	}


	@Override
	public void storeRON(int clientId, int sum) throws Exception{
		int newSum = 0;
		Client client = em.find(Client.class, clientId);
		newSum = client.getBalanceRONDeposit() + sum;
		if (newSum < 1000){
			throw new Exception("Client: "+client.getName()+" cannot have deposit less than 1000 RON on an account");
		}else{
			client.setBalanceRONDeposit(newSum);
		}
	}

	@Override
	public void depositEuro(int clientId, int sum){
		int newSum = 0;
		Client client = em.find(Client.class, clientId);
		newSum = client.getBalanceEuro() + sum;
		client.setBalanceEuro(newSum);
	}

	@Override
	public void depositRON(int clientId, int sum) {
		int newSum = 0;
		Client client = em.find(Client.class, clientId);
		newSum = client.getBalanceRON() + sum;
		client.setBalanceRON(newSum);
	}
	
	@Override
	public void withdrawalRON(int clientId, int sum) throws Exception{
		int newSum = 0;
		Client client = em.find(Client.class, clientId);
		if (sum > client.getBalanceRON()){
			throw new Exception("Client: "+client.getName()+" don't have enough on the account to perform the withdrawal");
		}else{
			newSum = client.getBalanceRON() - sum;
			client.setBalanceRON(newSum);
		}
	}
	
	@Override
	public void withdrawalEuro(int clientId, int sum) throws Exception {
		int newSum = 0;
		Client client = em.find(Client.class, clientId);
		newSum =  client.getBalanceEuro() - sum;
		if (newSum < 0){
			throw new Exception("Client "+client.getName()+" cannot retrieve more money than on the account");
		}else{
			client.setBalanceEuro(newSum);
		}
	}

	@Override
	public void withdrawalRONFromDeposit(int clientId, int sum)
			throws Exception {
		int newSum = 0;
		Client client = em.find(Client.class, clientId);
		newSum = client.getBalanceRONDeposit() - sum;
		if (newSum < 0){
			throw new Exception("Client "+client.getName()+" cannot retrieve more money than on the account");
		}else{
			client.setBalanceRONDeposit(newSum);
		}
	}

	@Override
	public void withdrawalEuroFromDeposit(int clientId, int sum)
			throws Exception {
		int newSum = 0;
		Client client = em.find(Client.class, clientId);
		newSum = client.getBalanceRONDeposit() - sum;
		if (newSum < 0){
			throw new Exception("Client "+client.getName()+" cannot retrieve more money than on the account");
		}else{
			client.setBalanceEuroDeposit(newSum);
		}
	}


	@Override
	public void attachFiscMonitor(TheBank bank, Client client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyFisc() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detachFiscMonitor(TheBank bank, Client client) {
		// TODO Auto-generated method stub
		
	}
}
