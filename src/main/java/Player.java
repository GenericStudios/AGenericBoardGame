import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int id;
    private int money = 100;
    private int boardPosition = 0;
    private boolean missNextTurn = false;
    private List<AnimalBoardSpace> animals = new ArrayList<AnimalBoardSpace>();

    public Player(int myID) {
        this.id = myID;
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
        this.money += amount;
        return this.money;
    }

    public void payPlayer(Player otherPlayer, int amount) {
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
