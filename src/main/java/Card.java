public class Card {

    private Enums.CardAbility ability;

    public Card(Enums.CardAbility myAbility) {
        this.ability = myAbility;
    }

    public Enums.CardAbility getAbility() {
        return this.ability;
    }

}
