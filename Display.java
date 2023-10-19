import java.util.List;

public class Display {
    public void showBuildings(List<Building> buildings) {
        for (int height = 5; height > 0; height--) {
            for (Building building : buildings) {
                if (building.getHeight() >= height) {
                    System.out.print("~ ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public void showPlayer(Player player, List<Building> buildings) {
        // Top line with symbols
        for (Building building : buildings) {
            if (buildings.indexOf(building) == player.getCurrentBuildingIndex()) {
                System.out.print("P ");
            } else if (building.hasFuelCell()) {
                System.out.print("F ");
            } else if (building.isWebTrapped()) {
                System.out.print("W ");
            } else if (building.hasExitPortal()) {
                System.out.print("X ");
            } else if (building.isFrozen()) {
                System.out.print("! ");
            } else {
                System.out.print("  ");  // Empty space for buildings with no symbols
            }
        }
        System.out.println();  // Newline at the end of the top line

        // Lines below with '~' for building heights
        int maxHeight = buildings.stream().map(Building::getHeight).max(Integer::compare).orElse(0);
        for (int i = maxHeight; i > 0; i--) {
            for (Building building : buildings) {
                if (building.getHeight() >= i) {
                    System.out.print("~ ");
                } else {
                    System.out.print("  ");  // Empty space for lower buildings
                }
            }
            System.out.println();  // Newline at the end of each line
        }
    }





    public void showPlayerStatus(Player player) {
        System.out.println("Player Status:");
        System.out.println("Name: " + player.getPlayerName());
        System.out.println("Device Fuel: " + player.getfuelCharge());
        System.out.println("Fuel Cells Found: " + player.getFuelCellsFound());
        if (player.getCurrentBuilding() != null) {
            System.out.println("Current Building: " + player.getCurrentBuilding().toString());
        }
    }
}
