package bank.tests;

import javax.persistence.EntityManager;

import org.junit.Test;

import utils.JPAUtils;
import bank.pojo.Client;
import bank.pojo.TheBank;
import bank.usecasesImpl.BankUseCasesImpl;

public class CreateAnAccountTest {


	EntityManager em;
	BankUseCasesImpl bankUseCase;
	
	@Test
	public void initBankAndCreateAnAccount() {
		em = JPAUtils.getEntityManager();
		//DbCleaner.deleteTableContentFromDB(em);
		
		//em.getTransaction().begin();
		
		//creating bank use case
		bankUseCase = new BankUseCasesImpl(em);
		
		TheBank ing = null;
		//creating the bank
		try{
			ing = bankUseCase.createBank("ING");
		}catch(Exception e){
			System.out.println("Unable to create the bank");
			e.printStackTrace();
		}
			
		//creating clients
		Client edison = bankUseCase.createClient("Thomas Edison", "1989567890123", ing.getBankId());
		Client ionica = bankUseCase.createClient("Ionica", "1934567890123", ing.getBankId());
		
		
		
		//creating accounts
		try {
			System.out.println(edison.getClientID());
			edison = bankUseCase.createAccount(edison.getClientID(), ing.getBankId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try{
			ionica = bankUseCase.createAccount(ionica.getClientID(), ing.getBankId());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JPAUtils.close();
	}
	
	
	

}
