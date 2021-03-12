public class Dice {
    private  int rollSingle() {
        return (int)((Math.random() * 6) + 1);
    }

    public int roll() {
        return rollSingle() + rollSingle();
    }
}
