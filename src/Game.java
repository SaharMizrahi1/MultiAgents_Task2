import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Game {

    boolean[][] network;


    public SortedSet<Integer> neighborsIDOf(int i) { //returns a set of agent it's neighbors
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

    public SortedSet<Agent> neighborsOf(int i, List<Agent> agents) { //returns a set of agent it's neighbors
        SortedSet<Agent> neighbors = new TreeSet<Agent>();

        for (Agent agent: agents)
        {
            if(neighborsIDOf(i).contains(agent.getId()))
            {
                neighbors.add(agent);
            }
        }

        return neighbors;
    }


    public int getStrategyIndex(String strategy) {
       return -1;

    }



}
