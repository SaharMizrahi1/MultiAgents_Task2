
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedSet;

public class Agent implements Runnable {

	private int id;
	private int assignment, agents;
	private Mailer mailer;
	//private HashMap<Integer, ConsTable> constraints;
	private HashMap<Integer, Integer> assignments = new HashMap<Integer, Integer>();

	SortedSet<Integer> neighbors; //ids of all neighbors of this agent
	
	/*
	 * constructor parameters -
	 * agent's id
	 * a reference to mailer
	 * private information from masp object
	 */
	public Agent(int id, Mailer mailer, SortedSet<Integer> neighbors, int n) {
		this.id = id;
		this.mailer = mailer;
		this.neighbors=neighbors;
		//this.domainSize = d;
		this.agents = n;
		
		Random r = new Random();
		assignment = r.nextInt(domainSize);
	}

	@Override
	public void run() {
		
		
		Message m = new AssignmentMessage(id, assignment);
		for (Entry<Integer, ConsTable> e: constraints.entrySet()) {
			mailer.send(e.getKey(), m);
		}
		
		while (assignments.size() < constraints.size()) {
			AssignmentMessage message = (AssignmentMessage) mailer.readOne(id);
			if (message == null) {
				continue;
			}
			
			assignments.put(message.getSender(), message.getAssignment());
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int success = 0;
		for (Entry<Integer, ConsTable> e: constraints.entrySet()) {
			int a1 = -1, a2 = -1;
			if (e.getKey() < id) {
				a1 = assignments.get(e.getKey());
				a2 = assignment;
			}
			else {
				a2 = assignments.get(e.getKey());
				a1 = assignment;
			}
			if (e.getValue().check(a1, a2)) {
				success++;
			}
		}
		
		System.out.println("id: " + id + ", assignment: " + assignment + ", successful constraint checks: " + success);
		
		if (id != agents - 1) {
			ChecksMessage message = new ChecksMessage(success);
			mailer.send(agents - 1, message);
		}
		else {
			int count = 0;
			while (count < agents - 1) {
				ChecksMessage message = (ChecksMessage) mailer.readOne(id);
				if (message == null) {
					continue;
				}
				count++;
				success += message.getChecks();
			}
			
			System.out.println("total number of constraint checks: " + success);
		}
	}

}
