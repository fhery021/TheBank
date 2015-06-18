package bank.pojo;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Client")
public class Client {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String CNP;
	
	@ManyToOne(optional = false)
	private TheBank bank;
	
	public Long getId() {
		return id;
	}

/*
	public void setId(Long id) {
		this.id = id;
	}
*/

	public String getCNP() {
		return CNP;
	}


	public void setCNP(String cNP) {
		CNP = cNP;
	}


	public TheBank getBank() {
		return bank;
	}


	public void setBank(TheBank bank) {
		this.bank = bank;
	}

	public Collection<Account> getAccounts() {
		return bank.getAccounts(this.id);
	}

	
}
