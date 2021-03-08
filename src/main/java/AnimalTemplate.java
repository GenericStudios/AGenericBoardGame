public class AnimalTemplate {

    // Base Purchase Price
    final int purchasePrice;
    // Upgrade Price
    final int upgradePrice;
    // Stop Prices per stage
    final int[] stopPrices;
    // Name
    final String name;

    public AnimalTemplate(int myPurchasePrice, int myUpgradePrice, int[] myStopPrices, String myName) {
        this.purchasePrice = myPurchasePrice;
        this.upgradePrice = myUpgradePrice;
        this.stopPrices = myStopPrices;
        this.name = myName;
    }

}
