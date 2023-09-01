
public class AssignmentMessage implements Message {

	private int sender, assignment;
	
	// a message should include information.
	// you are required to add corresponding fields and constructor parameters
	// in order to pass on that information
	public AssignmentMessage(int sender, int assignment) {
		this.sender = sender;
		this.assignment = assignment;
	}

	public int getSender() {
		return sender;
	}

	public int getAssignment() {
		return assignment;
	}
}
