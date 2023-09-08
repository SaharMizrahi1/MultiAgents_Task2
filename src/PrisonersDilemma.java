public class PrisonersDilemma extends Game {

    VarTuple [][] matrix = new VarTuple[2][2];
    public PrisonersDilemma(boolean[][] network) {
        super();
        this.network=network;
        initializeMatrix();
    }

    public void initializeMatrix() {
        matrix[0][0] = new VarTuple(5, 5);
        matrix[0][1] = new VarTuple(10, 0);
        matrix[1][0] = new VarTuple(0, 10);
        matrix[1][1] = new VarTuple(8, 8);
    }

    public int getStrategyIndex(String strategy) {
        if(strategy.equals("Cooperate"))
            return 1;
        else
            return 0;

    }

    public VarTuple[][] getMatrix() {
        return matrix;
    }




}
