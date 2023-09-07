
import java.util.HashMap;

public class Generator {

	private String Type;
	private int n;
	private double p1, pw;

	/*
	 * constructor parameters -
	 * n, number of agents

	 * p1, probability that two agents are constrained
	 *
	 */
	public Generator(int n, double p1, String Type, double pw) {
		this.n = n;
		this.p1 = p1;
		this.Type = Type;
	//	this.p2 = p2;
	}

	// generate masp
	public Game generateGame() { //Define a probability p1 to connect two agents.
		
		//HashMap<VarTuple, ConsTable> cons_tables = new HashMap<VarTuple, ConsTable>();
		boolean[][] neighbors = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {

				if (Math.random() < p1) {
					neighbors[i][j] = true;
					neighbors[j][i] = true; // set the corresponding cell symmetrically
					//VarTuple at = new VarTuple(i, j);
//					ConsTable ct = new ConsTable(d, p2);
//					cons_tables.put(at, ct);
				}
				else
					neighbors[i][j] = false;


			}
		}
		if (Type.equals("PD")) { // Prisoner's Dilemma
			return new PrisonersDilemma(neighbors);
		} else if (Type.equals("BoS")) { // Battle of the Sexes
			return new BattleOfSexes(neighbors);
		} else {
			// Handle default behavior
			return null;
		}
		//return new pd / battleOFsexes ?
		//return new MASP(n,neighbors,Type,pw);
	}
}
