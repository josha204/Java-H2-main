public class Monster {
    private String name;
    private String searchMode;
    private int[][] location;
    private int[][] targetLocation;
    private boolean isStunned;

    public Monster(String name, String searchMode, int[][] location, int[][] targetLocation, boolean isStunned) {
        this.name = name;
        this.searchMode = searchMode;
        this.location = location;
        this.targetLocation = targetLocation;
        this.isStunned = isStunned;
    }


 // GETTERS
    // Get Name
    public String getName() {
        return name;
    }

    // Get Search Mode
    public String getSearchMode() {
        return searchMode;
    }

    // Get Location
    public int[][] getLocation() {
        return location;
    }

    // Get Target Location
    public int[][] getTargetLocation() {
        return targetLocation;
    }

    // Get Is Stunned
    public boolean getIsStunned() {
        return isStunned;
    }


 // SETTERS
    // Set Name
    public void setName(String name) {
        this.name = name;
    }

    // Set Search Mode
    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    // Set Location
    public void setLocation(int[][] location) {
        this.location = location;
    }

    // Set Target Location
    public void setTargetLocation(int[][] targetLocation) {
        this.targetLocation = targetLocation;
    }

    // Set Is Stunned
    public void setIsStunned(boolean isStunned) {
        this.isStunned = isStunned;
    }
}
