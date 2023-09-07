
public class AssignmentMessage implements Message {

	private int sender;
	private String strategy;
	
	// a message should include information.
	// you are required to add corresponding fields and constructor parameters
	// in order to pass on that information
	public AssignmentMessage(int sender, String assignment) {
		this.sender = sender;
		this.strategy = assignment;
	}

	public int getSender() {
		return sender;
	}

	public String getStrategy() {
		return strategy;
	}
}
