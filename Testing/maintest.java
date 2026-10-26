package Testing;
import java.io.*;

import Menu;

public class maintest {
    public static void main(String[] args) throws IOException {
        Menu.initialize();

        try{
            int choice = Menu.choose("MAIN MENU", new String[] {"Start Game", "Load Game", "Settings", "Quit"});

            switch (choice) {
                case 0 -> System.out.println("Starting game...");
                case 1 -> System.out.println("Loading game...");
                case 2 -> System.out.println("Opening settings...");
                case 3 -> System.out.println("Thanks for playign!");
            }
        } finally {
            Menu.shutdown();
        }
    }
}
