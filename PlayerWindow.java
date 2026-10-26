import java.util.*;

public class PlayerWindow {
    
    public static final Scanner stdin = new Scanner(System.in);
    public static final Random rndObj = new Random();

    public static void main(String[] args) {
        int width = 0;
        int height = 0;
        String temp = " ";

        while (true) {
            
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
            
            
            System.out.println("=== MAP DIMENSIONS ===");
            System.out.println("Current width: " + width);
            System.out.println("Current height: " + height);
            System.out.println();


            System.out.println("=== MAIN MENU ===");
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



    public static void begin(int rows, int cols) {
        stdin.nextLine();

        H2.clearScreen();
        int Prow = 0;
        int Pcol = 0;

        int AIrow = 0;
        int AIcol = 0;

        // Initialize Map
        String[][] fullMap = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                fullMap[i][k] = "#";
            }
        }

        Prow = rndObj.nextInt(rows);
        Pcol = rndObj.nextInt(cols);
        fullMap[Prow][Pcol] = "P";

        while (true) {
            AIrow = rndObj.nextInt(rows);
            AIcol = rndObj.nextInt(cols);

            if (AIrow != Prow || AIcol != Pcol) {
                fullMap[AIrow][AIcol] = "C";
                break;
            }
        }

        boolean running = true;
        while (running) {
            while (true) {
                // Print full map
                for (int i = 0; i < cols; i++) {
                    System.out.print("\t" + (i + 1));
                }

                System.out.println("\n\n");
                for (int i = 0; i < rows; i++) {
                    System.out.print((i + 1) + "\t");
                    for (int k = 0; k < cols; k++) {
                        System.out.print(fullMap[i][k] + "\t");
                    }
                    System.out.println("\n\n");
                }

                System.out.print("\nMake your move (WASD): ");
                String move = stdin.nextLine();

                if (move.trim().equalsIgnoreCase("W")) {
                    if (Prow != 0) {
                        fullMap[Prow][Pcol] = "#";
                        Prow -= 1;
                        fullMap[Prow][Pcol] = "P";
                    }
                } else if (move.trim().equalsIgnoreCase("A")) {
                    if (Pcol != 0) {
                        fullMap[Prow][Pcol] = "#";
                        Pcol -= 1;
                        fullMap[Prow][Pcol] = "P";
                    }
                } else if (move.trim().equalsIgnoreCase("S")) {
                    if (Prow != rows) {
                        fullMap[Prow][Pcol] = "#";
                        Prow += 1;
                        fullMap[Prow][Pcol] = "P";
                    }
                } else if (move.trim().equalsIgnoreCase("D")) {
                    if (Pcol != cols) {
                        fullMap[Prow][Pcol] = "#";
                        Pcol += 1;
                        fullMap[Prow][Pcol] = "P";
                    }
                } else {
                    System.out.println("This input is invalid. Please try again.");
                }
            }

            // Print AI's map



            // Print Player's map

        }
    }
}
