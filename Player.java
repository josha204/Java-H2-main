public class Player {
    private String name;
    private int health;
    private int[][] position;
    private String[] inventory;
    private int treasuresCollected;


    public Player(String name, int health, int[][] position, String[] inventory, int treasuresCollected) {
        this.name = name;
        this.health = health;
        this.position = position;
        this.inventory = inventory;
        this.treasuresCollected = treasuresCollected;
    }


 // GETTERS
    // Get Name
    public void getName() {
        return name;
    }

    // Get Player Health
    public void getHealth() {
        return health;
    }

    // Get Position
    public void getPosition() {
        return position;
    }

    // Get Inventory
    public void getInventory() {
        return inventory;
    }

    // Get Treasures Collected
    public void treasuresCollected() {
        return treasuresCollected;
    }



 // SETTERS
    // Set Name
    
}
