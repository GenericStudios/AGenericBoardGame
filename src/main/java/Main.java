public class Main {
    // Or here
    public static void main(String[] args) {

        // Initialize Objects
        // TODO: Ask user how many players, for now we'll do 3

        // This creates placeholders for the player but doesn't initialize them or give them any values
        Player[] players = new Player[3];

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i + 1);
            System.out.println(players[i].getID());
        }

        // Create dice
        Dice dice = new Dice();

        // Setup board
        Board board = new Board();

    }
    // Don't put code here
    // Flashback to spending 15+ mins trying to work out why it didn't work...
}
