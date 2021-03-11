import javax.print.attribute.standard.MediaSize;
import java.util.Locale;
import java.util.Scanner;

public class Game {

    private Player[] players;
    private final int playerCount;
    private Dice dice = new Dice();
    private Board board = new Board();
    private Scanner scanner = new Scanner(System.in);

    public Game() {
        // Initialize the game

        System.out.println("How many players do you want?");
        String input;
        int playerNumber = 0;
        double playerFloat = 0;
        while (true) {
            input = scanner.nextLine();
            playerFloat = Double.parseDouble(input);
            playerNumber = (int)(Math.round(playerFloat));
            if (playerFloat == playerNumber) { // Make sure it's whole
                System.out.println("Please enter a whole number.");
            }
            else {
                if (playerNumber >= 2 && playerNumber <= 4) {
                    break;
                }
                else {
                    System.out.println("That's out of range, it needs to be between 2 and 4.");
                }
            }
        }

        this.players = new Player[playerNumber];
        this.playerCount = playerNumber;

        for (int i = 0; i < playerNumber; i++) {
            this.players[i] = new Player(i);
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

    public void start() {
        // Start the game
        int gameLoops = 0;

        while (true) {
            for (int pIndex = 0; pIndex < this.playerCount; pIndex++) {
                Player currentPlayer = players[pIndex];
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

                while (!turnEnded) {
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
                        System.out.println("That's invalid, please enter \"l\", \"r\", \"list\", \"list animals\", \"roll\" or \"roll dice\".");
                    }
                    System.out.println("");
                    System.out.println("====================");
                    System.out.println("");
                    if (choice == 0) {
                        AnimalBoardSpace[] playerAnimals = currentPlayer.getAnimals();

                        System.out.println("Your animals:");
                        System.out.println("---");

                        for (int i = 0; i < playerAnimals.length; i++) {
                            AnimalBoardSpace animal = playerAnimals[i];
                            System.out.println("Animal " + animal.getID() + " (" + playerAnimals[i].getName() + ")");
                        }

                        System.out.println("---");
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
                                currentPlayer.payPlayer(owner, price);
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
                        // Draw card
                        Card card = board.getTopCard();
                        Enums.CardAbility cardAbility = card.getAbility();
                        System.out.println("You drew a " + cardAbility + " card.");

                        if (cardAbility == Enums.CardAbility.lose100) {
                            System.out.println("You lose 100.");
                            currentPlayer.adjustMoney(-100);
                        }
                    }
                }
            }
            gameLoops++;
        }
    }
}
