package bank.tests;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.DbCleaner;
import utils.JPAUtils;
import bank.pojo.Client;
import bank.pojo.TheBank;
import bank.usecasesImpl.BankUseCasesImpl;

public class BankUseCasesImplTests {

	EntityManager em;
	TheBank ing = null;
	TheBank brd = null;
	BankUseCasesImpl bankUseCase;
	Client edison = new Client();
	Client ionica = new Client();
	Client gutenberg = new Client();
	
	boolean gutenBergTransactionPerformed = false;
	
	@Before 
	public void setUpTheBank() {
		em = JPAUtils.getEntityManager();
		//DbCleaner.deleteTableContentFromDB(em);
		
		/*if (!em.getTransaction().isActive()){
			em.getTransaction().begin();
		}
		*/
		//creating bank use case
		bankUseCase = new BankUseCasesImpl(em);
		
		//creating the bank
		try{
			ing = bankUseCase.createBank("ING");
		}catch(Exception e){
			System.out.println("Unable to create the bank");
			e.printStackTrace();
		}
		
		try{
			brd = bankUseCase.createBank("BRD");
		}catch(Exception e){
			System.out.println("Unable to create the bank");
			e.printStackTrace();
		}
		
		
		//creating clients
		edison = bankUseCase.createClient("Thomas Edison", "1989567890123", ing.getBankId());
		ionica = bankUseCase.createClient("Ionica", "1934567890123", ing.getBankId());
		gutenberg = bankUseCase.createClient("Johannes Gutenberg", "1146867890123", brd.getBankId());
		
		//creating accounts
		try {
			edison = bankUseCase.createAccount(edison.getClientID(), ing.getBankId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try{
			ionica = bankUseCase.createAccount(ionica.getClientID(), ing.getBankId());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			gutenberg = bankUseCase.createAccount(gutenberg.getClientID(), brd.getBankId());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void performGutenbergTransactions(){
		// Gutenberg is depositing money
		try {
			bankUseCase.depositEuro(gutenberg.getClientID(), 110);
			bankUseCase.depositRON(gutenberg.getClientID(), 140);
			bankUseCase.storeEuro(gutenberg.getClientID(), 1000);
			bankUseCase.storeRON(gutenberg.getClientID(), 6100);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests:
	 * public void createAccount(TheBank bank, Client client, Account account);
	 */
	@Test
	public void createNewAccountTest() {
		Client tesla = bankUseCase.createClient("Nikola Tesla", "1955567890123", ing.getBankId());
		try{
			bankUseCase.createAccount(tesla.getClientID(),ing.getBankId());
		}catch(Exception e){
			e.printStackTrace();
		}

		Collection<Client> allAccounts = bankUseCase.getAllClients(ing);
		
		
		if ((!allAccounts.contains(edison)) || (!allAccounts.contains(tesla))){
			fail("The accounts are note created in the database.");
		}
		
	}
	
	@Test
	public void getBalanceEuroTest(){
		Client leonardo = bankUseCase.createClient("Leonardo Da Vinci", "1145267890123", ing.getBankId());
		try{
			bankUseCase.createAccount(leonardo.getClientID(), ing.getBankId());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		leonardo.setBalanceEuro(1000);
		int balance = bankUseCase.getBalanceEuro(leonardo.getClientID());
		if (balance != 1000){
			fail("Failed to get the balance or invalid sum on the account");
		}
		
	}
	
	@Test
	public void getBalanceRonTest(){
		Client archimedes = bankUseCase.createClient("Archimedes", "1287056789013", ing.getBankId());
		try {
			bankUseCase.createAccount(archimedes.getClientID(), ing.getBankId());
		} catch (Exception e) {
			e.printStackTrace();
			//fail(e.getMessage());
		}
		
		archimedes.setBalanceRON(500);
		int balance = bankUseCase.getBalanceRON(archimedes.getClientID());
		if (balance != 500){
			fail("Failed to get the balance or invalid sum on the account");
		}
		
	}
	
	@Test
	public void storeEuroTest(){
		int sum = 2500;
		Client newton = bankUseCase.createClient("Isaac Newton", "1164367890123", brd.getBankId());
		try{
			bankUseCase.createAccount(newton.getClientID(), brd.getBankId());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			bankUseCase.storeEuro(newton.getClientID(), sum);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		if (bankUseCase.getBalanceEuroDeposit(newton.getClientID()) != sum){
			fail("Cannot store the sum: "+sum+" to client: "+newton.getName());
		}
	}
	
	@Test
	public void storeRONTest(){
		int sum = 2300;
		Client ford = bankUseCase.createClient("Henry Ford", "1186267890123", brd.getBankId());
		try{
			bankUseCase.createAccount(ford.getClientID(), brd.getBankId());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			bankUseCase.storeRON(ford.getClientID(), sum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (bankUseCase.getBalanceRONDeposit(ford.getClientID()) != sum){
			fail("Cannot store the sum: "+sum+" to client: "+ford.getName());
		}
	}

	@Test
	public void withdrawalRONTest(){
		if (!gutenBergTransactionPerformed){
			performGutenbergTransactions();
			gutenBergTransactionPerformed = true;
		}
		try {
			bankUseCase.withdrawalRON(gutenberg.getClientID(), 100);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		if (bankUseCase.getBalanceRON(gutenberg.getClientID()) != 40){
			fail("Withdrawal unsuccessful or calculation error");
		}
	}
	
	@Test
	public void withdrawalEuroTest(){
		if (!gutenBergTransactionPerformed){
			performGutenbergTransactions();
			gutenBergTransactionPerformed = true;
		}
		try {
			bankUseCase.withdrawalEuro(gutenberg.getClientID(), 100);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		if (bankUseCase.getBalanceEuro(gutenberg.getClientID()) != 10){
			fail("Withdrawal unsuccessful or calculation error");
		}
	}
	
	@Test
	public void withdrawalRONFromDepositTest(){
		if (!gutenBergTransactionPerformed){
			performGutenbergTransactions();
			gutenBergTransactionPerformed = true;
		}
		try{
			bankUseCase.withdrawalRONFromDeposit(gutenberg.getClientID(), 100);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		if (bankUseCase.getBalanceRONDeposit(gutenberg.getClientID()) != 6000){
			fail("Withdrawal unsuccessful or calculation error");
		}
		
	}
	
	public void withdrawalEuroFromDepositTest(){
		if (!gutenBergTransactionPerformed){
			performGutenbergTransactions();
			gutenBergTransactionPerformed = true;
		}
		try{
			bankUseCase.withdrawalEuroFromDeposit(gutenberg.getClientID(), 500);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		if (bankUseCase.getBalanceEuroDeposit(gutenberg.getClientID()) != 500){
			fail("Withdrawal unsuccessful or calculation error");
		}
	}
	
	@Test
	public void  depositEuroTest(){
		//charging of the account
		edison.setBalanceEuro(0);
		try {
			bankUseCase.depositEuro(edison.getClientID(), 50);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		if (bankUseCase.getBalanceEuro(edison.getClientID()) != 50){
			fail("Failed to charge the accout for: "+edison.getName());
		};
	}
	
	@Test
	public void depositRONTest(){
		if (!gutenBergTransactionPerformed){
			performGutenbergTransactions();
			gutenBergTransactionPerformed = true;
		}
		if (bankUseCase.getBalanceRONDeposit(gutenberg.getClientID()) != 100){
			fail("Failed to get the sum of the RON deposit for client: "+gutenberg.getName());
		}
	}
	
	@Test
	public void attachFiscMonitorTest(){
		fail("not implemented");
	}
	
	@Test
	public void notifyFiscTest(){
		fail("not implemented");
	}
	
	public void detachFiscMonitorTest(){
		fail("not implemented");
	}
	
	@Test
	public void deleteAccountTest(){
		try {
			bankUseCase.deleteAccount(ing.getBankId(), ionica.getClientID());
		} catch (Exception e) {
			fail("Excetion met. Could not delete completely the account: "+ionica.getName());
			e.printStackTrace();
		}
		
		if (bankUseCase.getAllClients(ing).contains(ionica)){
			fail("Could not delete completely the account: "+ionica.getName());
		}
	}
	
	
	/*
	 * End of tests, helper methods. 
	 * OP: Should be in Other class?
	 */
	public void printAllClients(){
		Collection <Client> allClients  = bankUseCase.getAllClients(ing);
		System.out.println(allClients);
	}
	
	@After
	public void tearDown(){
		JPAUtils.close();
	}
	

	/*TO-DO
	 * 
	 * 1. Correct withdrawalRONFromDepositTest
	 * withdrawalEuroTest
	 * depositRonTest
	 * 
	 * 2. Clean-up the db before executing the use-cases
	 * 
	 * 3. Implement the notification service for Fisc
	 * 
	 */
	
}
