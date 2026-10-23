// Joshua Aycock
// Menu.java

// A globally reuseable Java program with a navigable, terminal-based menu input interface.

import java.io.IOException;
import org.jline.terminal.*;
import org.jline.utils.InfoCmp.Capability;
import org.jline.keymap.*;


public class Menu {
    
    // Shared JLine objects.
    private static Terminal terminal;
    private static Attributes original;
    private static BindingReader reader;
    private static final KeyMap<String> keys = new KeyMap<>();


    // Bind up/down/enter keys for menu navigation and selection.
    static {
        keys.bind("UP", "\u001B[A", "\u001BOA", "w", "W");
        keys.bind("DOWN", "\u001B[B", "\u001BOB", "s", "S");
        keys.bind("SELECT", "\r", "\n");
        keys.setNomatch("IGNORE");
    }


    // Run only ONCE when the game starts.
    public static void initialize() throws IOException {
        
        if (terminal != null) {
            return;
        }

        terminal;
    }







}