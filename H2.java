// josha204
// H2 Advanced Implementation

// Overview:
// see README.md


import java.io.*;
import java.util.*;

public class H2 {
    // Global Scanner
    public static final Scanner stdin = new Scanner(System.in);

    // Global Player
    public static Player player;

    // Global Monster
    public static Monster monster;

    // Global Map Blueprint
    public static Map initialMap;

    // Global Current Map
    public static Map currentMap;

    // Global Error Conditional
    public static boolean hasError = false;

    // Global Error String
    public static String error = "";

    public static void main(String[] args) throws IOException {

        player = new Player("Player", 100, new int[][] {{0, 0}}, new String[] {"   ", "   ", "   "}, new ArrayList<>(), new ArrayList<>(), 0);
        monster = new Monster("Monster", "idle", new int[][] {{0, 0}}, new int[][] {{0, 0}}, false);
        currentMap = new Map("initialMap", 10, 10, new Tile[][] {{}}, false);

        Menu.initialize();
        System.out.println("Welcome to this Java Dungeon game!");
        
        while (true) {
            int choice = Menu.chooseMenu("MAIN MENU", new String[] {"Start Game", "Load Game", "Map Editor", "Settings", "Quit"}, hasError, error);
            hasError = false;
            clearScreen();

            switch (choice) {
                case 0 -> startGame();
                case 1 -> loadGame();
                case 2 -> currentMap = mapEditor();
                case 3 -> settings();
                case 4 -> quit();
            }
        }
    }

    
    // Start Game
    public static /*Game*/ void startGame() {
        // 
    }


    // Load Game
    public static /*Game*/ void loadGame() {
        // Implement basic singleplayer networking and a MasterServer to handle long-term game saves.
        return;
    }



 // MAP EDITOR HIERARCHY
    // Map Editor
    public static Map mapEditor() {
        System.out.println();
        while (true) {
            int menuChoice = Menu.chooseMenu("MAP EDITOR MENU", new String[] {"Make New Map", "Generate Map", "Load Map", "Save Current Map", "View Current Map", "Edit Current Map", "Exit"}, hasError, error);
            hasError = false;
            clearScreen();

            switch (menuChoice) {
                // Make New Map
                case 0 -> currentMap = makeNewMap();

                // Generate Map
                case 1 -> currentMap = generateMap();

                // Load Map
                case 2 -> {
                    if (!player.getSavedMaps().isEmpty()) {
                        if (currentMap != null && !currentMap.getIsSaved()) {
                            int questionChoice = Menu.chooseQuestion("Your current map isn't saved yet.\n\nDo you want to save it?", new String[] {"Yes", "No"}, hasError, error);
                            hasError = false;

                            if (questionChoice == 0) {
                                currentMap.setIsSaved(true);
                                player.getSavedMaps().add(currentMap);
                            } else if (questionChoice != 1) {
                                // The question was cancelled; stay in the map editor.
                                break;
                            }
                        }
                        // With no current map, or after the save decision, proceed to loading.
                        currentMap = loadMap();
                    }
                    else {
                        hasError = true;
                        error = "You currently don't have any saved maps.\n";
                    }
                }

                // Save Current Map
                case 3 -> {
                    if (!currentMap.getName().equalsIgnoreCase("initialMap")) {
                        boolean alreadySaved = false;
                        for (Map savedMap : player.getSavedMaps()) {
                            if (savedMap.hasSameLayout(currentMap)) {
                                alreadySaved = true;
                                System.out.println("Right here");
                                break;
                            }
                        }

                        if (!alreadySaved) {
                            currentMap.setIsSaved(true);
                            player.getSavedMaps().add(currentMap);
                            System.out.println("Map saved successfully");
                        } else {
                            hasError = true;
                            error = "A map with this layout is already saved.";
                        }
                    }
                    else {
                        hasError = true;
                        error = "You currently don't have a map selected.\n";
                    }
                }

                // View Current Map
                case 4 -> {
                    if (currentMap != null && !currentMap.getName().equalsIgnoreCase("initialMap")) viewCurrentMap(currentMap);
                    else {
                        hasError = true;
                        error = "You currently don't have a map selected.\n";
                    }
                }

                // Edit Current Map
                case 5 -> {
                    if (currentMap != null && !currentMap.getName().equalsIgnoreCase("initialMap")) currentMap = editCurrentMap(currentMap);
                    else {
                        hasError = true;
                        error = "You currently don't have a map selected.\n";
                    }
                }

                // Exit
                case 6 -> {
                    return currentMap;
                }
            }
        }
    }


    // Make New Map
    public static Map makeNewMap() {
        Map newMap = null;
        return newMap;
    }


    // Generate Map
    public static Map generateMap() {
        Map newMap = new Map("newMap", 10, 10, new Tile[][] {{}}, false);
        return newMap;
    }


    // Load Map
    public static Map loadMap() {
        Map newMap = null;
        return newMap;
    }


    // View Current Map
    public static void viewCurrentMap(Map currentMap) {
    }


    // Edit Current Map
    public static Map editCurrentMap(Map currentMap) {
        return currentMap;
    }




 // SETTINGS HIERARCHY
    // Settings
    public static void settings() {
    }





 // UTILITY FUNCTIONS
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
