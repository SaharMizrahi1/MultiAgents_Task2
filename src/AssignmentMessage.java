
public class AssignmentMessage implements Message {

	private int sender;
	private String strategy;
	

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
