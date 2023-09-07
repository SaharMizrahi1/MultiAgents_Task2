
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedSet;

public class Agent implements Runnable {

	private int id;
	private int agents;
	private Mailer mailer;
	//private HashMap<Integer, ConsTable> constraints;
	private HashMap<Integer, Integer> assignments = new HashMap<Integer, Integer>();

	SortedSet<Integer> neighbors; //ids of all neighbors of this agent
	// New fields by Sahar for agent's strategy, AgentView, Agent's Gain
	private String strategy;
	private HashMap<Integer, Integer> agentView = new HashMap<Integer, Integer>();
	private int agentGain;



	/*
	 * Constructor parameters:
	 * id - Agent's unique identifier.
	 * mailer - A reference to the mailer for communication.
	 * neighbors - A set of IDs representing neighboring agents.
	 * n - Total number of agents in the system.
	 * strategy - The current strategy of the agent.
	 */
	public Agent(int id, Mailer mailer, SortedSet<Integer> neighbors, int n) {
		this.id = id;
		this.mailer = mailer;
		this.neighbors=neighbors;
		this.agents = n;
		//this.strategy = strategy; // Added: Store the agent's strategy
		this.strategy=initializeRandomStrategy(); //each agent starts with a random strategy

	}


	// By Sahar Getter for Agent's Gain
	public int getAgentGain() {
		return agentGain;
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
		//By Sahar
		// Update Agent's Gain
		agentGain = success; // Added: Store the agent's gain

		// Update AgentView
		for (int neighbor : neighbors) {
			if (assignments.containsKey(neighbor)) {
				agentView.put(neighbor, assignments.get(neighbor));
			}
		}
		//
		
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


// shaked added
	private String initializeRandomStrategy() {
    // Randomly select a strategy from the available options
    Random random = new Random();
    int randomIndex = random.nextInt(2);
	if(randomIndex==0)
		return "Cooperate";
		else
			return "Defect";

}

  
}


