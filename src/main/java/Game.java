public class Game {
    public Game() {
        // Initialize the game

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

    public void start() {
        // Start the game
    }

}
