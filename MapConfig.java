public class MapConfig {
    private String genMode;
    private int height;
    private int width;
    private int numFloors;
    private int percentWalkable;
    private int numHazards;
    private int numMonsters;
    private int numTreasures;


    public MapConfig(String genMode, int height, int width, int numFloors, int percentWalkable, int numHazards, int numMonsters, int numTreasures) {
        this.genMode = genMode;
        this.height = height;
        this.width = width;
        this.numFloors = numFloors;
        this.percentWalkable = percentWalkable;
        this.numHazards = numHazards;
        this.numMonsters = numMonsters;
        this.numTreasures = numTreasures;
    }


 // GETTERS
    // Get Gen Mode
    public String getGenMode() {
        return genMode;
    }
    
    // Get Height
    public int getHeight() {
        return height;
    }

    // Get Width
    public int getWidth() {
        return width;
    }

    // Get Num Floors
    public int getNumFloors() {
        return numFloors;
    }

    // Get Percent Walkable
    public int getPercentWalkable() {
        return percentWalkable;
    }

    // Get Num Hazards
    public int getNumHazards() {
        return numHazards;
    }

    // Get Num Monsters
    public int getNumMonsters() {
        return numMonsters;
    }

    // Get Num Treasures
    public int getNumTreasures() {
        return numTreasures;
    }



 // SETTERS
    // Set Gen Mode
    public void setGenMode(String genMode) {
        this.genMode = genMode;
    }
    
    // Set Height
    public void setHeight(int height) {
        this.height = height;
    }

    // Set Width
    public void setWidth(int width) {
        this.width = width;
    }

    // Set Num Floors
    public void setNumFloors(int numFloors) {
        this.numFloors = numFloors;
    }

    // Set Percent Walkable
    public void setPercentWalkable(int percentWalkable) {
        this.percentWalkable = percentWalkable;
    }

    // Set Num Hazards
    public void setNumHazards(int numHazards) {
        this.numHazards = numHazards;
    }

    // Set Num Monsters
    public void setNumMonsters(int numMonsters) {
        this.numMonsters = numMonsters;
    }

    // Set Num Treasures
    public void setNumTreasures(int numTreasures) {
        this.numTreasures = numTreasures;
    }
}
