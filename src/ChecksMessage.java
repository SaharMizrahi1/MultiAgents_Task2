
public class ChecksMessage implements Message {

	private int checks;
	
	public ChecksMessage(int checks) {
		this.checks = checks;
	}

	public int getChecks() {
		return checks;
	}
}
