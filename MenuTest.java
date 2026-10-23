import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.Attributes;
import org.jline.utils.InfoCmp.Capability;
import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;


public class MenuTest {
    public static void main(String[] args) throws Exception {
        
        String[] options = {"Start Game", "Load Game", "Settings", "Quit"};

        try (Terminal terminal = TerminalBuilder.builder().system(true).provider("jni").dumb(false).build()) {
            
            System.out.println("Terminal: " + terminal.getClass().getName());
            System.out.println("Type: " + terminal.getType());

            Attributes original = terminal.enterRawMode();

            try {
                terminal.puts(Capability.keypad_xmit);
                terminal.flush();

                BindingReader reader = new BindingReader(terminal.reader());

                KeyMap<String> keys = new KeyMap<>();

                keys.bind("UP", "\u001B[A", "\u001BOA", "w");
                keys.bind("DOWN", "\u001B[B", "\u001BOB", "s");
                keys.bind("SELECT", "\r", "\n");
                keys.setNomatch("IGNORE");

                int selected = 0;

                while (true) {

                    // Clear screen and move cursor to the top
                    terminal.writer().print("\u001B[2J\u001B[H");

                    terminal.writer().println("\n=== MAIN MENU ===");
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

                    if (input == null) break;

                    switch (input) {
                        case "UP" ->
                            selected = (selected - 1 + options.length) % options.length;
                        
                        case "DOWN" ->
                            selected = (selected + 1) % options.length;

                        case "SELECT" -> {
                            terminal.writer().print("\u001B[2J\u001B[H");
                            terminal.writer().println("You selected: " + options[selected]);
                            terminal.flush();
                            return;
                        }
                    }
                }
            } finally {
                terminal.puts(Capability.keypad_local);
                terminal.setAttributes(original);
                terminal.flush();
            }
        }
    }
}