package game;

import java.util.*;

public class MapConfig {
    private String genMode;
    private int height;
    private int width;
    private int numFloors;
    private int percentWalkable;
    private int numHazards;
    private int numMonsters;
    private int numTreasures;
    public static final Random rndObj = new Random();

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


    // Generate Map
    public static Map generateMap(MapConfig mapConfig) {
        
        // Extract relevant information from mapConfig
        String genMode = mapConfig.getGenMode();
        int rows = mapConfig.getHeight();
        int cols = mapConfig.getWidth();
        int numFloors = mapConfig.getNumFloors();
        int percentWalkable = mapConfig.getPercentWalkable();
        int numHazards = mapConfig.getNumHazards();
        int numMonsters = mapConfig.getNumMonsters();
        int numTreasures = mapConfig.getNumTreasures();

        Tile[][] fullMap = new Tile[rows][cols];
        Map currentMap = new Map("currentMap", rows, cols, fullMap, false);


        // Initialize map to closed spaces '#'
        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                currentMap.getFullMap()[i][k] = new Tile("#", false, false, false, -1);
            }
        }


        // Choose an 'Entrance' (E) tile
        // Sides: 1=Left, 2=Right, 3=Top, 4=Bottom
        int eSide = rndObj.nextInt(4) + 1;
        int eRow = 0;
        int eCol = 0;
        switch (eSide) {
            // Left
            case 1 -> {
                eRow = rndObj.nextInt((rows - 1) / 2) * 2 + 1;
                eCol = 0;
            }
            // Right
            case 2 -> {
                eRow = rndObj.nextInt((rows - 1) / 2) * 2 + 1;
                eCol = cols - 1;
            }
            // Top
            case 3 -> {
                eRow = 0;
                eCol = rndObj.nextInt((cols - 1) / 2) * 2 + 1;
            }
            // Bottom
            case 4 -> {
                eRow = rows - 1;
                eCol = rndObj.nextInt((cols - 1) / 2) * 2 + 1;
            }
        }

        currentMap.getFullMap()[eRow][eCol].setTileId("E");
        currentMap.getFullMap()[eRow][eCol].setVisited(true);
        currentMap.getFullMap()[eRow][eCol].setValue(1.0);


        // Start DFS in the interior cell immediately beside the entrance.
        int currentRow = eRow;
        int currentCol = eCol;
        switch (eSide) {
            case 1 -> currentCol = 1;
            case 2 -> currentCol = cols - 2;
            case 3 -> currentRow = 1;
            case 4 -> currentRow = rows - 2;
        }

        currentMap.getFullMap()[currentRow][currentCol].setTileId("_");
        currentMap.getFullMap()[currentRow][currentCol].setVisited(true);
        currentMap.getFullMap()[currentRow][currentCol].setValue(1.0);

        // The stack stores row/column pairs so DFS can backtrack through grid positions.
        Stack<int[]> stack = new Stack<int[]>();
        stack.push(new int[] {currentRow, currentCol});
        int[][] directions = {{-2, 0}, {2, 0}, {0, -2}, {0, 2}};


        // Generate the grid with randomized depth-first search.
        // Moving two cells at a time leaves a wall between maze cells to carve into a corridor.
        while (!stack.isEmpty()) {
            int[] currentPosition = stack.peek();
            currentRow = currentPosition[0];
            currentCol = currentPosition[1];

            ArrayList<int[]> unvisitedNeighbors = new ArrayList<int[]>();
            for (int i = 0; i < directions.length; i++) {
                int nextRow = currentRow + directions[i][0];
                int nextCol = currentCol + directions[i][1];

                if (nextRow > 0 && nextRow < rows - 1
                        && nextCol > 0 && nextCol < cols - 1
                        && !currentMap.getFullMap()[nextRow][nextCol].getVisited()) {
                    unvisitedNeighbors.add(new int[] {nextRow, nextCol});
                }
            }

            if (unvisitedNeighbors.isEmpty()) {
                // No unvisited neighbor remains, so backtrack to the previous maze cell.
                stack.pop();
            } else {
                // Randomly select an unvisited neighbor to keep the maze layout varied.
                int[] nextPosition = unvisitedNeighbors.get(rndObj.nextInt(unvisitedNeighbors.size()));
                int nextRow = nextPosition[0];
                int nextCol = nextPosition[1];
                int wallRow = currentRow + (nextRow - currentRow) / 2;
                int wallCol = currentCol + (nextCol - currentCol) / 2;

                currentMap.getFullMap()[wallRow][wallCol].setTileId("_");
                currentMap.getFullMap()[wallRow][wallCol].setVisited(true);
                currentMap.getFullMap()[wallRow][wallCol].setValue(1.0);

                currentMap.getFullMap()[nextRow][nextCol].setTileId("_");
                currentMap.getFullMap()[nextRow][nextCol].setVisited(true);
                currentMap.getFullMap()[nextRow][nextCol].setValue(1.0);
                stack.push(nextPosition);
            }
        }


        // Choose an 'Exit' (X) tile on a different side.
        // Its boundary position lines up with a visited maze cell, so it is reachable.
        int xSide = rndObj.nextInt(3) + 1;
        if (xSide >= eSide) {
            xSide++;
        }

        int xRow = 0;
        int xCol = 0;
        switch (xSide) {
            // Left
            case 1 -> {
                xRow = rndObj.nextInt((rows - 1) / 2) * 2 + 1;
                xCol = 0;
            }
            // Right
            case 2 -> {
                xRow = rndObj.nextInt((rows - 1) / 2) * 2 + 1;
                xCol = cols - 1;
            }
            // Top
            case 3 -> {
                xRow = 0;
                xCol = rndObj.nextInt((cols - 1) / 2) * 2 + 1;
            }
            // Bottom
            case 4 -> {
                xRow = rows - 1;
                xCol = rndObj.nextInt((cols - 1) / 2) * 2 + 1;
            }
        }

        currentMap.getFullMap()[xRow][xCol].setTileId("X");
        currentMap.getFullMap()[xRow][xCol].setVisited(true);
        currentMap.getFullMap()[xRow][xCol].setValue(1.0);

        return currentMap;
    }
}
