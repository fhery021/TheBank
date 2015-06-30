package bank.fisc;


public class FiscEntry {
	
	private int clientId;
	
	private String actionType;
	
	private int sum;
	
	public FiscEntry(int clientId, String actionType, int sum){
		this.clientId = clientId;
		this.actionType = actionType;
		this.sum = sum;
	}
	
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public String toString(){
		return "Client id: " + clientId + " Action type: " + actionType + " sum: "+sum;
		
	}
}
