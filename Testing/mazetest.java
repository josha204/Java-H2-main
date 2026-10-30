// josha204

// NOTE
// This testing program is for me to develop a higher degree of mapmaking skill that takes the mapmaking technique developed in PlayerWindow.java and applies it.
// The specific goal of these applications is to make the map-generation remain random, but randomly develops walkable space into tunnels or hallways with intersections.


// PLAN
// At initialization, the map will be filled with 'X' characters, all of which will be replaced by either characters: '#' (closed spaces) or '_' (open spaces), and special symbols.
// Then, the generation should randomly choose one tile 'Entrance' on edge 'A' of the map, and one tile 'Exit' on edge 'B', 'C', or 'D'. 
// If 'Entrance' is on the corner tile of edges 'A' and 'B', then 'Exit' can still appear on edges 'A' or 'B', but must appear on the further half of that edge from the 'Entrance'.
// No matter how the open spaces are arranged into tunnels, there must be a walkable path from 'Entrance' to 'Exit'. 
package Testing;

import game.Map;
import game.Tile;
import java.util.*;

public class mazetest {
    
    // Global Scanner
    public static final Scanner stdin = new Scanner(System.in);

    // Global Random
    public static final Random rndObj = new Random();

    

    public static void main(String[] args) {
        int width = 0;
        int height = 0;
        String temp = " ";

        while (true) {
            
            // Take Valid Map Width
            while (true) {
                System.out.print("Enter the map width: ");
                width = stdin.nextInt();

                if (width > 10 && width % 2 == 1) {
                    break;
                } else {
                    clearScreen();
                    System.out.println("Please enter an odd integer greater than 10.\n\n");
                }
            }


            // Take Valid Map Height
            while (true) {
                System.out.print("Enter the map height: ");
                height = stdin.nextInt();

                if (height > 10 && height % 2 == 1) {
                    break;
                } else {
                    clearScreen();
                    System.out.println("Please enter an odd integer greater than 10.\n\n");
                }
            }
            
            clearScreen();
            System.out.println("\n=== MAP DIMENSIONS ===");
            System.out.println("Current width: " + width);
            System.out.println("Current height: " + height);


            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1) Start Simulation \n2) Reconfigure Map \n3) Quit");
            System.out.print("\nMake a choice: ");
            int choice = stdin.nextInt();
            clearScreen();

            switch (choice) {
                case 1 -> generateMap(height, width);
                //case 2 -> reconfig();
                case 3 -> quit();
            }
        }
    }



    // Generate Map
    public static void generateMap(int rows, int cols) {
        Tile[][] fullMap = new Tile[rows][cols];
        Map currentMap = new Map("currentMap", rows, cols, fullMap, false);


        // Initialize map to closed spaces '#'
        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                currentMap.getFullMap()[i][k] = new Tile("#", false, -1);
            }
        }


        // Choose an 'Entrance' (E) tile
        // Sides: 1=Left, 2=Right, 3=Top, 4=Bottom
        int eSide = 0;
        int eRow = 0;
        int eCol = 0;
        while (true) {
            eSide = rndObj.nextInt(4) + 1;
            switch (eSide) {
                // Left
                case 1 -> {
                    eRow = rndObj.nextInt(rows);
                    eCol = 0;
                }
                // Right
                case 2 -> {
                    eRow = rndObj.nextInt(rows);
                    eCol = cols - 1;
                }
                // Top
                case 3 -> {
                    eRow = 0;
                    eCol = rndObj.nextInt(cols);
                }
                // Bottom
                case 4 -> {
                    eRow = rows - 1;
                    eCol = rndObj.nextInt(cols);
                }
            }
            currentMap.getFullMap()[eRow][eCol].setTileId("E");
            currentMap.getFullMap()[eRow][eCol].setVisited(true);
            currentMap.getFullMap()[eRow][eCol].setValue(1.0);
            break;
        }


        // Initialize helpers to handle grid generation
        int currentRow = rndObj.nextInt(rows);
        int currentCol = rndObj.nextInt(cols);
        Stack<Tile> stack = new Stack<Tile>();
        int[][] directions = {{-2, 0}, {2, 0}, {0, -2}, {0, 2}};
        



        // Generate the grid with the Depth-First Search (DFS) algorithm
        boolean anyUnvisitedNeighbors = false;
        while (true) {
            // Set current tile to visited

            if (currentMap.getFullMap()[currentRow][currentCol])
        }


        // Choose an 'Exit' (X) tile
        // Sides: 1=Left, 2=Right, 3=Top, 4=Bottom
        int xSide = 0;
        int xRow = 0;
        int xCol = 0;
        while (true) {
            while (true) {
                xSide = rndObj.nextInt(4) + 1;
                if (xSide != eSide) {
                    break;
                }
            }

            while (true) {
                switch (xSide) {
                    // Left
                    case 1 -> {
                        xRow = rndObj.nextInt(rows);
                        xCol = 0;
                    }
                    // Right
                    case 2 -> {
                        xRow = rndObj.nextInt(rows);
                        xCol = cols - 1;
                    }
                    // Top
                    case 3 -> {
                        xRow = 0;
                        xCol = rndObj.nextInt(cols);
                    }
                    // Bottom
                    case 4 -> {
                        xRow = rows - 1;
                        xCol = rndObj.nextInt(cols);
                    }
                }

                if (!(xRow == eRow && xCol == eCol) && currentMap.getFullMap()[xRow][xCol].getTileId().equals("_")) {
                    currentMap.getFullMap()[xRow][xCol].setTileId("X");
                    currentMap.getFullMap()[xRow][xCol].setVisited(true);
                    currentMap.getFullMap()[xRow][xCol].setValue(1.0);
                    break;
                }
            }
            break;
        }


        //


        printMap(currentMap);
    }



    // Print Map
    public static void printMap(Map currentMap) {
        clearScreen();
        System.out.println("Current Map:\n\n\n");


        // Print Header
        for (int i = 0; i < currentMap.getWidth(); i++) {
            System.out.print("\t" + (i + 1));
        }


        // Print Remaining Body
        System.out.println("\n\n");
        for (int i = 0; i < currentMap.getHeight(); i++) {
            System.out.print((i + 1) + "\t ");
            for (int k = 0; k < currentMap.getWidth(); k++) {
                System.out.print(currentMap.getFullMap()[i][k].getTileId() + "\t ");
            }
            System.out.println("\n\n");
        }
        quit();
    }



    // Quit
    public static void quit() {
        System.exit(0);
    }



    // Clear screen
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
