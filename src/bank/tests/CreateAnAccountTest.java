package bank.tests;

import javax.persistence.EntityManager;

import org.junit.Test;

import utils.JPAUtils;
import bank.pojo.Account;
import bank.pojo.Client;
import bank.pojo.TheBank;

public class PersistencyTests {

	EntityManager em;
	
	@Test
	public void initBank() {
		em = JPAUtils.getEntityManager();
		
		dropTablesFromDB();
		
		TheBank bank = new TheBank();
		bank.setName("ING");
		em.persist(bank);
		
		Client client = new Client();
		client.setCNP("19800254631789");
		client.setBank(bank);
		Account client1Account = new Account(bank);
		
		client1Account.setClient(client);
		em.persist(client);
		em.persist(client1Account);
		
		JPAUtils.close();
	}

	
	private void dropTablesFromDB(){
		em.createQuery("delete from Account").executeUpdate();
		em.createQuery("delete from Client").executeUpdate();
		em.createQuery("delete from TheBank").executeUpdate();
	}
}
