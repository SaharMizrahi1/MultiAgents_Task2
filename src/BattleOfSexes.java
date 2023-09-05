public class BattleOfSexes extends Game{

    public BattleOfSexes(boolean[][] network) {
        super();
        this.network=network;
        initializeMatrix();
    }

    public void initializeMatrix() {
        matrix[0][0] = new VarTuple(3, 1);
        matrix[0][1] = new VarTuple(0, 0);
        matrix[1][0] = new VarTuple(0, 0);
        matrix[1][1] = new VarTuple(1, 3);
    }

}
