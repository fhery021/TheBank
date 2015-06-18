package bank.pojo;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class Fisc {
	@OneToOne(optional = false)
	@JoinColumn(name = "TheBankId")
	private TheBank bank;

}
