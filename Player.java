import java.util.*;

public class Player {
    private String name;
    private int health;
    private int[][] position;
    private String[] inventory;
    private ArrayList<Game> savedGames = new ArrayList<>();
    private ArrayList<Map> savedMaps = new ArrayList<>();
    private int treasuresCollected;


    public Player(String name, int health, int[][] position, String[] inventory, ArrayList<Game> savedGames, ArrayList<Map> savedMaps, int treasuresCollected) {
        this.name = name;
        this.health = health;
        this.position = position;
        this.inventory = inventory;
        this.savedGames = savedGames;
        this.savedMaps = savedMaps;
        this.treasuresCollected = treasuresCollected;
    }


 // GETTERS
    // Get Name
    public String getName() {
        return name;
    }

    // Get Player Health
    public int getHealth() {
        return health;
    }

    // Get Position
    public int[][] getPosition() {
        return position;
    }

    // Get Inventory
    public String[] getInventory() {
        return inventory;
    }

    // Get Saved Games
    public ArrayList<Game> getSavedGames() {
        return savedGames;
    }

    // Get Saved Maps
    public ArrayList<Map> getSavedMaps() {
        return savedMaps;
    }

    // Get Treasures Collected
    public int treasuresCollected() {
        return treasuresCollected;
    }



 // SETTERS
    // Set Name
    public void setName(String name) {
        this.name = name;
    }

    // Set Health
    public void setHealth(int health) {
        this.health = health;
    }

    // Set Position
    public void setPosition(int[][] position) {
        this.position = position;
    }

    // Set Inventory
    public void setInventory(String[] inventory) {
        this.inventory = inventory;
    }

    // Set Saved Games
    public void setSavedGames(ArrayList<Game> savedGames) {
        this.savedGames = savedGames;
    }

    // Set Saved Maps
    public void setSavedMaps(ArrayList<Map> savedMaps) {
        this.savedMaps = savedMaps;
    }

    // Set Treasures Collected
    public void setTreasuresCollected(int treasuresCollected) {
        this.treasuresCollected = treasuresCollected;
    }
}
