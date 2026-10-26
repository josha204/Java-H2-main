// josha204
// H2 Advanced Implementation

// Overview:
// see README.md


import java.io.*;
import java.util.*;

public class H2 {
    public static final Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) {
        
        Menu.initialize();
        System.out.println("Welcome to this Java Dungeon game!");
        
        try {
            int choice = Menu.choose("MAIN MENU", new String[] {"Start Game", "Load Game", "Map Editor", "Settings", "Quit"});
            clearScreen();

            switch (choice) {
                case 0 -> startGame();
                case 1 -> loadGame();
                case 2 -> mapEditor();
                case 3 -> settings();
                case 4 -> quit();
            }
        }
    }

    
    // Start Game
    public static /*game*/ void startGame() {
        return;
    }


    // Load Game
    public static /*game*/ void loadGame() {
        return;
    }


    // Map Editor
    public static /*map*/ void mapEditor() {
        // Menu to view current map, edit current map, make new map, load map, or generate map.
        return;
    }


    // Quit
    public static void Quit() {
        return;
    }


    // Clear screen
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
