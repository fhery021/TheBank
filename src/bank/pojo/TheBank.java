package bank.pojo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "TheBank")
@Table( name = "TheBank",
		uniqueConstraints = {
			@UniqueConstraint(columnNames = "ID"),
			@UniqueConstraint(columnNames = "bank_name")
		})
public class TheBank implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8112938870507428579L;

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Integer bankId = 0;
	
	//@Column(name = "bank_name", unique = true, nullable = false, length = 20)
	@Column(name = "bank_name", unique = true, length = 20)
	private String bankName;
	
	
	@OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="BANK_ID")
	private Set<Client> clients;
	
	
	public Integer getBankId() {
		return this.bankId;
	}
	
	public void setBankId(Integer bankId){
		this.bankId = bankId;
	}
	
	public String getBankName(){
		return this.bankName;
	}
	
	
	public void setBankName(String bankName){
		this.bankName = bankName;
	}

	public Set<Client> getClients() {
		return clients;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}
	

	public void addClient(Client client) throws Exception {
		if (!clients.contains(client)){
			this.clients.add(client);
		}else{
			throw new Exception("Client already exists: "+client.getName());
		}
	}

	public void removeClient(Client client) throws Exception{
		if(clients.contains(client)){
			this.clients.remove(client);
		}else{
			throw new Exception("Client is already deleted: " + client.getName());
		}
	}
}
