import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		// extract parameters
		int n = Integer.valueOf(args[0]).intValue();
		String Type = args[1];// PD/BoS
		double p1 = Double.valueOf(args[2]).doubleValue();
		double pw = 0.0;

		if (Type.equals("BoS")) { //BoS is selected
			if (args.length >= 4) {
				pw = Double.valueOf(args[3]).doubleValue();
			} else {
				System.out.println("Missing argument pw for BoS type.");
				return;
			}
		}

		// Initialize variables to accumulate values
		int totalNumIterations = 0;
		double totalSW = 0.0;


	// Run the Best-Response algorithm for 100 random problems
		for (int run = 0; run < 100; run++) {

			System.out.println("game number: "+run);
		// generate game
			Generator gen = new Generator(n, p1, Type, pw);
			Game game = gen.generateGame();

			// initialize mailer
			Mailer mailer = new Mailer();
			for (int i = 0; i < n; i++) {
				mailer.put(i);
			}

			List<Agent> agents = new ArrayList<>(); // Store agents in a list
			// create agents
			for (int i = 0; i < n; i++) {
				// Create an Agent and pass its neighbor list
				Agent agent;

				if (Type.equals("BoS")) {//if bos then we need to create husbands and wives:
					if (Math.random() <= pw) {
						agent = new Wife(i, mailer, n, game);
					} else {
						agent = new Husband(i, mailer, n, game);
					}
				} else {
					agent = new Agent(i, mailer, n,game);
				}

				agents.add(agent); // Add the agent to the list
			}




			// Run agents as threads
			ArrayList<Thread> threads = new ArrayList<Thread>();
			for (Agent agent : agents) {
				agent.setNeighbors(game.neighborsOf(agent.getId(),agents));
				Thread t = new Thread(agent);
				threads.add(t);
			}

			// run agents as threads
			for (Thread t : threads) {
				t.start();
			}

			// wait for all agents to terminate
			for (Thread t : threads) {
				t.join();
			}

			// Accumulate values for this run
			int numIterations = mailer.getCurrentIterations();
			double SW = mailer.getTotalGain(); // total gain of agents for each game from 100 games

			totalNumIterations += numIterations;

			totalSW += SW;
		}

		// Calculate average values
		double averageNumIterations = (double) totalNumIterations / 100.0;
		double averageSW = totalSW / 100.0;

		// Print the average values
		System.out.println("Average Num_Iterations=" + averageNumIterations);
		System.out.println("Average SW=" + averageSW);

	}
}
