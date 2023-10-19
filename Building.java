
import java.util.Random;

public class Building {
    private int height;
    private boolean theExitPortal;
    private boolean theFuelCell;
    private boolean itsWebTrapped;
    private boolean frozenLand;

    // Default constructor
    public Building() {
        this.height = 0;
        this.theExitPortal = false;
        this.theFuelCell = false;
        this.itsWebTrapped = false;
        this.frozenLand = false;
    }

    // Non-default constructor
    public Building(int height, boolean theExitPortal, boolean theFuelCell, boolean itsWebTrapped, boolean frozenLand) {
        this.height = height;
        this.theExitPortal = theExitPortal;
        this.theFuelCell = theFuelCell;
        this.itsWebTrapped = itsWebTrapped;
        this.frozenLand = frozenLand;
    }

    // Accessors
    public int getHeight() {
        return height;
    }

    public boolean hasExitPortal() {
        return theExitPortal;
    }

    public boolean hasFuelCell() {
        return theFuelCell;
    }

    public boolean isWebTrapped() {
        return itsWebTrapped;
    }

    public boolean isFrozen() {
        return frozenLand;
    }

    // Mutators
    public void setHeight(int height) {
        this.height = height;
    }

    public void setExitPortal(boolean theExitPortal) {
        this.theExitPortal = theExitPortal;
    }

    public void setFuelCell(boolean theFuelCell) {
        this.theFuelCell = theFuelCell;
    }

    public void setWebTrapped(boolean itsWebTrapped) {
        this.itsWebTrapped = itsWebTrapped;
    }

    public void setFrozen(boolean frozenLand) {
        this.frozenLand = frozenLand;
    }

    public void randomizeHeight() {
        Random random = new Random();
        this.height = random.nextInt(5) + 1;
    }

    // toString method
    @Override
    public String toString() {
        String symbol = "~";
        if (theExitPortal) symbol = "X";
        if (theFuelCell) symbol = "F";
        if (itsWebTrapped) symbol = "W";
        if (frozenLand) symbol = "!";

        return symbol;
    }
}
