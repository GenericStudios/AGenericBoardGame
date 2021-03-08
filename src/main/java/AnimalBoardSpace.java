public class AnimalBoardSpace extends BoardSpace {

    // Player owner
    Player owner;
    // Level 0,1,2,3
    int stage = 0;

    // Base Purchase Price
    final int purchasePrice;
    // Upgrade Price
    final int upgradePrice;
    // Stop Prices per stage
    final int[] stopPrices;
    // Name
    final String name;

    public AnimalBoardSpace(int myID, AnimalTemplate animal) {
        super(myID, Enums.BoardSpaceTypes.animal);
        this.purchasePrice = animal.purchasePrice;
        this.upgradePrice = animal.upgradePrice;
        this.stopPrices = animal.stopPrices;
        this.name = animal.name;
    }

    public void setOwner(Player myOwner) {
        this.owner = myOwner;
    }

    public Player getOwner() {
        return this.owner;
    }

    public int getCurrentStopPrice() {
        return this.stopPrices[this.stage];
    }
}
