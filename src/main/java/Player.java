import java.util.ArrayList;
import java.util.List;

public class Player {
    final int id;
    int money = 0;
    List<AnimalBoardSpace> animals = new ArrayList<AnimalBoardSpace>();

    public Player(int myID) {
        this.id = myID;
    }

    public boolean canPurchase(AnimalBoardSpace animal) {
        return this.money >= animal.getCurrentStopPrice();
    }

    public void purchase(AnimalBoardSpace animal) {
        this.money -= animal.getCurrentStopPrice();
        this.animals.add(animal);
        animal.setOwner(this);
    }

    public int adjustMoney(int amount) {
        this.money += amount;
        return this.money;
    }

    public int getMoney() {
        return this.money;
    }
    public int getID() {
        return this.id;
    }
}
