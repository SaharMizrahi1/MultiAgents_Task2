public class PrisonersDilemma extends Game {

    public PrisonersDilemma() {
        super();
        initializeMatrix();
    }

    public void initializeMatrix() {
        matrix[0][0] = new VarTuple(5, 5);
        matrix[0][1] = new VarTuple(10, 0);
        matrix[1][0] = new VarTuple(0, 10);
        matrix[1][1] = new VarTuple(8, 8);
    }




}
