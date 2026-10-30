// Joshua Aycock
// Menu.java

// A globally reuseable Java program with a navigable, terminal-based menu input interface.

package game;

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
    
    private boolean hasMessage = false;
    private String message = " ";


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
    // Get Has message
    public boolean gethasMessage() {
        return hasMessage;
    }

    // Get message
    public String getmessage() {
        return message;
    }

 // SETTERS
    // Set Has message
    public void sethasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

    // Set message
    public void setmessage(String message) {
        this.message = message;
    }

    // Choose w/ Menu
    public static int chooseMenu(String title, String[] options, boolean hasMessage, String message) {
        return choose(title, options, false, hasMessage, message);
    }


    // Choose w/ Question
    public static int chooseQuestion(String question, String[] answers, boolean hasMessage, String message) {
        return choose(question, answers, true, hasMessage, message);
    }


    // Reuseable menu method
    private static int choose(String prompt, String[] options, boolean isQuestion, boolean hasMessage, String message) {
        if (reader == null) {
            throw new IllegalStateException("Menu not initialized");
        }
        if (options.length == 0) {
            throw new IllegalArgumentException("Menu has no options");
        }


        int selected = 0;
        while (true) {
            terminal.writer().print("\u001B[2J\u001B[H");
            
            if (hasMessage) {
                System.out.println(message);
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