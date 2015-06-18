package bank.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name = "Account")
public class Account {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "clientId")
	private Client client;

	@ManyToOne(optional = false)
	private TheBank bank;
	
	private long balanceEuro;
	private long balanceRON;
	
	public Account (TheBank bank){
		this.bank = bank;
		this.balanceEuro = 0;
		this.balanceRON = 0;
	}
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public long getBalanceEuro() {
		return balanceEuro;
	}

	public void setBalanceEuro(long balanceEuro) {
		this.balanceEuro = balanceEuro;
	}

	public long getBalanceRON() {
		return balanceRON;
	}

	public void setBalanceRON(long balanceRON) {
		this.balanceRON = balanceRON;
	}
	public TheBank getBank() {
		return bank;
	}
	public void setBank(TheBank bank) {
		this.bank = bank;
	}
}
