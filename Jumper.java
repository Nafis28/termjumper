import java.io.*;
import java.util.*;

public class Jumper{
    private Game game;

    // Default constructor
    public Jumper() {
        this.game = new Game();
    }

    // Non-default constructor
    public Jumper(Game game) {
        this.game = game;
    }

    // Accessor for Game
    public Game getGame() {
        return game;
    }

    // Mutator for Game
    public void setGame(Game game) {
        this.game = game;
    }

    // FileHandler's loadBuildingFile method
    public List<Building> loadBuildingFile(String filePath) throws IOException {
        List<Building> buildings = new ArrayList<>();
        Scanner scanner = new Scanner(new FileReader(filePath));
        int lineNumber = 1; // Keep track of line number for error messages
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length != 5) { // There should be exactly 5 parts
                System.err.println("Invalid format on line " + lineNumber + ": Expected 5 values but found " + parts.length);
                lineNumber++;
                continue;
            }
            try {
                int height = Integer.parseInt(parts[0]);
                boolean theExitPortal = Boolean.parseBoolean(parts[1]);
                boolean theFuelCell = Boolean.parseBoolean(parts[2]);
                boolean itsWebTrapped = Boolean.parseBoolean(parts[3]);
                boolean frozenLand = Boolean.parseBoolean(parts[4]);
                buildings.add(new Building(height, theExitPortal, theFuelCell, itsWebTrapped, frozenLand));
            } catch (NumberFormatException e) {
                System.err.println("Invalid format on line " + lineNumber + ": " + e.getMessage());
            }
            lineNumber++;
        }
        scanner.close();
        return buildings;
    }

    //Validation
    public static boolean stringIsBlank(String value) {
        boolean blank = false;
        if (value.trim().length() == 0)
            blank = true;
        return blank;
    }

    public static boolean stringLengthWithinRange(String value, int min, int max) {
        boolean withinRange = false;
        if ((value.trim().length() >= min) && (value.trim().length() <= max))
            withinRange = true;
        return withinRange;
    }

    // toString method
    @Override
    public String toString() {
        return "Jumper()[game=" + game.toString() + "]";
    }

    public static void main(String[] args) {
        System.out.println(" Welcome to the Java Jumper!");

        Scanner scanner = new Scanner(System.in);
        // Read player name and validate its length
        String playerName;
        do {
            System.out.print("Enter your name (between 3 and 12 characters): ");
            playerName = scanner.nextLine().trim();
        } while (Jumper.stringIsBlank(playerName) ||
                !Jumper.stringLengthWithinRange(playerName, 3, 12));

        // Read buildings from buildings.txt file
        Jumper handler = new Jumper();
        List<Building> buildings = null;
        try {
            buildings = handler.loadBuildingFile("C:\\Users\\n_a_f\\Desktop\\University\\ITO4131 - Java Programming\\Assignment\\JumperGame\\src\\buildings.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the buildings file.");
            e.printStackTrace();
            return;
        }

        Player player = new Player(playerName, buildings); // Pass the buildings list

        // Create the fuel cells and actions (not implemented)
        List<FuelCell> fuelCells = new ArrayList<>(); // Initialize with empty list
        List<Action> actions = new ArrayList<>(); // Initialize with empty list

        // Create the game
        Game game = new Game(player, buildings, fuelCells, actions);

        // Start the game
        game.start();

        scanner.close();
    }
}
