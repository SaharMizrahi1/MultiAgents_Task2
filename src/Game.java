import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Game {
    VarTuple [][] matrix = new VarTuple[2][2];
    boolean[][] network;



    public void initializeMatrix() {

    }

    public SortedSet<Integer> neighborsOf(int i) { //returns a set of agent i's neighbors
        SortedSet<Integer> neighbors = new TreeSet<Integer>();
        for (int k = 0; k < network.length; k++) {
            for (int j = k + 1; j < network.length; j++) {
                if (k==i)
                    neighbors.add(j);
                if(j==i)
                    neighbors.add(k);

            }
        }
        return neighbors;
    }


}
