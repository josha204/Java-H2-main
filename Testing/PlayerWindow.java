package Testing;
// josha204

// NOTE
// The way that this program handles more typical edge cases is far from professional, and many typical edge cases are unaccounted for. 
// This is because this program's purpose is to refine a working interface for window management on a larger, grid-based map using 2D arrays and window-handling logic.

import game.*;
import java.util.*;

public class PlayerWindow {
    
    public static final Scanner stdin = new Scanner(System.in);
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
                    H2.clearScreen();
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
                    H2.clearScreen();
                    System.out.println("Please enter an odd integer greater than 10.\n\n");
                }
            }
            
            H2.clearScreen();
            System.out.println("\n=== MAP DIMENSIONS ===");
            System.out.println("Current width: " + width);
            System.out.println("Current height: " + height);


            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1) Start Simulation \n2) Reconfigure Map \n3) Quit");
            System.out.print("\nMake a choice: ");
            int choice = stdin.nextInt();
            H2.clearScreen();

            switch (choice) {
                case 1 -> begin(height, width);
                //case 2 -> reconfig();
                //case 3 -> quit();
            }
        }
    }



    public static void begin(int rows, int cols) throws ArrayIndexOutOfBoundsException {
        stdin.nextLine();
        H2.clearScreen();

        int Prow = 0;
        int Pcol = 0;

        int AIrow = 0;
        int AIcol = 0;


        // Initialize map full of '#' characters
        String[][] fullMap = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                fullMap[i][k] = "#";
            }
        }


        // Take input for what percentage of the map should be walkable, signified by '_' characters
        int openTiles = 0;
        while (true) {
            
            H2.clearScreen();
            System.out.print("Enter what percentage of the map should be walkable: ");
            Double temp = stdin.nextDouble();
            

            // Validate the input as a percentage
            if (0.0 <= temp && temp <= 100.0) {
                Double percentage = temp / 100.0;
                openTiles = (int) Math.floor(percentage * rows * cols);
                

                // Find the first open tile randomly
                int firstRow = rndObj.nextInt(rows);
                int firstCol = rndObj.nextInt(cols);
                fullMap[firstRow][firstCol] = "_";
                openTiles--;
                

                // Fill the map with connected '_' characters as needed.
                while (openTiles != 0) {
                    int rndRow = rndObj.nextInt(rows);
                    int rndCol = rndObj.nextInt(cols);

                    if (fullMap[rndRow][rndCol] != "_") {
                        openTiles--;
                        if (rndRow + 1 < rows && fullMap[rndRow + 1][rndCol].equals("_")) {
                            fullMap[rndRow][rndCol] = "_";
                        }
                        else if (rndRow > 0 && fullMap[rndRow - 1][rndCol].equals("_")) {
                            fullMap[rndRow][rndCol] = "_";
                        }
                        else if (rndCol + 1 < cols && fullMap[rndRow][rndCol + 1].equals("_")) {
                            fullMap[rndRow][rndCol] = "_";
                        }
                        else if (rndCol > 0 && fullMap[rndRow][rndCol - 1].equals("_")) {
                            fullMap[rndRow][rndCol] = "_";
                        } else {
                            openTiles++;
                        }
                    }
                }
                break;
            }
        }


        // Find the player's starting position
        while (true) {
            Prow = rndObj.nextInt(rows);
            Pcol = rndObj.nextInt(cols);
            if (fullMap[Prow][Pcol] == "_") {
                fullMap[Prow][Pcol] = "P";
                break;
            }
        }
        

        // Find the AI's starting position
        while (true) {
            AIrow = rndObj.nextInt(rows);
            AIcol = rndObj.nextInt(cols);
            if ((AIrow != Prow || AIcol != Pcol) && fullMap[AIrow][AIcol] == "_") {
                fullMap[AIrow][AIcol] = "C";
                break;
            }
        }


        // Handles the movement logic and validation until the exit conditional is met
        stdin.nextLine();
        boolean running = true;
        boolean sharedTile = false;
        while (running) {
            while (true) {
                
                // Prevents P and/or C from overwriting one another when on the same tile.
                if (Prow == AIrow && Pcol == AIcol) {
                    fullMap[Prow][Pcol] = "P C";
                }

                // Print full map
                for (int i = 0; i < cols; i++) {
                    System.out.print("\t " + (i + 1));
                }

                System.out.println("\n\n");
                for (int i = 0; i < rows; i++) {
                    System.out.print((i + 1) + "\t ");
                    for (int k = 0; k < cols; k++) {
                        System.out.print(fullMap[i][k] + "\t ");
                    }
                    System.out.println("\n\n");
                }


                // Take user input
                System.out.print("\nMake your move (WASD): ");
                String move = stdin.nextLine();
                if (move.trim().equalsIgnoreCase("Quit")) {
                    running = false;
                    return;
                }
                

                // Validate input
                if (move.trim().equalsIgnoreCase("W")) {
                    if (Prow != 0 && fullMap[Prow - 1][Pcol] != "#") {
                        if (fullMap[Prow][Pcol] == "P C") {
                            fullMap[Prow][Pcol] = "C";
                        } else {
                            fullMap[Prow][Pcol] = "_";
                        }
                        Prow -= 1;
                        fullMap[Prow][Pcol] = "P";
                    }
                } else if (move.trim().equalsIgnoreCase("A")) {
                    if (Pcol != 0 && fullMap[Prow][Pcol - 1] != "#") {
                        if (fullMap[Prow][Pcol] == "P C") {
                            fullMap[Prow][Pcol] = "C";
                        } else {
                            fullMap[Prow][Pcol] = "_";
                        }
                        Pcol -= 1;
                        fullMap[Prow][Pcol] = "P";
                    }
                } else if (move.trim().equalsIgnoreCase("S")) {
                    if (Prow != rows - 1 && fullMap[Prow + 1][Pcol] != "#") {
                        if (fullMap[Prow][Pcol] == "P C") {
                            fullMap[Prow][Pcol] = "C";
                        } else {
                            fullMap[Prow][Pcol] = "_";
                        }
                        Prow += 1;
                        fullMap[Prow][Pcol] = "P";
                    }
                } else if (move.trim().equalsIgnoreCase("D")) {
                    if (Pcol != cols - 1 && fullMap[Prow][Pcol + 1] != "#") {
                        if (fullMap[Prow][Pcol] == "P C") {
                            fullMap[Prow][Pcol] = "C";
                        } else {
                            fullMap[Prow][Pcol] = "_";
                        }
                        Pcol += 1;
                        fullMap[Prow][Pcol] = "P";
                    }
                } else {
                    System.out.println("This input is invalid. Please try again.");
                }
            }
        }
    }
}
