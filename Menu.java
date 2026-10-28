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
    
    private boolean hasError = false;
    private String error = " ";


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

 // GETTERS
    // Get Has Error
    public boolean getHasError() {
        return hasError;
    }

    // Get Error
    public String getError() {
        return error;
    }

 // SETTERS
    // Set Has Error
    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    // Set Error
    public void setError(String error) {
        this.error = error;
    }

    // Choose w/ Menu
    public static int chooseMenu(String title, String[] options, boolean hasError, String error) {
        return choose(title, options, false, hasError, error);
    }


    // Choose w/ Question
    public static int chooseQuestion(String question, String[] answers, boolean hasError, String error) {
        return choose(question, answers, true, hasError, error);
    }


    // Reuseable menu method
    private static int choose(String prompt, String[] options, boolean isQuestion, boolean hasError, String error) {
        if (reader == null) {
            throw new IllegalStateException("Menu not initialized");
        }
        if (options.length == 0) {
            throw new IllegalArgumentException("Menu has no options");
        }


        int selected = 0;
        while (true) {
            terminal.writer().print("\u001B[2J\u001B[H");
            
            if (hasError) {
                System.out.println(error);
            } else {
                System.out.println("\n");
            }

            if (isQuestion) {
                terminal.writer().println(prompt);
            } else {
                terminal.writer().println("=== " + prompt + " ===");
            }
            terminal.writer().println();

            for (int i = 0; i < options.length; i++) {
                terminal.writer().println((i == selected ? "> " : "  ") + options[i]);
            }

            terminal.flush();

            String input = reader.readBinding(keys);
            if (input == null) {
                return -1;
            }

            switch (input) {
                case "UP" -> selected = (selected - 1 + options.length) % options.length;
                case "DOWN" -> selected = (selected + 1) % options.length;
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