public class Map {
    private String mapName;
    private int height;
    private int width;
    private Tile[][] fullMap;


    public Map(String mapName, int height, int width, Tile[][] fullMap) {
        this.mapName = mapName;
        this.height = height;
        this.width = width;
        this.fullMap = fullMap;
    }


 // GETTERS
    // Get Map Name
    public String getMapName() {
        return mapName;
    }

    // Get Height
    public int getHeight() {
        return height;
    }

    // Get Width
    public int getWidth() {
        return width;
    }

    // Get Full Map
    public Tile[][] getFullMap() {
        return fullMap;
    }



 // SETTERS
    // Set Map Name
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    // Set Height
    public void setHeight(int height) {
        this.height = height;
    }

    // Set Width
    public void setWidth(int width) {
        this.width = width;
    }

    // Set Full Map
    public void setFullMap(Tile[][] fullMap) {
        this.fullMap = fullMap;
    }
}
