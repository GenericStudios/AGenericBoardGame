import com.sun.tools.javac.util.StringUtils;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

// TODO: prevent player from buying/upgrading when money afterwards would be 0

public class Game {

    private Player[] players;
    private final int playerCount;
    private Dice dice = new Dice();
    private Board board = new Board();
    private Scanner scanner = new Scanner(System.in);

    public Player currentPlayer;
    public int playersLeft;

    public Game() {
        // Initialize the game

        System.out.println("How many players do you want?");
        String input;
        int playerNumber = 0;
        double playerFloat = 0;
        while (true) {
            input = scanner.nextLine();
            if (isNumber(input)) {
                playerFloat = Double.parseDouble(input);
                playerNumber = (int)(Math.round(playerFloat));
                if (playerFloat == playerNumber) { // Make sure it's whole
                    if (playerNumber >= 2 && playerNumber <= 4) {
                        break;
                    }
                    else {
                        System.out.println("That's out of range, it needs to be between 2 and 4.");
                    }
                }
                else {
                    System.out.println("Please enter a whole number.");
                }
            }
            else {
                System.out.println("Please enter a number.");
            }
        }

        this.players = new Player[playerNumber];
        this.playerCount = playerNumber;

        for (int i = 0; i < playerNumber; i++) {
            this.players[i] = new Player(i, this);
        }
    }

    public boolean getYesNo() {
        boolean choice = false;
        while (true) {
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("y") || input.equals("n") || input.equals("yes") || input.equals("no")) {
                return input.equals("y") || input.equals("yes");
            }
            System.out.println("That's invalid, please enter either \"y\", \"n\", \"yes\" or \"no\".");
        }
    }
    public boolean isNumber(String number) { // Had to implement this method myself because it's apparently easier than attempting to import it from a library
        String numbers = "0123456789";

        boolean dot = false;
        int length = number.length();
        for (int i = 0; i < length; i++) {
            char character = number.charAt(i);
            if (numbers.indexOf(character) == -1) {
                if (character == '.' && (! dot)) {
                    dot = true;
                }
                else {
                    if (i != 0 || character != '-') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void start() {
        // Start the game
        int gameLoops = 0;
        this.playersLeft = playerCount;

        boolean gameOver = false;
        while (! gameOver) {
            for (int pIndex = 0; pIndex < this.playerCount; pIndex++) {
                if (gameOver) {
                    break;
                }

                Player currentPlayer = players[pIndex];
                this.currentPlayer = currentPlayer;
                if (currentPlayer == null) {
                    continue;
                }

                System.out.println("");
                System.out.println("====================");
                System.out.println("");
                System.out.println("Current Player: " + (currentPlayer.getID() + 1) + ".");

                boolean turnEnded = false;

                if (currentPlayer.getMissNextTurn()) {
                    currentPlayer.setMissNextTurn(false);
                    turnEnded = true;
                    System.out.println("You missed your turn.");
                }

                Boolean drawnCard = false;
                while (! turnEnded) {
                    if (gameOver) {
                        break;
                    }
                    int oldPos = currentPlayer.getBoardPosition();

                    System.out.println("");
                    System.out.println("====================");
                    System.out.println("");
                    System.out.println("You have " + currentPlayer.getAnimals().length + " animals and " + currentPlayer.getMoney() + " money.");
                    System.out.println("Would you like to: (l) List Animals you own, (r) Roll dice or (d) Draw card.");

                    // 0 = list animals
                    // 1 = roll dice
                    // 2 = draw card
                    int choice;
                    while (true) {
                        String input = scanner.nextLine().toLowerCase();
                        if (input.equals("l") || input.equals("list") || input.equals("list animals") || input.equals("r") || input.equals("roll") || input.equals("roll dice") || input.equals("d") || input.equals("draw") || input.equals("draw card")) {
                            if (input.equals("l") || input.equals("list") || input.equals("list animals")) {
                                choice = 0;
                            } else if (input.equals("r") || input.equals("roll") || input.equals("roll dice")) {
                                choice = 1;
                            } else {
                                choice = 2;
                            }
                            break;
                        }
                        System.out.println("That's invalid, please enter \"l\", \"r\", \"d\", \"list\", \"list animals\", \"roll\", \"roll dice\", \"draw\" or \"draw card\".");
                    }
                    System.out.println("");
                    System.out.println("====================");
                    System.out.println("");
                    if (choice == 0) {
                        AnimalBoardSpace[] playerAnimals = currentPlayer.getAnimals();

                        if (playerAnimals.length == 0) {
                            System.out.println("You don't have any animals.");
                        }
                        else {
                            System.out.println("Your animals:");

                            for (int i = 0; i < playerAnimals.length; i++) {
                                AnimalBoardSpace animal = playerAnimals[i];
                                System.out.println(" * Animal " + animal.getID() + " (" + playerAnimals[i].getName() + ")");
                            }
                        }
                    } else if (choice == 1) {
                        // Role Dice and Move
                        int roll = dice.roll();
                        System.out.println("You rolled a " + roll + ".");

                        int newPos = currentPlayer.moveGetPosition(roll);
                        BoardSpace newSpace = board.getBoardSpace(newPos);
                        System.out.println("You are now on position: " + newPos + "/26.");

                        // Pass Start Logic
                        if (newPos < oldPos) {
                            if (newPos == 0) {
                                System.out.println("Landed on start, get 1000!");
                                currentPlayer.adjustMoney(1000);
                            } else {
                                System.out.println("Passed start, get 500!");
                                currentPlayer.adjustMoney(500);
                            }
                        }

                        if (newSpace.getType() == Enums.BoardSpaceTypes.animal) {
                            AnimalBoardSpace animal = (AnimalBoardSpace)newSpace;
                            String animalName = animal.getName();
                            Player owner = animal.getOwner();
                            System.out.println("");
                            System.out.println("You landed on an animal!");
                            animal.printStats();
                            if (owner == null) {
                                // Purchase

                                if (currentPlayer.canPurchase(animal)) {

                                    System.out.println("You can purchase " + animalName + " at this space for " + animal.getPurchasePrice() + " money. Would you like to?");

                                    boolean purchase = getYesNo();

                                    if (purchase) {
                                        currentPlayer.purchase(animal);
                                        System.out.println("You purchased " + animalName + ". You now have " + currentPlayer.getMoney() + " money.");

                                        animal.printStats();
                                    }
                                } else {
                                    System.out.println("You cannot afford this animal. You need " + (animal.getPurchasePrice() - currentPlayer.getMoney()) + " more money. (You have " + currentPlayer.getMoney() + ").");
                                }
                            } else if (owner == currentPlayer) {
                                // Check if can upgrade
                                if (currentPlayer.canUpgrade(animal)) {

                                    while (currentPlayer.canUpgrade(animal)) {
                                        System.out.println("You can upgrade " + animalName + " at this space for " + animal.getPurchasePrice() + " money, from level " + animal.getStage() + " to level " + (animal.getStage() + 1) + ". Would you like to?");

                                        boolean upgrade = getYesNo();

                                        if (upgrade) {
                                            currentPlayer.upgrade(animal);
                                            System.out.println("You upgraded " + animalName + ". You now have " + currentPlayer.getMoney() + " money.");

                                            animal.printStats();
                                        } else {
                                            break;
                                        }
                                    }
                                } else {
                                    System.out.println("You cannot upgrade this animal. You need " + (animal.getPurchasePrice() - currentPlayer.getMoney()) + " more money. (You have " + currentPlayer.getMoney() + ").");
                                }
                            } else {
                                // Pay owner
                                int price = animal.getCurrentStopPrice();
                                currentPlayer.payPlayer(owner, price, currentPlayer);
                                System.out.println("You landed on player " + (owner.getID() + 1) + "'s " + animalName + " and paid " + price + ". You now have " + currentPlayer.getMoney() + " money.");
                            }
                        } else {
                            OtherBoardSpace space = (OtherBoardSpace)newSpace;
                            if (space.getActionType() == Enums.OtherBoardSpaceTypes.missTurn) {
                                currentPlayer.setMissNextTurn(true);
                            }
                        }

                        turnEnded = true;
                    } else {
                        if (drawnCard) {
                            System.out.println("You've already drawn a card this turn.");
                        }
                        else {
                            drawnCard = true;

                            // Draw card
                            Card card = board.getTopCard();
                            Enums.CardAbility cardAbility = card.getAbility();

                            if (cardAbility == Enums.CardAbility.lose100) {
                                System.out.println("You drew a lose 100 card.");
                                System.out.println("You lost 100 money.");
                                currentPlayer.adjustMoney(-100);
                            }
                            else if (cardAbility == Enums.CardAbility.gain100) {
                                System.out.println("You drew a gain 100 card!");
                                System.out.println("You got 100 money!");
                                currentPlayer.adjustMoney(100);
                            }
                            else if (cardAbility == Enums.CardAbility.notStonks) {
                                System.out.println("You drew a not stonks card.");
                                System.out.println("You lost 1000 money.");
                                currentPlayer.adjustMoney(-1000);
                                System.out.println("(Not stonks)");
                            }
                            else if (cardAbility == Enums.CardAbility.stonks) {
                                System.out.println("You drew a stonks card!");
                                System.out.println("You gained 5000 money!");
                                currentPlayer.adjustMoney(5000);
                                System.out.println("STONKS!");
                            }
                            else if (cardAbility == Enums.CardAbility.steal300) {
                                System.out.println("You drew a steal 300 card!");
                                if (playersLeft == 2) {
                                    int otherPlayerID;
                                    for (otherPlayerID = 0; otherPlayerID < playerCount; otherPlayerID++) {
                                        if (players[otherPlayerID] != null && otherPlayerID != currentPlayer.getID()) {
                                            break;
                                        }
                                    }
                                    Player otherPlayer = players[otherPlayerID];
                                    // The divide by 2 is so that you only get half of what they had left if they don't have more than 300
                                    if (otherPlayer.getMoney() <= 300) {
                                        System.out.println("You stole the rest of player " + (otherPlayerID + 1) + "'s money. You gained " + (Math.min(otherPlayer.getMoney(), 600) / 2) + " money.");
                                    }
                                    else {
                                        System.out.println("You stole 300 from player " + (otherPlayerID + 1) + ".");
                                    }
                                    currentPlayer.adjustMoney((Math.min(otherPlayer.getMoney(), 600) / 2));
                                    otherPlayer.adjustMoney(-Math.min(otherPlayer.getMoney(), 300));
                                }
                                else {
                                    System.out.println("As there's more than 2 players left, you get to choose who to steal from.");
                                    System.out.println("Stealing from a player with 300 or less will make them lose, but you will only get half of what they had left.");
                                    System.out.println("Who do you want to steal from?");
                                    ArrayList<Integer> options = new ArrayList<Integer>();
                                    int otherPlayerID;
                                    for (otherPlayerID = 0; otherPlayerID < playerCount; otherPlayerID++) {
                                        if (players[otherPlayerID] != null && otherPlayerID != currentPlayer.getID()) {
                                            options.add(otherPlayerID);
                                            System.out.println(" * Player " + (otherPlayerID + 1) + " with " + players[otherPlayerID].getMoney() + " money");
                                        }
                                    }

                                    int inputNumber;
                                    while (true) {
                                        String words[] = scanner.nextLine().split(" ");
                                        String input = words[words.length - 1];
                                        if (isNumber(input)) {
                                            double inputDouble = Double.parseDouble(input);
                                            inputNumber = (int)(Math.round(inputDouble));
                                            if (inputNumber == inputDouble) { // Make sure it's whole
                                                inputNumber--;
                                                if (options.contains(inputNumber)) {
                                                    break;
                                                }
                                                else {
                                                    System.out.println("That's not an option.");
                                                }
                                            }
                                            else {
                                                System.out.println("That's not a whole number.");
                                            }
                                        }
                                        else {
                                            System.out.println("That's not a number.");
                                        }
                                    }

                                    Player otherPlayer = players[inputNumber];
                                    // The divide by 2 is so that you only get half of what they had left if they don't have more than 300
                                    if (otherPlayer.getMoney() <= 300) {
                                        System.out.println("You stole the rest of player " + (inputNumber + 1) + "'s money. You gained " + (Math.min(otherPlayer.getMoney(), 600) / 2) + " money.");
                                    }
                                    else {
                                        System.out.println("You stole 300 from player " + (inputNumber + 1) + ".");
                                    }
                                    currentPlayer.adjustMoney((Math.min(otherPlayer.getMoney(), 600) / 2));
                                    otherPlayer.adjustMoney(-Math.min(otherPlayer.getMoney(), 300));
                                }
                            }
                            else {
                                System.out.println("Error: unknown card.");
                            }
                        }
                    }


                    if (players[pIndex] == null) { // Current player is out of the game
                        turnEnded = true;
                        if (this.playersLeft == 1) {
                            int winningID;
                            for (winningID = 0; winningID < playerCount; winningID++) {
                                if (players[winningID] != null) {
                                    break;
                                }
                            }
                            System.out.println("Player " + (winningID + 1) + " won!");
                            gameOver = true;
                        }
                    }
                    else {
                        if (this.playersLeft == 1) {
                            gameOver = true;
                        }
                    }
                }
            }
            gameLoops++;
        }
    }

    public void removePlayer(Player player) {
        this.players[player.getID()] = null;
    }
}
