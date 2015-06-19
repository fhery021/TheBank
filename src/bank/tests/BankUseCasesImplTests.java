package bank.tests;

import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.DbCleaner;
import utils.JPAUtils;
import bank.pojo.Account;
import bank.pojo.Client;
import bank.pojo.TheBank;
import bank.usecasesImpl.BankUseCasesImpl;

public class BankUseCasesImplTests {

	EntityManager em;
	TheBank ing = new TheBank();
	BankUseCasesImpl bankUseCase;
	
	@Before 
	public void setUpTheBank() {
		em = JPAUtils.getEntityManager();
		DbCleaner.deleteTableContentFromDB(em);
		bankUseCase = new BankUseCasesImpl(em);
		
		ing.setName("ING");
		em.persist(ing);
	}

	/**
	 * Tests:
	 * public void createAccount(TheBank bank, Client client, Account account);
	 */
	@Test
	public void createNewAccountTest() {
		Client edison = new Client();
		edison.setName("Thomas Edison");
		edison.setCNP("1989567890123");
		
		bankUseCase.createAccount(ing, edison);
		
		Client tesla = new Client();
		tesla.setName("Nikola Tesla");
		tesla.setCNP("1955567890123");
		
		bankUseCase.createAccount(ing, tesla);
		
		printAllAccountsAndInfo(ing);
		
		List<Account> allAccounts = bankUseCase.getAllAccounts(ing);
		/*if ((!allAccounts.contains(edison.)) || (!allAccounts.contains(tesla))){
			fail("The accounts are note created in the database.");
		}
		*/
		
	}
	
	private void printAllAccountsAndInfo(TheBank bank){
		List<Account> allAccounts= bankUseCase.getAllAccounts(bank);
		for(int i=0; i<allAccounts.size(); i++){
			System.out.println(allAccounts.get(i).toString());
		}
	}
	
	@After
	public void tearDown(){
		JPAUtils.close();
	}
	

}
