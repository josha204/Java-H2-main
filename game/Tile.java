package game;

public class Tile {
    private String tileId;
    private boolean visited;
    private double value;

    public Tile(String tileId, boolean visited, double value) {
        this.tileId = tileId;
        this.visited = visited;
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
    
    // Set Value
    public void setValue(double value) {
        this.value = value;
    }
}