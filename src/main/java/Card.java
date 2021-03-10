public class Card {

    private int id;
    private Enums.CardAbility ability;

    public Card(int myID, Enums.CardAbility myAbility) {
        this.id = myID;
        this.ability = myAbility;
    }

    public Enums.CardAbility getAbility() {
        return this.ability;
    }

    public int getID() {
        return this.id;
    }
}
