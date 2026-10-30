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

public class mazetestSample {

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

        printMap(currentMap);
    }


    // Print Map
    public static void printMap(Map currentMap) {
        System.out.print("\u001B[?7l"); // disable auto-wrap
        try {
            clearScreen();
            // Keep all your existing map-printing code here, unchanged.
        } finally {
            System.out.print("\u001B[?7h"); // restore auto-wrap
            System.out.flush();
        }
        
        clearScreen();
        System.out.println("Current Map:\n");

        int cellWidth = 9;
        System.out.printf("%-" + cellWidth + "s", "");
        for (int col = 0; col < currentMap.getWidth(); col++) {
            String columnNumber = Integer.toString(col + 1);
            int leftPadding = (cellWidth - columnNumber.length()) / 2;
            int rightPadding = cellWidth - columnNumber.length() - leftPadding;
            System.out.print(" ".repeat(leftPadding) + columnNumber + " ".repeat(rightPadding));
        }
        System.out.println();
        System.out.println();
        System.out.println();

        for (int row = 0; row < currentMap.getHeight(); row++) {
            boolean horizontalTunnelRow = false;
            for (int col = 0; col < currentMap.getWidth(); col++) {
                boolean open = !currentMap.getFullMap()[row][col].getTileId().equals("#");
                boolean openToLeft = col > 0
                        && !currentMap.getFullMap()[row][col - 1].getTileId().equals("#");
                boolean openToRight = col + 1 < currentMap.getWidth()
                        && !currentMap.getFullMap()[row][col + 1].getTileId().equals("#");
                if (open && (openToLeft || openToRight)) {
                    horizontalTunnelRow = true;
                    break;
                }
            }

            int displayLines = horizontalTunnelRow ? 5 : 3;
            int centerLine = displayLines / 2;
            for (int displayRow = 0; displayRow < displayLines; displayRow++) {
                if (displayRow == centerLine) {
                    System.out.printf("%-" + cellWidth + "d", row + 1);
                } else {
                    System.out.printf("%-" + cellWidth + "s", "");
                }

                for (int col = 0; col < currentMap.getWidth(); col++) {
                    String tileId = currentMap.getFullMap()[row][col].getTileId();
                    boolean wall = tileId.equals("#");
                    boolean openToLeft = col > 0
                            && !currentMap.getFullMap()[row][col - 1].getTileId().equals("#");
                    boolean openToRight = col + 1 < currentMap.getWidth()
                            && !currentMap.getFullMap()[row][col + 1].getTileId().equals("#");
                    boolean horizontalTunnelCell = !wall && (openToLeft || openToRight);
                    boolean topBlocked = row == 0 || currentMap.getFullMap()[row - 1][col].getTileId().equals("#");
                    boolean bottomBlocked = row == currentMap.getHeight() - 1
                            || currentMap.getFullMap()[row + 1][col].getTileId().equals("#");

                    if (wall) {
                        System.out.print("#########");
                    } else if (displayRow == centerLine) {
                        String displayTile = tileId.equals("_") ? "-" : tileId;
                        int leftPadding = (cellWidth - displayTile.length()) / 2;
                        int rightPadding = cellWidth - displayTile.length() - leftPadding;
                        System.out.print(" ".repeat(leftPadding) + displayTile + " ".repeat(rightPadding));
                    } else if (horizontalTunnelCell && displayRow == 0 && topBlocked) {
                        System.out.print("#########");
                    } else if (horizontalTunnelCell
                            && displayRow == displayLines - 1 && bottomBlocked) {
                        // Borders follow each horizontal run and stop at vertical openings.
                        System.out.print("#########");
                    } else {
                        System.out.print(" ".repeat(cellWidth));
                    }
                }
                System.out.println();
            }
        }
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
