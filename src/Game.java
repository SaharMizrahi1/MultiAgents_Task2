import java.util.*;

public class Game {

    boolean[][] network;


    public SortedSet<Integer> neighborsIDOf(int i) { //returns a set of the ids of the agent neighbors
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

    public HashSet<Agent> neighborsOf(int i, List<Agent> agents) { //returns a set of agents that are neighbors of i (Agent object)
        HashSet<Agent> neighbors = new HashSet<Agent>();

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
