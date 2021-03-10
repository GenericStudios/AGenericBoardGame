public class AnimalBoardSpace extends BoardSpace {

    // Player owner
    private Player owner;
    // Level 0,1,2,3
    private int stage = 0;

    // Base Purchase Price
    private final int purchasePrice;
    // Stop Prices per stage
    private final int[] stopPrices;
    // Name
    private final String name;

    public AnimalBoardSpace(int myID, AnimalTemplate animal) {
        super(myID, Enums.BoardSpaceTypes.animal);
        this.purchasePrice = animal.purchasePrice;
        this.stopPrices = animal.stopPrices;
        this.name = animal.name;
    }

    public void printStats() {
        System.out.println("");
        System.out.println("Animal " + this.getID());
        System.out.println("===");
        System.out.println("Name: " + this.name);
        String upgradeStage;
        if (this.stage == 0) {
            upgradeStage = "None";
        } else {
            upgradeStage = String.valueOf(this.stage);
        }
        System.out.println("Upgrade stage: " + upgradeStage);
        for (int i = 0; i < this.stopPrices.length; i++) {
            String prefix;
            if (i == 0) {
                prefix = "No upgrade price: ";
            } else {
                prefix = "Upgrade " + String.valueOf(i) + " price: ";
            }
            if (i == this.stage) {
                prefix = "-> " + prefix;
            }
            System.out.println(prefix + this.stopPrices[i]);
        }
        System.out.println("Purchase/Upgrade price: " + this.purchasePrice);
        Player owner = this.owner;
        String ownerName;
        if (owner == null) {
            ownerName = "None";
        } else {
            ownerName = "Player " + String.valueOf(owner.getID() + 1);
        }
        System.out.println("Owner: " + ownerName);
        System.out.println("===");
        System.out.println("");
    }

    public void setOwner(Player myOwner) {
        this.owner = myOwner;
    }

    public void upgrade() {
        this.stage++;
    }

    public Player getOwner() {
        return this.owner;
    }

    public int getStage() {
        return this.stage;
    }

    public int getPurchasePrice() {
        return this.purchasePrice;
    }

    public int[] getStopPrices() {
        return this.stopPrices;
    }

    public String getName() {
        return this.name;
    }

    public int getCurrentStopPrice() {
        return this.stopPrices[this.stage];
    }
}
