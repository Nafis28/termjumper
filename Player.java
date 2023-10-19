import java.util.List;

public class Player {
    private String playerName;
    private int currentBuildingIndex;
    private Building currentBuilding;
    private int fuelCharge;
    private int fuelCellsFound;

    private static final int MAX_CHARGE = 20;
    private static final int INITIAL_CHARGE = 10;

    private List<Building> buildings; // Add this field


    // Default constructor
    public Player() {
        this.playerName = "";
        this.currentBuildingIndex = 0;
        this.currentBuilding = null;
        this.fuelCharge = 0;
        this.fuelCellsFound = 0;
    }

    // Non-default constructor
    public Player(String playerName, List<Building> buildings) {
        this.playerName = playerName;
        this.currentBuildingIndex = 0;
        this.fuelCharge = INITIAL_CHARGE;
        this.fuelCellsFound = 0;
        this.buildings = buildings;
    }

    // Accessors
    public String getPlayerName() {
        return playerName;
    }

    public Building getCurrentBuilding() {
        return currentBuilding;
    }

    public int getCurrentBuildingIndex() {
        return currentBuildingIndex;
    }

    public int getfuelCharge() {
        return fuelCharge;
    }

    public int getFuelCellsFound() {
        return fuelCellsFound;
    }

    // Mutators
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setCurrentBuilding(Building currentBuilding) {
        this.currentBuilding = currentBuilding;
    }

    public void setCurrentBuildingIndex(int currentBuildingIndex) {
        this.currentBuildingIndex = currentBuildingIndex;
    }

    public void setfuelCharge(int fuelCharge) {
        this.fuelCharge = fuelCharge;
    }

    public void setFuelCellsFound(int fuelCellsFound) {
        this.fuelCellsFound = fuelCellsFound;
    }

    public void incrementFuelCellsFound() {
        this.fuelCellsFound++;
    }

    public void jumpForward() {
        if (this.currentBuildingIndex < buildings.size() - 2) { // Check if there are at least two buildings ahead
            Building currentBuilding = buildings.get(currentBuildingIndex);
            Building nextBuilding = buildings.get(currentBuildingIndex + 2); // Get the building two steps ahead
            int heightDifference = Math.abs(currentBuilding.getHeight() - nextBuilding.getHeight()) + 1;

            // Check if the next building is Frozen
            if (nextBuilding.isFrozen()) {
                System.out.println("You are frozen, skip a turn.");
                return; // Prevents moving to the frozen building
            }

            // Check if the next building is web trapped
            if (nextBuilding.isWebTrapped()) {
                heightDifference += 5;
            }

            if (fuelCharge >= heightDifference) {
                this.fuelCharge -= heightDifference; // Deduct the height difference from the device charge
                this.currentBuildingIndex += 2; // Jump two buildings ahead
                this.currentBuilding = buildings.get(currentBuildingIndex);
                System.out.println("Jumped forward. Device charge depleted by " + heightDifference + " points.");

                // Check if the new building has a fuel cell and if the player actually landed on this building
                if (this.currentBuilding.hasFuelCell() && (this.currentBuildingIndex == buildings.size() - 1 || this.currentBuildingIndex == buildings.size() - 2)) {
                    this.fuelCharge += 5; // Increase device charge by 5
                    this.currentBuilding.setFuelCell(false); // Remove the fuel cell from the building
                    System.out.println("You found a fuel cell! Device charge increased by 5 points.");
                }
            } else {
                System.out.println("Not enough device charge to jump forward.");
            }
        } else {
            System.out.println("You're already at the last building. You can't jump forward.");
        }
    }

    public void jumpBackward() {
        if (this.currentBuildingIndex > 1) { // Check if there are at least two buildings behind
            Building currentBuilding = buildings.get(currentBuildingIndex);
            Building previousBuilding = buildings.get(currentBuildingIndex - 2); // Get the building two steps back
            int heightDifference = Math.abs(currentBuilding.getHeight() - previousBuilding.getHeight()) + 1;

            if (fuelCharge >= heightDifference) {
                this.fuelCharge -= heightDifference; // Deduct the height difference from the device charge
                this.currentBuildingIndex -= 2; // Jump two buildings back
                this.currentBuilding = buildings.get(currentBuildingIndex);
                System.out.println("Jumped backward. Device charge depleted by " + heightDifference + " points.");

                // Check if the previous building is web trapped
                if (previousBuilding.isFrozen()) {
                    System.out.println("You are frozen, skip a turn.");
                    return; // Prevents moving to the frozen building
                }

                // Check if the previous building is web trapped
                if (previousBuilding.isWebTrapped()) {
                    heightDifference += 5;
                }

                // Check if the new building has a fuel cell and if the player actually landed on this building
                if (this.currentBuilding.hasFuelCell() && (this.currentBuildingIndex == 0 || this.currentBuildingIndex == 1)) {
                    this.fuelCharge += 5; // Increase device charge by 5
                    this.currentBuilding.setFuelCell(false); // Remove the fuel cell from the building
                    System.out.println("You found a fuel cell! Device charge increased by 5 points.");
                }
            } else {
                System.out.println("Not enough device charge to jump backward.");
            }
        } else {
            System.out.println("You're already at the first building. You can't jump backward.");
        }
    }



    public void skipTurn() {
        System.out.println("You've skipped a turn.");
        // The jumper does not consume any charge when skipping a turn.
    }

    private void updateFuelCharge() {
        this.fuelCharge = Math.min(MAX_CHARGE, this.fuelCharge + 1);
    }

    public void performAction(Action action) {
        String actionDescription = action.getDescription();
        if (actionDescription.equals("Jump forward")) {
            jumpForward();
        } else if (actionDescription.equals("Jump backward")) {
            jumpBackward();
        } else if (actionDescription.equals("Skip a turn")) {
            skipTurn();
        }
        // updateFuelCharge(); // Update device charge after performing the action
    }

    // toString method
    @Override
    public String toString() {
        return "Player[name=" + playerName + ", charge=" + fuelCharge + ", fuelCellsFound=" + fuelCellsFound + "]";
    }
}
