public class Tile {
    private String tileId;
    private double value;

    public Tile(String tileId, double value) {
        this.tileId = tileId;
        this.value = value;
    }

 // GETTERS
    // Get Tile Id
    public String getTileId() {
        return tileId;
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

    public void setValue(double value) {
        this.value = value;
    }
}