package game;

public class Tile {
    private String tileId;
    private boolean visited;
    private boolean discovered;
    private boolean viewed;
    private double value;

    public Tile(String tileId, boolean visited, boolean discovered, boolean viewed, double value) {
        this.tileId = tileId;
        this.visited = visited;
        this.discovered = discovered;
        this.viewed = viewed;
        this.value = value;
    }

 // GETTERS
    // Get Tile Id
    public String getTileId() {
        return tileId;
    }

    // Get Visited
    public boolean getVisited() {
        return visited;
    }

    // Get Discovered
    public boolean getDiscovered() {
        return discovered;
    }

    // Get Viewed
    public boolean getViewed() {
        return viewed;
    }

    // Get Value
    public double getValue() {
        return value;
    }

 // SETTERS
    // Set Tile Id
    public void setTileId(String tileId) {
        this.tileId = tileId;
    }

    // Set Visited
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    // Set Discovered
    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    // Set Viewed
    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
    
    // Set Value
    public void setValue(double value) {
        this.value = value;
    }
}