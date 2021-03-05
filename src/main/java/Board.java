public class Board {

    // Initialize attributes

    Card[] cards = new Card[20];
    BoardSpace[] boardSpaces = new BoardSpace[26];

    public Board() {
        // Build Board Spaces

        /*
            0: Start
            1-12: 1-12
            13: Miss a turn
            14-25: 14-25
        */

        for (int i = 0; i < 26; i++) {
            BoardSpace space;
            if (i == 0) { // Start Space
                space = new OtherBoardSpace(i, Enums.OtherBoardSpaceTypes.start);
            } else if (i <= 12) { // 1 to 12
                space = new AnimalBoardSpace(i);
            } else if (i == 13) { // Miss a turn Space
                space = new OtherBoardSpace(i, Enums.OtherBoardSpaceTypes.missTurn);
            } else { // 14 to 25
                space = new AnimalBoardSpace(i);
            }
            boardSpaces[i] = space;
        }

        // Setup Cards

        for (int i = 0; i < 20; i++) {
            cards[i] = new Card(Enums.CardAbility.lose100);
        }

        // Debug Setup
        for (int i = 0; i < boardSpaces.length; i++) {
            boardSpaces[i].debugSpace();
        }

    }
}
