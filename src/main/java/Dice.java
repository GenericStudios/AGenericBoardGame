public class Dice {
    public int roll() {
        return (int) (Math.random() * (7)) + (int) (Math.random() * (7));
    }
}
