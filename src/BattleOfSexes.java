public class BattleOfSexes extends Game{

    private VarTuple [][] man_woman = new VarTuple[2][2];
    private VarTuple [][] woman_man = new VarTuple[2][2];
    private VarTuple [][] man_man = new VarTuple[2][2];
    private VarTuple [][] woman_woman = new VarTuple[2][2];

    public BattleOfSexes(boolean[][] network) {
        super();
        this.network=network;
        initializeMan_Woman();
        initializeWoman_man();
        initializeMan_Man();
        initializeWoman_Woman();
    }

    private void initializeWoman_man() {
        woman_man[0][0] = new VarTuple(3, 1);
        woman_man[0][1] = new VarTuple(0, 0);
        woman_man[1][0] = new VarTuple(0, 0);
        woman_man[1][1] = new VarTuple(1, 3);
    }

    private void initializeMan_Woman() {
        man_woman[0][0] = new VarTuple(1, 3);
        man_woman[0][1] = new VarTuple(0, 0);
        man_woman[1][0] = new VarTuple(0, 0);
        man_woman[1][1] = new VarTuple(3, 1);
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

    public VarTuple[][] getMan_woman() {
        return man_woman;
    }

    public VarTuple[][] getWoman_man() {
        return woman_man;
    }

    public int getStrategyIndex(String strategy) {
        if(strategy.equals("Soccer"))
            return 1;
        return 0;


    }
}
