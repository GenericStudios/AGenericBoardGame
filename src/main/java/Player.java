import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int id;
    private final Game game;
    private int money = 100;
    private int boardPosition = 0;
    private boolean missNextTurn = false;
    private List<AnimalBoardSpace> animals = new ArrayList<AnimalBoardSpace>();

    public Player(int myID, Game game) {
        this.id = myID;
        this.game = game;
    }

    public boolean canPurchase(AnimalBoardSpace animal) {
        return this.money >= animal.getPurchasePrice();
    }

    public void purchase(AnimalBoardSpace animal) {
        this.money -= animal.getPurchasePrice();
        this.animals.add(animal);
        animal.setOwner(this);
    }

    public int getBoardPosition() {
        return this.boardPosition;
    }

    public int moveGetPosition(int amount) {
        int newPosition = this.boardPosition + amount;
        if (newPosition >= 26) {
            newPosition -= 26;
        }
        this.boardPosition = newPosition;
        return newPosition;
    }

    public int adjustMoney(int amount) {
        Player currentPlayer = this.game.currentPlayer;
        this.money += amount;
        if (this.money <= 0) {
            if (this.getID() == currentPlayer.getID()) {
                System.out.println("You ran out of money!");
                System.out.println("You lose.");
            }
            else {
                System.out.println("Player " + (this.getID() + 1) + " ran out of money!");
                System.out.println("They're now out of the game.");
            }
            game.removePlayer(this);
            game.playersLeft--;
        }
        return this.money;
    }

    public void payPlayer(Player otherPlayer, int amount, Player currentPlayer) {
        otherPlayer.adjustMoney(amount);
        this.adjustMoney(-amount);
    }

    public void setMissNextTurn(boolean value) {
        this.missNextTurn = value;
    }

    public void upgrade(AnimalBoardSpace animal) {
        animal.upgrade();
        this.money -= animal.getPurchasePrice();
    }

    public int getMoney() {
        return this.money;
    }
    public int getID() {
        return this.id;
    }

    public boolean getMissNextTurn() {
        return this.missNextTurn;
    }

    public AnimalBoardSpace[] getAnimals() {
        // Convert arraylist to array and return it
        AnimalBoardSpace[] myAnimals = new AnimalBoardSpace[this.animals.size()];
        myAnimals = this.animals.toArray(myAnimals);
        return myAnimals;
    }

    public boolean canUpgrade(AnimalBoardSpace animal) {
        // TODO: Fix being able to upgrade animal too far
        return (animal.getStage() < animal.getStopPrices().length && this.money >= animal.getPurchasePrice());
    }
}
