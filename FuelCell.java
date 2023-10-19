public class FuelCell {
    private Building location;
    private int chargeCount;
    private int turnsSinceLastRecharge;  // New field

    // Modify the constructors to initialize turnsSinceLastRecharge to 0
    public FuelCell() {
        this.location = null;
        this.chargeCount = 0;
        this.turnsSinceLastRecharge = 0;  // Initialize to 0
    }

    public FuelCell(Building location) {
        this.location = location;
        this.chargeCount = 5;
        this.turnsSinceLastRecharge = 0;  // Initialize to 0
    }

    // Add a method to increment turnsSinceLastRecharge
    public void incrementTurnsSinceLastRecharge() {
        this.turnsSinceLastRecharge++;
    }

    // Add a method to check if the fuel cell is drained
    public boolean isDrained() {
        return this.turnsSinceLastRecharge >= 3;
    }

    public Building getLocation() {
        return location;
    }

    public int getChargeCount() {
        return chargeCount;
    }

    public void setLocation(Building location) {
        this.location = location;
    }

    public void setChargeCount(int chargeCount) {
        this.chargeCount = chargeCount;
    }

    public void recharge(Player player) {
        player.setfuelCharge(player.getfuelCharge() + chargeCount);
    }

    @Override
    public String toString() {
        return "FuelCell[location=" + location.toString() + ", chargeCount=" + chargeCount + "]";
    }
}