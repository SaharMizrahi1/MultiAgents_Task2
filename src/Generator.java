
import java.util.HashMap;

public class Generator {

	private String Type;
	private int n;
	private double p1, pw;


	public Generator(int n, double p1, String Type, double pw) {
		this.n = n;
		this.p1 = p1;
		this.Type = Type;
		this.pw = pw;
	}

	public Game generateGame() {
		
		boolean[][] neighbors = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {

				if (Math.random() < p1) {
					neighbors[i][j] = true;
					neighbors[j][i] = true; // set the corresponding cell symmetrically
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

	}


}
