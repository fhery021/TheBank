package bank.tests;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import utils.JPAUtils;
import bank.pojo.Client;
import bank.pojo.TheBank;
import bank.usecasesImpl.BankUseCasesImpl;
import bank.usecasesImpl.FiscUseCasesImpl;

public class FiscUseCasesImplTests{

	EntityManager entityManager;
	BankUseCasesImpl bankingService;
	
	Client edison = new Client();
	Client ionica = new Client();
	Client gutenberg = new Client();
	TheBank ing = null;
	TheBank brd = null;
	FiscUseCasesImpl fiscService = null;
	
	@Before
	public void setUp(){
		entityManager = JPAUtils.getEntityManager();
		bankingService = new BankUseCasesImpl(entityManager);
		
		System.out.println("setting up...");
		//creating the bank
		try {
			ing = bankingService.createBank("ING");
		} catch (Exception e) {
			System.out.println("Unable to create the bank");
			e.printStackTrace();
		}

		try {
			brd = bankingService.createBank("BRD");
		} catch (Exception e) {
			System.out.println("Unable to create the bank");
			e.printStackTrace();
		}
		
		//creating clients
		edison = bankingService.createClient("Thomas Edison", "1989567890123", ing.getBankId());
		ionica = bankingService.createClient("Ionica", "1934567890123", ing.getBankId());
		gutenberg = bankingService.createClient("Johannes Gutenberg", "1146867890123", brd.getBankId());
		
		//creating accounts
		try {
			edison = bankingService.createAccount(edison.getClientID(), ing.getBankId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try{
			ionica = bankingService.createAccount(ionica.getClientID(), ing.getBankId());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			gutenberg = bankingService.createAccount(gutenberg.getClientID(), brd.getBankId());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		fiscService = new FiscUseCasesImpl(bankingService);
	}
	
	@Test
	public void startMonitoringTest() {
		if (fiscService.getBankService() == null){
			fiscService.setBankService(bankingService);
		}
		fiscService.startMonitoring();
		bankingService.depositRON(edison.getClientID(), 10);
		if (!fiscService.wasTheClientMonitored(edison)){
			fail("Client "+edison.getName()+" is not monitored");
		}
		fiscService.stopMonitoring();
	}

	@Test
	public void stopMonitoring() {
		int nrOfMonitoredTransactions = 0;
		int expectedMonitoredTransactions = 0;
		
		if (fiscService.getBankService() == null){
			fiscService.setBankService(bankingService);
		}
		
		fiscService.startMonitoring();
		bankingService.depositEuro(ionica.getClientID(), 12345);
		try {
			bankingService.storeEuro(ionica.getClientID(), 2000);
		} catch (Exception e) {
			fail("Storage unsuccesful for client: "+ionica.getName());
			e.printStackTrace();
		}
		expectedMonitoredTransactions = fiscService.getNumberOfTransactions(ionica);
		fiscService.stopMonitoring();
		//Stopped monitoring. 
		bankingService.depositEuro(ionica.getClientID(), 12345);
		nrOfMonitoredTransactions = fiscService.getNumberOfTransactions(ionica);
		
		if (nrOfMonitoredTransactions != expectedMonitoredTransactions){
			fail("Client "+ ionica.getName() + " is still monitored");
		}
		
	}

	@Test
	public void retrieveInfo() {
		if (fiscService.getBankService() == null){
			fiscService.setBankService(bankingService);
		}
		fiscService.startMonitoring();
		bankingService.depositRON(gutenberg.getClientID(), 1990);
		try {
			bankingService.storeRON(gutenberg.getClientID(), 1500);
		} catch (Exception e) {
			fail("Storage unsuccesful for client: "+gutenberg.getName());
			e.printStackTrace();
		}
		fiscService.stopMonitoring();

		if (fiscService.getAllEntries().isEmpty()){
			fail("No entries in the Fisc service");
		}
		fiscService.stopMonitoring();
	}

	//@Test
	public void showAllInfo() {
		fiscService.showAllInfo();
	}

}
