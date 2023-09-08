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


			// generate and print CSP
			Generator gen = new Generator(n, p1, Type, pw);
			//MASP masp = gen.generateMASP();
			Game game = gen.generateGame();
			//masp.print();

			// initialize mailer
			Mailer mailer = new Mailer();
			for (int i = 0; i < n; i++) {
				mailer.put(i);
			}

			List<Agent> agents = new ArrayList<>(); // Store agents in a list

			// create agents

			for (int i = 0; i < n; i++) {
				// use the csp to extract the private information of each agent
				//SortedSet<Integer> AgentNeighbors = game.neighborsIDOf(i);

				// Create an Agent and pass its neighbor list
				Agent agent;

				if (Type.equals("BoS")) {//if bos then we need to create husbands and wives:
					if (Math.random() <= pw) {
						agent = new Wife(i, mailer, n);
					} else {
						agent = new Husband(i, mailer, n);
					}
				} else {
					agent = new Agent(i, mailer, n);
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
		}
	}
}
