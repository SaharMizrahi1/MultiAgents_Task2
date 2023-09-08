public class BattleOfSexes extends Game{

    VarTuple [][] man_man = new VarTuple[2][2];
    VarTuple [][] woman_woman = new VarTuple[2][2];

    public BattleOfSexes(boolean[][] network) {
        super();
        this.network=network;
        initializeMatrix();
        initializeMan_Man();
        initializeWoman_Woman();
    }

    private void initializeMatrix() {
        matrix[0][0] = new VarTuple(3, 1);
        matrix[0][1] = new VarTuple(0, 0);
        matrix[1][0] = new VarTuple(0, 0);
        matrix[1][1] = new VarTuple(1, 3);
    }

    private void initializeWoman_Woman() {
        woman_woman[0][0] = new VarTuple(3, 3);
        woman_woman[0][1] = new VarTuple(3, 1);
        woman_woman[1][0] = new VarTuple(1, 3);
        woman_woman[1][1] = new VarTuple(1, 1);
    }

    private void initializeMan_Man() {
        man_man[0][0] = new VarTuple(1, 1);
        man_man[0][1] = new VarTuple(1, 3);
        man_man[1][0] = new VarTuple(3, 1);
        man_man[1][1] = new VarTuple(3, 3);
    }

    public VarTuple[][] getMan_man() {
        return man_man;
    }

    public VarTuple[][] getWoman_woman() {
        return woman_woman;
    }
}
