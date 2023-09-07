
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedSet;

public class Agent implements Runnable {

	private int id;
	private int agents;
	private Mailer mailer;
	SortedSet<Integer> neighbors; //ids of all neighbors of this agent
	// New fields by Sahar for agent's strategy, AgentView, Agent's Gain
	private String strategy;
	private HashMap<Integer, String> agentView = new HashMap<Integer, String>();
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
		

		//By Sahar
		// Update Agent's Gain
		//agentGain = success; // Added: Store the agent's gain

		// Update AgentView
		for (int neighbor : neighbors) {
			if (assignments.containsKey(neighbor)) {
				agentView.put(neighbor, assignments.get(neighbor));
			}
		}
		//Yair ? not sure if delete
		
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


