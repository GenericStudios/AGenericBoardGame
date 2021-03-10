import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Board {

    // Initialize attributes

    private Card[] cards = new Card[20];
    private BoardSpace[] boardSpaces = new BoardSpace[26];

    public Board() {
        // Build Board Spaces

        /*
            0: Start
            1-12: 1-12
            13: Miss a turn
            14-25: 14-25
        */

        Random animalGenerator = new Random();
        int animalGeneratorLength = Animals.animalsArray.length;

        for (int i = 0; i < 26; i++) {

            BoardSpace space;
            int randomIndex = animalGenerator.nextInt(animalGeneratorLength);
            AnimalTemplate animal = Animals.animalsArray[randomIndex];

            if (i == 0) { // Start Space
                space = new OtherBoardSpace(i, Enums.OtherBoardSpaceTypes.start);
            } else if (i <= 12) { // 1 to 12
                space = new AnimalBoardSpace(i, animal);
            } else if (i == 13) { // Miss a turn Space
                space = new OtherBoardSpace(i, Enums.OtherBoardSpaceTypes.missTurn);
            } else { // 14 to 25
                space = new AnimalBoardSpace(i, animal);
            }
            boardSpaces[i] = space;
        }

        // Setup Cards

        for (int i = 0; i < 20; i++) {
            cards[i] = new Card(i, Enums.CardAbility.lose100);
        }

        // Debug Setup
        for (int i = 0; i < boardSpaces.length; i++) {
            boardSpaces[i].debugSpace();
        }

    }

    public BoardSpace getBoardSpace(int pos) {
        return this.boardSpaces[pos];
    }

    public Card getTopCard() {
        Card top = this.cards[0];
        Collections.rotate(Arrays.asList(this.cards), -1);

        return top;
    }

}
