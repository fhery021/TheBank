package bank.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "Client")
@Table(name="Client", uniqueConstraints = {
		@UniqueConstraint(columnNames = "CNP"),
		@UniqueConstraint(columnNames = "ID")
})
public class Client implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4419143579375510798L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "ID", unique = true, nullable = false)
	@Column(name = "ID", unique = true)
	private Integer clientId;
	
	//@Column(name = "CNP", unique = true, nullable = false)
	@Column(name = "CNP", unique = true, length = 13)
	private String CNP;
	
	//@Column(name="NAME", nullable = false)
	@Column(name="NAME")
	private String name;
	
	@Column(name="BALANCE_EURO")
	private int balanceEuro;

	@Column(name="BALANCE_RON")
	private int balanceRON;
	
	@Column(name="BALANCE_EURO_DEPOSIT")
	private int balanceEuroDeposit;
	
	@Column(name="BALANCE_RON_DEPOSIT")
	private int balanceRONDeposit;
	
	@ManyToOne
	private TheBank bank;
	
	public Integer getClientID(){
		return this.clientId;
	}

	public void setId(Integer clientId){
		this.clientId = clientId;
	}
	
	//@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@ManyToOne
	private TheBank Thebank;

	public TheBank getTheBank(){
		return this.Thebank;
	}
	
	public void setTheBank(TheBank bank){
		this.Thebank = bank;
	}
	
	public Client(){
		this.balanceEuro = 0;
		this.balanceEuroDeposit = 0;
		this.balanceRON = 0;
		this.balanceRONDeposit = 0;
	}
	
	public String getCNP() {
		return this.CNP;
	}


	public void setCNP(String cNP) {
		CNP = cNP;
	}

	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public int getBalanceEuro() {
		return this.balanceEuro;
	}

	public void setBalanceEuro(int balanceEuro) {
		this.balanceEuro = balanceEuro;
	}
	

	
	public int getBalanceRON() {
		return this.balanceRON;
	}

	public void setBalanceRON(int balanceRON) {
		this.balanceRON = balanceRON;
	}

	
	public int getBalanceEuroDeposit() {
		return this.balanceEuroDeposit;
	}

	public void setBalanceEuroDeposit(int balanceEuroDeposit) {
		this.balanceEuroDeposit = balanceEuroDeposit;
	}


	public int getBalanceRONDeposit() {
		return this.balanceRONDeposit;
	}

	public void setBalanceRONDeposit(int balanceRONDeposit) {
		this.balanceRONDeposit = balanceRONDeposit;
	}
	
	public String toString(){
		return "Name: "+this.name + " CNP: " + this.CNP;
	}
}
