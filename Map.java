import java.util.*;

public class Map {
    private String name;
    private int height;
    private int width;
    private Tile[][] fullMap;
    private boolean isSaved;


    public Map(String name, int height, int width, Tile[][] fullMap, boolean isSaved) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.fullMap = fullMap;
        this.isSaved = isSaved;
    }


 // GETTERS
    // Get Map Name
    public String getName() {
        return name;
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

    // Get Is Saved
    public boolean getIsSaved() {
        return isSaved;
    }



 // SETTERS
    // Set Map Name
    public void setName(String name) {
        this.name = name;
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

    // Set Is Saved
    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }


 // OTHER METHODS
    // Has Same Layout
    public boolean hasSameLayout(Map other) {
        if (other == null || height != other.height || width != other.width) {
            return false;
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Tile thisTile = fullMap[row][col];
                Tile otherTile = other.fullMap[row][col];

                if (thisTile == null || otherTile == null) {
                    if (thisTile != otherTile) {
                        return false;
                    }
                } else if (!Objects.equals(thisTile.getTileId(), otherTile.getTileId())) {
                    return false;
                }
            }
        }
        return true;
    }
}
