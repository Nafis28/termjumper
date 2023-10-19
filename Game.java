import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.*;

public class Game {
    private Player player;
    private List<Building> buildings;
    private int turnCount;
    private List<FuelCell> fuelCells;
    private List<Action> actions;
    private int totalTurns;  // New field
    private boolean gameOver;



    // Default constructor
    public Game() {
        this.player = null;
        this.buildings = null;
        this.turnCount = 0;
        this.fuelCells = null;
        this.actions = null;
        this.totalTurns = 0;  // Initialize to 0

    }

    // Non-default constructor
    public Game(Player player, List<Building> buildings, List<FuelCell> fuelCells, List<Action> actions) {
        this.player = player;
        this.buildings = buildings;
        this.turnCount = 0;
        this.fuelCells = fuelCells;
        this.actions = actions;
        this.totalTurns = 0;  // Initialize to 0


        // Initialize actions with their corresponding costs
        Action jumpForward = new Action("Jump forward", 2);
        Action jumpBackward = new Action("Jump backward", 2);
        Action skipTurn = new Action("Skip a turn", 0);

        this.actions.add(jumpForward);
        this.actions.add(jumpBackward);
        this.actions.add(skipTurn);
        this.totalTurns = 0;
        this.gameOver = false;
    }

    // Accessors
    public Player getPlayer() {
        return player;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public List<FuelCell> getFuelCells() {
        return fuelCells;
    }

    public List<Action> getActions() {
        return actions;
    }

    // Mutators
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public void setFuelCells(List<FuelCell> fuelCells) {
        this.fuelCells = fuelCells;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public void performAction(Action action) {
        Building currentBuilding = player.getCurrentBuilding();
        if (currentBuilding.isFrozen()) {
            System.out.println("The building is frozen. You must skip your turn.");
            player.skipTurn();
        } else if (action.getDescription().equals("Jump forward")) {
            player.jumpForward();
        } else if (action.getDescription().equals("Jump backward")) {
            player.jumpBackward();
        } else if (action.getDescription().equals("Skip a turn")) {
            // Player skips a turn, no action needed
        }
    }



    public void start() {

        Scanner scanner = new Scanner(System.in);
        while (turnCount < 10 && !gameOver) {

            displayGameState(); // Display the game state at the start of each turn
            System.out.println("Turn " + turnCount + " begins. What will you do?");
            handleTurn(scanner);
            turnCount++;

            // Shuffle buildings from index 1 to 14 (exclusive). This keeps building 0 and 15 in place.
            List<Building> buildingsToShuffle = new ArrayList<>(buildings.subList(1, 14));
            Collections.shuffle(buildingsToShuffle);
            buildings.subList(1, 14).clear();
            buildings.addAll(1, buildingsToShuffle);
        }
        end();
        scanner.close();
    }


    public void end() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\n_a_f\\Desktop\\University\\ITO4131 - Java Programming\\Assignment\\JumperGame\\src\\outcome.txt"))) {
            writer.write("Game over. Final stats:\n");
            writer.write("Player name: " + player.getPlayerName() + "\n");
            writer.write("Fuel charge: " + player.getfuelCharge() + "\n");
            writer.write("Fuel cells found: " + player.getFuelCellsFound() + "\n");
            writer.write("Turns taken: " + turnCount + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to outcome.txt.");
            e.printStackTrace();
        }
    }

    public void handleTurn(Scanner scanner) {
        Building currentBuilding = buildings.get(player.getCurrentBuildingIndex());
        if (currentBuilding.isFrozen()) {
            System.out.println("The building is frozen. You must skip your turn.");
            player.skipTurn();
        } else {
            System.out.println("Available actions:");
            for (int i = 0; i < actions.size(); i++) {
                System.out.println((i + 1) + ": " + actions.get(i).getDescription());
            }

            int actionChoice;
            do {
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Discard the invalid input
                }
                actionChoice = scanner.nextInt();
                if (actionChoice <= 0 || actionChoice > actions.size()) {
                    System.out.println("Invalid action choice. Please choose a number from 1 to " + actions.size() + ".");
                }
            } while (actionChoice <= 0 || actionChoice > actions.size());

            Action chosenAction = actions.get(actionChoice - 1);
            player.performAction(chosenAction);
            displayGameState(); // Display the game state after performing an action

        }
        // Check if the player's device fuel has hit 0
        if (player.getfuelCharge() <= 0) {
            System.out.println("Your device has run out of fuel. Game Over!");
            gameOver = true;
            return;
        }

        // Check if the player has reached the exit portal
        if (buildings.get(player.getCurrentBuildingIndex()).hasExitPortal()) {
            if (buildings.get(player.getCurrentBuildingIndex()).isFrozen()) {
                System.out.println("The exit portal is frozen. You cannot use it this turn.");
            } else {
                System.out.println("Congratulations, " + player.getPlayerName() + "! You've reached the exit portal and won the game!");
                gameOver = true;
            }
            return;
        }

        // Check if the player has found a fuel cell
        if (buildings.get(player.getCurrentBuildingIndex()).hasFuelCell()) {
            // Increase player's device charge
            player.setfuelCharge(player.getfuelCharge() + 5);
            // Remove the fuel cell from the building
            buildings.get(player.getCurrentBuildingIndex()).setFuelCell(false);
            // Increase the number of fuel cells found by the player
            player.incrementFuelCellsFound();
            System.out.println("You found a fuel cell! Device charge increased by 5 points.");
        }

        for (Iterator<FuelCell> iterator = fuelCells.iterator(); iterator.hasNext();) {
            FuelCell fuelCell = iterator.next();
            fuelCell.incrementTurnsSinceLastRecharge();  // Increment turnsSinceLastRecharge for the fuel cell

            // Check if the fuel cell is drained
            if (fuelCell.isDrained()) {
                // If the fuel cell is drained, remove it from the list
                iterator.remove();
                // You should also set the fuelCell flag of the building to false
                fuelCell.getLocation().setFuelCell(false);
            }
        }

        // Check if it's time to spawn a new fuel cell
        if (totalTurns % 3 == 0) {
            // Implement the logic to spawn a new fuel cell
            // You need to choose a building that does not currently have a fuel cell
            // Then create a new FuelCell object and add it to the fuelCells list
            // Also set the fuelCell flag of the building to true
        }

        totalTurns++;  // Increment the total number of turns

        changeWebTrapLocation();
        changeFrozenBuilding();
        displayGameState(); // Display the game state at the end of each turn
    }


    private void changeWebTrapLocation() {
        Random random = new Random();
        Building webTrappedBuilding = buildings.get(random.nextInt(buildings.size()));
        int newX = random.nextInt(5);
        int newY = random.nextInt(5);
        // Implement the code to change the web trap location here (if desired)
    }

    private void changeFrozenBuilding() {
        Random random = new Random();
        // Unfreeze the currently frozen building, if there is one.
        for (Building building : buildings) {
            if (building.isFrozen()) {
                building.setFrozen(false);
                break;
            }
        }
        // Freeze a random building.
        Building frozenBuilding = buildings.get(random.nextInt(buildings.size()));
        frozenBuilding.setFrozen(true);
        // Implement the code to freeze a random building here (if desired)
    }

    public void displayGameState() {
        Display display = new Display();
        display.showPlayerStatus(player);
        display.showPlayer(player, buildings);
        // Add a call to showProbabilities() here if you implement that method
    }




    // Additional methods for game logic can be added here.
    // ...

    // toString method
    public String toString() {
        return "Game[player=" + player.getPlayerName() + ", turnCount=" + turnCount + "]";
    }
}
