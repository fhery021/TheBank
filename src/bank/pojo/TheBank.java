package bank.pojo;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "TheBank")
public class TheBank {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	
	@OneToMany(mappedBy = "bank")
	private Collection<Client> clients;
	
	@OneToMany(mappedBy = "bank")
	private Collection<Account> accounts;
	
	public void setClients(final Collection<Client> clients){
		this.clients = clients;
	}
	
	public Collection<Client> getClients(){
		return this.clients;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Account> getAccounts(Long idClient) {
		
		return null;
	}
}
