public class Game {

    Player[] players;
    Dice dice = new Dice();
    Board board = new Board();

    public Game() {
        // Initialize the game

        // TODO: Ask user how many players, for now we'll do 3
        this.players = new Player[3];

        for (int i = 0; i < this.players.length; i++) {
            this.players[i] = new Player(i);
        }
    }

    public void start() {
        // Start the game
        int gameLoops = 0;
        int playerCount = players.length;

        while (true) {
            for (int pIndex = 0; pIndex < playerCount; pIndex++) {
                Player currentPlayer = players[pIndex];
                System.out.println(currentPlayer.getID());
            }
            gameLoops++;
            break;
        }

    }

}
