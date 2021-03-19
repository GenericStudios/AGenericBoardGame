public class Main {
    // Or here
    public static void main(String[] args) {
        boolean wantsToPlay = true;

        while (wantsToPlay) { // while the user still wants to play, create a new game object every time
            Game game = new Game();
            game.start();
            System.out.println("\nDo you want to play again?");
            wantsToPlay = game.getYesNo();
        }
        System.out.println("Bye!");
    }
    // Don't put code here
    // Flashback to spending 15+ mins trying to work out why it didn't work...
}
