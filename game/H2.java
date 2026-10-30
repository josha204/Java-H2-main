// josha204
// H2 Advanced Implementation

// Overview:
// see README.md

package game;

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

    // Global message Conditional
    public static boolean hasMessage = false;

    // Global message String
    public static String message = "";

    public static void main(String[] args) throws IOException {

        player = new Player("Player", 100, new int[][] {{0, 0}}, new String[] {"   ", "   ", "   "}, new ArrayList<>(), new ArrayList<>(), 0);
        monster = new Monster("Monster", "idle", new int[][] {{0, 0}}, new int[][] {{0, 0}}, false);
        currentMap = new Map("initialMap", 10, 10, new Tile[][] {{}}, false);

        Menu.initialize();
        System.out.println("Welcome to this Java Dungeon game!");
        
        while (true) {
            int choice = Menu.chooseMenu("MAIN MENU", new String[] {"Start Game", "Load Game", "Map Editor", "Settings", "Quit"}, hasMessage, message);
            hasMessage = false;
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
            int menuChoice = Menu.chooseMenu("MAP EDITOR MENU", new String[] {"Make New Map", "Generate Map", "Load Map", "Save Current Map", "View Current Map", "Edit Current Map", "Exit"}, hasMessage, message);
            hasMessage = false;
            clearScreen();

            switch (menuChoice) {
                // Make New Map
                case 0 -> currentMap = makeNewMap();


                // Generate Map
                case 1 -> currentMap = generateMap();


                // Load Map
                case 2 -> {
                    if (!player.getSavedMaps().isEmpty()) {
                        if (currentMap != null) {
                            boolean alreadySaved = false;
                            for (Map savedMap : player.getSavedMaps()) {
                                if (savedMap.hasSameLayout(currentMap)) {
                                    alreadySaved = true;
                                    break;
                                }
                            }

                            if (!alreadySaved) {
                                int questionChoice = Menu.chooseQuestion("Your current map isn't saved yet.\n\n Do you want to save it?", new String[] {"Yes", "No"}, hasMessage, message);
                                
                                switch (questionChoice) {
                                    case 0 -> {
                                        currentMap.setIsSaved(true);
                                        player.getSavedMaps().add(currentMap);
                                    } 
                                    case 1 -> {
                                        continue;
                                    }
                                }
                            }
                        }
                        // With no current map, or after the save decision, proceed to loading.
                        currentMap = loadMap();
                    }
                    else {
                        hasMessage = true;
                        message = "You currently don't have any saved maps.\n";
                    }
                }


                // Save Current Map
                case 3 -> {
                    if (!currentMap.getName().equalsIgnoreCase("initialMap")) {
                        boolean alreadySaved = false;
                        for (Map savedMap : player.getSavedMaps()) {
                            if (savedMap.hasSameLayout(currentMap)) {
                                alreadySaved = true;
                                break;
                            }
                        }

                        if (!alreadySaved) {
                            currentMap.setIsSaved(true);
                            player.getSavedMaps().add(currentMap);
                            hasMessage = true;
                            message = "Map saved successfully.\n";
                        } else {
                            hasMessage = true;
                            message = "A map with this layout is already saved.\n";
                        }
                    }
                    else {
                        hasMessage = true;
                        message = "You currently don't have a map selected.\n";
                    }
                }


                // View Current Map
                case 4 -> {
                    if (currentMap != null && !currentMap.getName().equalsIgnoreCase("initialMap")) viewCurrentMap(currentMap);
                    else {
                        hasMessage = true;
                        message = "You currently don't have a map selected.\n";
                    }
                }

                
                // Edit Current Map
                case 5 -> {
                    if (currentMap != null && !currentMap.getName().equalsIgnoreCase("initialMap")) currentMap = editCurrentMap(currentMap);
                    else {
                        hasMessage = true;
                        message = "You currently don't have a map selected.\n";
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


 // GENERATE MAP HIERARCHY
    // Generate Map
    public static Map generateMap() {

        MapConfig mapConfig = null;
        
        // Main implementation
        while (true) {
            hasMessage = false;
            int menuChoice = Menu.chooseMenu("GENERATE MAP MENU", new String[] {"Set Map Configuration", "Set Random Generation", "Generate Map", "Exit"}, hasMessage, message);

            switch (menuChoice) {
                // Set Map Configuration
                case 0 -> {
                    mapConfig = setMapConfiguration(mapConfig);
                }

                // Set Random Generation
                case 1 -> {

                }

                // Generate Map
                case 2 -> {
                    currentMap = mapConfig.generateMap(genMode);
                }

                // Exit
                case 3 -> {
                    return currentMap;
                }
            }
        }



        // Test case
        int height = 11;
        int width = 10;
        Tile[][] fullMap = new Tile[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                fullMap[row][col] = new Tile("#", -1);
            }
        }
        return new Map("newMap", height, width, fullMap, false);
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
    // Print Map
    public static void printMap() {

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
