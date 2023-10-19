import java.util.function.Consumer;

public class Action {
    private String description;
    private int cost;

    // Default constructor
    public Action() {
        this.description = "";
        this.cost = 0;
    }

    // Non-default constructor
    public Action(String description, int cost) {
        this.description = description;
        this.cost = cost;
    }

    // Accessor
    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    // Mutator
    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    // Perform method
    public void perform(Player player) {
        // Deduct the cost from the device charge
        player.setfuelCharge(Math.max(0, player.getfuelCharge() - this.cost));
    }

    // toString method
    @Override
    public String toString() {
        return "Action[description=" + description + "]";
    }
}
