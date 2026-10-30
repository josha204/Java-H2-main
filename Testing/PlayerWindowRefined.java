package Testing;
import game.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PlayerWindowRefined {

    private static final Scanner stdin = new Scanner(System.in);
    private static final Random random = new Random();
    private static final int MIN_DIMENSION = 11;

    public static void main(String[] args) {
        int rows = readDimension("height");
        if (rows == -1) {
            return;
        }

        int cols = readDimension("width");
        if (cols == -1) {
            return;
        }

        while (true) {
            H2.clearScreen();
            System.out.println("=== MAP DIMENSIONS ===");
            System.out.println("Current width: " + cols);
            System.out.println("Current height: " + rows);
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1) Start Simulation\n2) Reconfigure Map\n3) Quit");

            String choice = readLine("\nMake a choice: ");
            if (choice == null) {
                return;
            }

            switch (choice.trim()) {
                case "1":
                    if (!begin(rows, cols)) {
                        return;
                    }
                    break;
                case "2":
                    rows = readDimension("height");
                    if (rows == -1) {
                        return;
                    }
                    cols = readDimension("width");
                    if (cols == -1) {
                        return;
                    }
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Please enter 1, 2, or 3.");
            }
        }
    }


    // Goal: keep dimensions valid even when this class is called outside its menu.
    // How: reject non-positive sizes and guard the area calculation before allocating arrays.
    public static boolean begin(int rows, int cols) {
        if (rows < MIN_DIMENSION || cols < MIN_DIMENSION || rows % 2 == 0 || cols % 2 == 0) {
            System.out.println("Map dimensions must be odd integers greater than 10.");
            return true;
        }

        long tileCount = (long) rows * cols;
        if (tileCount > Integer.MAX_VALUE) {
            System.out.println("The requested map is too large to generate.");
            return true;
        }

        H2.clearScreen();
        int targetOpenTiles = readOpenTileCount(tileCount);
        if (targetOpenTiles == -1) {
            return false;
        }

        // Goal: keep terrain independent of occupants so moving entities cannot
        // overwrite each other or erase the underlying walkability information.
        boolean[][] walkable = generateConnectedTerrain(rows, cols, targetOpenTiles);

        // Goal: guarantee that both entities can be placed without retry loops.
        // The percentage prompt rejects targets below two, so this list has enough cells.
        List<int[]> openTiles = collectOpenTiles(walkable);
        int[] playerPosition = openTiles.remove(random.nextInt(openTiles.size()));
        int[] computerPosition = openTiles.get(random.nextInt(openTiles.size()));

        return runMovement(walkable, playerPosition, computerPosition);
    }

    // Goal: prevent invalid numeric text and out-of-range values from crashing or
    // silently restarting the percentage prompt.
    private static int readOpenTileCount(long tileCount) {
        while (true) {
            String input = readLine("Enter what percentage of the map should be walkable (0-100): ");
            if (input == null) {
                return -1;
            }

            double percentage;
            try {
                percentage = Double.parseDouble(input.trim());
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a number from 0 to 100.");
                continue;
            }

            if (!Double.isFinite(percentage) || percentage < 0.0 || percentage > 100.0) {
                System.out.println("The percentage must be between 0 and 100.");
                continue;
            }

            // Goal: avoid impossible placement of a player and a distinct computer entity.
            // How: calculate the same floored percentage count, then require at least two tiles.
            int openCount = (int) Math.floor((percentage / 100.0) * tileCount);
            if (openCount < 2) {
                System.out.println("That percentage creates fewer than two open tiles. "
                        + "Choose a larger percentage.");
                continue;
            }
            return openCount;
        }
    }

    // Goal: create exactly the requested number of connected walkable cells without
    // repeatedly sampling the entire map and potentially waiting a long time for progress.
    private static boolean[][] generateConnectedTerrain(int rows, int cols, int targetOpenTiles) {
        boolean[][] walkable = new boolean[rows][cols];
        boolean[][] inFrontier = new boolean[rows][cols];
        List<int[]> frontier = new ArrayList<>();

        int startRow = random.nextInt(rows);
        int startCol = random.nextInt(cols);
        walkable[startRow][startCol] = true;
        addFrontierNeighbors(startRow, startCol, walkable, inFrontier, frontier);

        int openCount = 1;
        while (openCount < targetOpenTiles) {
            // The frontier contains only closed cells adjacent to the connected region.
            // Removing a random entry preserves connectivity while guaranteeing progress.
            if (frontier.isEmpty()) {
                throw new IllegalStateException("Map generation frontier unexpectedly became empty.");
            }
            int selectedIndex = random.nextInt(frontier.size());
            int lastIndex = frontier.size() - 1;
            int[] next = frontier.get(selectedIndex);
            int[] last = frontier.remove(lastIndex);
            if (selectedIndex < lastIndex) {
                frontier.set(selectedIndex, last);
            }
            inFrontier[next[0]][next[1]] = false;

            walkable[next[0]][next[1]] = true;
            openCount++;
            addFrontierNeighbors(next[0], next[1], walkable, inFrontier, frontier);
        }
        return walkable;
    }

    private static void addFrontierNeighbors(int row, int col, boolean[][] walkable,
                                             boolean[][] inFrontier, List<int[]> frontier) {
        addFrontierCell(row - 1, col, walkable, inFrontier, frontier);
        addFrontierCell(row + 1, col, walkable, inFrontier, frontier);
        addFrontierCell(row, col - 1, walkable, inFrontier, frontier);
        addFrontierCell(row, col + 1, walkable, inFrontier, frontier);
    }

    private static void addFrontierCell(int row, int col, boolean[][] walkable,
                                        boolean[][] inFrontier, List<int[]> frontier) {
        if (row >= 0 && row < walkable.length && col >= 0 && col < walkable[0].length
                && !walkable[row][col] && !inFrontier[row][col]) {
            inFrontier[row][col] = true;
            frontier.add(new int[] {row, col});
        }
    }

    private static List<int[]> collectOpenTiles(boolean[][] walkable) {
        List<int[]> openTiles = new ArrayList<>();
        for (int row = 0; row < walkable.length; row++) {
            for (int col = 0; col < walkable[row].length; col++) {
                if (walkable[row][col]) {
                    openTiles.add(new int[] {row, col});
                }
            }
        }
        return openTiles;
    }

    // Goal: make each move a validate-then-update operation.
    // How: calculate a candidate destination, check bounds/terrain/occupancy, then commit it.
    private static boolean runMovement(boolean[][] walkable, int[] playerPosition, int[] computerPosition) {
        int rows = walkable.length;
        int cols = walkable[0].length;

        while (true) {
            printMap(walkable, playerPosition, computerPosition);
            String move = readLine("\nMake your move (WASD, or Quit): ");
            if (move == null || move.trim().equalsIgnoreCase("Quit")) {
                return false;
            }

            if (move.trim().length() != 1) {
                System.out.println("Enter one direction (W, A, S, or D), or Quit.");
                continue;
            }

            int rowChange;
            int colChange;
            switch (Character.toUpperCase(move.trim().charAt(0))) {
                case 'W':
                    rowChange = -1;
                    colChange = 0;
                    break;
                case 'A':
                    rowChange = 0;
                    colChange = -1;
                    break;
                case 'S':
                    rowChange = 1;
                    colChange = 0;
                    break;
                case 'D':
                    rowChange = 0;
                    colChange = 1;
                    break;
                default:
                    System.out.println("This input is invalid. Use W, A, S, D, or Quit.");
                    continue;
            }

            int destinationRow = playerPosition[0] + rowChange;
            int destinationCol = playerPosition[1] + colChange;
            if (destinationRow < 0 || destinationRow >= rows
                    || destinationCol < 0 || destinationCol >= cols) {
                System.out.println("You cannot move beyond the map boundary.");
            } else if (!walkable[destinationRow][destinationCol]) {
                System.out.println("That tile is blocked by a wall.");
            } else if (destinationRow == computerPosition[0]
                    && destinationCol == computerPosition[1]) {
                System.out.println("That tile is occupied by the computer.");
            } else {
                playerPosition[0] = destinationRow;
                playerPosition[1] = destinationCol;
            }
        }
    }

    private static void printMap(boolean[][] walkable, int[] playerPosition,
                                 int[] computerPosition) {
        for (int col = 0; col < walkable[0].length; col++) {
            System.out.print("\t" + (col + 1));
        }
        System.out.println("\n");

        for (int row = 0; row < walkable.length; row++) {
            System.out.print((row + 1) + "\t");
            for (int col = 0; col < walkable[row].length; col++) {
                String tile;
                if (row == playerPosition[0] && col == playerPosition[1]) {
                    tile = "P";
                } else if (row == computerPosition[0] && col == computerPosition[1]) {
                    tile = "C";
                } else {
                    tile = walkable[row][col] ? "_" : "#";
                }
                System.out.print(tile + "\t");
            }
            System.out.println("\n");
        }
    }

    // Goal: keep command-line parsing consistent and handle end-of-input cleanly.
    private static String readLine(String prompt) {
        System.out.print(prompt);
        return stdin.hasNextLine() ? stdin.nextLine() : null;
    }

    // Goal: ensure the menu's dimension constraints are enforced for every entry,
    // while malformed input results in another prompt rather than a Scanner exception.
    private static int readDimension(String name) {
        while (true) {
            String input = readLine("Enter the map " + name + " (odd integer greater than 10): ");
            if (input == null) {
                return -1;
            }

            try {
                int dimension = Integer.parseInt(input.trim());
                if (dimension > 10 && dimension % 2 == 1) {
                    return dimension;
                }
            } catch (NumberFormatException exception) {
                // Invalid text is reported below with the same guidance as an invalid size.
            }
            System.out.println("Please enter an odd integer greater than 10.");
        }
    }
}
