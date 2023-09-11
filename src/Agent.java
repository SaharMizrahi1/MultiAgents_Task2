
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class Agent implements Runnable {

	private int id; //agent id
	private int agents; //number of agents - n
	private Mailer mailer; //reference to mailer
	//SortedSet<Integer> neighborsid; //ids of all neighbors of this agent
	HashSet<Agent> neighbors= new HashSet<Agent>(); //ids of all neighbors of this agent

	// New fields by Sahar for agent's strategy, AgentView, Agent's Gain
	private String strategy; //agent strategy
	private HashMap<Integer, String> agentView = new HashMap<Integer, String>(); //holds ids of neighbors and their strategies
	private int agentGain; //sum of the agent gain from all turns in the current round

	private Game game; //the game being played
	private int numIterations;



	/*
	 * Constructor parameters:
	 * id - Agent's unique identifier.
	 * mailer - A reference to the mailer for communication.
	 * neighbors - A set of IDs representing neighboring agents.
	 * n - Total number of agents in the system.
	 * strategy - The current strategy of the agent.
	 */
	public Agent(int id, Mailer mailer, int n, Game game) {
		this.id = id;
		this.mailer = mailer;
		//this.neighbors=neighbors;
		this.agents = n;
		this.game=game;
		initializeRandomStrategy(); //each agent starts with a random strategy based on the game type

	}


	// By Sahar Getter for Agent's Gain
	public int getAgentGain() {
		return agentGain;
	}

	@Override
	public void run() {
		boolean hasChangedStrategy;
		while (true) {

			numIterations++; // Increment round count
			System.out.println("id: "+id+" round: "+numIterations);

			// Create a message containing the current strategy
			Message strategyMessage = new AssignmentMessage(id, strategy);

			// Send the strategy message to all neighbors
			for (Agent neighbor : neighbors) {
				int neighborId= neighbor.getId();
				mailer.send(neighborId, strategyMessage);
			}

			// Collect the strategies of neighboring agents
			// Continue collecting until we have received all neighbors' strategies
			while (agentView.size() < neighbors.size()) {
				// Read a strategy message from the mailbox
				AssignmentMessage receivedMessage = (AssignmentMessage) mailer.readOne(id);

				if (receivedMessage != null) {
					int neighborId = receivedMessage.getSender();
					String neighborStrategy = receivedMessage.getStrategy();
					agentView.put(neighborId, neighborStrategy); // insert message to agent view
				}
			}

			// Compute the best response strategy based on neighboring strategies
			String bestResponse = computeBestResponse(agentView); //need to write function

			// Update agent's strategy and check if it has changed
			if (!strategy.equals(bestResponse)) {
				strategy = bestResponse;
				System.out.println("strategy changed to :"+bestResponse +"Id: "+id);
				hasChangedStrategy = true;
			} else {
				hasChangedStrategy = false;
			}

			// Terminate if no agent changes strategy in a round
			if (!hasChangedStrategy) {
				System.out.println("strategy didnt changed , it is: :"+strategy);
				break;
			}
		}

		//totalAgentGains.addAndGet(agentGain); //should continue ?
		mailer.addToTotalGain(agentGain);
		//mailer.addToTotaliterations(numIterations);
		mailer.determineCurrentIterations(numIterations);



		}


	//compute the best response according to the neighbors assignments from the last round
	private String computeBestResponse(HashMap<Integer, String> agentView)
	{
		String bestResponse = strategy; // Initialize with the agent's current strategy
		AtomicInteger payoff0 = new AtomicInteger(0);
		AtomicInteger payoff1 = new AtomicInteger(0);
		// Access the game-specific matrix based on the agent's game and gender
		VarTuple[][] matrix;

		for( Agent neighbor : neighbors)
		{
			System.out.println("agent strategy "+ strategy+"Id: "+id);
			System.out.println("neighbor strategy "+ neighbor.strategy+"Id: "+neighbor.id);

			if (game instanceof BattleOfSexes)
			{
				if(this instanceof Husband)
				{
					System.out.println("agent " +id+" - husband");

					if(neighbor instanceof Husband) {
						System.out.println("neighbor " +neighbor.id+ "- husband");

						matrix = ((BattleOfSexes) game).getMan_man();
					}
					else {
						matrix = ((BattleOfSexes) game).getMan_woman();
						System.out.println("neighbor " +neighbor.id+ "- wife");

					}
				}
				else{
					System.out.println("agent " +id+ "- wife");
					if(neighbor instanceof Husband) {
						matrix = ((BattleOfSexes) game).getWoman_man();
						System.out.println("neighbor " +neighbor.id+ "- husband");

					}
					else {
						matrix = ((BattleOfSexes) game).getWoman_woman();
						System.out.println("neighbor "+ neighbor.id+ "- wife");

					}
				}
			}
			else
				matrix=((PrisonersDilemma)game).getMatrix(); //instanceof PD game

			if (matrix == null) {
				System.out.println("null matrix");
				// Your code for accessing the matrix and performing calculations
			}
			String neighborStrategy=agentView.get(neighbor.getId());
			int neighborIndex;
			if(game instanceof PrisonersDilemma) {
				neighborIndex = ((PrisonersDilemma) game).getStrategyIndex(neighborStrategy);
				int value0=matrix[0][neighborIndex].getI();
				int value1=matrix[1][neighborIndex].getI();
				payoff0.addAndGet(value0);
				payoff1.addAndGet(value1);
			}
			else {
				neighborIndex = game.getStrategyIndex(neighborStrategy);
				int value0 = matrix[0][neighborIndex].getI();
				int value1 = matrix[1][neighborIndex].getI();
				payoff0.addAndGet(value0);
				payoff1.addAndGet(value1);
			}

		}
		if(payoff0.get()>payoff1.get())
		{
			agentGain=payoff0.get();
			if (game instanceof PrisonersDilemma)
				return "Defect";
			else
				return "Theatre";
		}
		else {
			agentGain=payoff1.get();
			if (game instanceof PrisonersDilemma)
				return "Cooperate";
			else
				return "Soccer";
		}








	}

	private void initializeRandomStrategy() {
		Random random = new Random();

		if (game instanceof BattleOfSexes) {
			// Battle of the Sexes game - Initialize with "Theatre" or "Soccer"
			String[] bosStrategies = { "Theatre", "Soccer" };
			int randomIndex = random.nextInt(bosStrategies.length);
			strategy = bosStrategies[randomIndex];

		} else if (game instanceof PrisonersDilemma) {
			// Prisoner's Dilemma game - Initialize with "Cooperate" or "Defect"
			String[] pdStrategies = { "Cooperate", "Defect" };
			int randomIndex = random.nextInt(pdStrategies.length);
			strategy = pdStrategies[randomIndex];
		} else {
			// Handle other game types or default behavior
			// You can set a default strategy here if needed

			return;

		}
	}

	public int getId() {
		return id;
	}

	public void setNeighbors(HashSet<Agent> neighbors) {
		this.neighbors = neighbors;
	}


}


