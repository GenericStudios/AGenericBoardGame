public class Enums {

    public enum BoardSpaceTypes {
        animal, // Ordinary spot where there's an animal
        other // A spot where you don't purchase anything like start / miss a turn
    };

    public enum OtherBoardSpaceTypes {
        start, // You get money for landing on / passing this
        missTurn // You miss a turn for landing on this
    };

    public enum CardAbility {
        lose100, // The card makes you lose 100 gens/money
        gain100, // Gain 100
        notStonks, // Lose 1,000
        stonks, // Gain 5,000
        steal300 // Steal 300 from another player
    };

}