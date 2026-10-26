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

        terminal = TerminalBuilder.builder().system(true).provider("jni").dumb(false).build();
        original = terminal.enterRawMode();

        terminal.puts(Capability.keypad_xmit);
        terminal.flush();

        reader = new BindingReader(terminal.reader());
    }

    
    // Reuseable menu method
    public static int choose(String title, String[] options) {

        if (reader == null) {
            throw new IllegalStateException("Menu not initialized");
        }

        if (options.length == 0) {
            throw new IllegalArgumentException("Menu has no options");
        }

        int selected = 0;

        while (true) {
            // Clear screen
            terminal.writer().print("\u001B[2J\u001B[H");

            terminal.writer().println("=== " +title+ " ===");
            terminal.writer().println();

            for (int i = 0; i < options.length; i++) {
                if (i == selected) {
                    terminal.writer().println("> " + options[i]);
                } else {
                    terminal.writer().println("  " + options[i]);
                }
            }

            terminal.flush();

            String input = reader.readBinding(keys);
            if (input == null) {
                return -1;
            }

            switch (input) {
                case "UP" ->
                    selected = (selected - 1 + options.length) % options.length;

                case "DOWN" ->
                    selected = (selected + 1) % options.length;

                case "SELECT" -> {
                    terminal.writer().print("\u001B[2J\u001B[H");
                    terminal.flush();
                    return selected;
                }
            }
        }
    }


    // Run only ONCE when the game closes
    public static void shutdown() throws IOException {
        if (terminal == null) {
            return;
        }

        try {
            terminal.puts(Capability.keypad_local);
            terminal.setAttributes(original);
            terminal.flush();
        } finally {
            terminal.close();
            terminal = null;
            reader = null;
        }
    }

}