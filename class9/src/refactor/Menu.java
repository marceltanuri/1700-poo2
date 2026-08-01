import java.util.HashMap;
import java.util.Map;

public class Menu {
    private Map<String, Command> commands = new HashMap<>();

    public void registerCommand(String key, Command command) {
        commands.put(key, command);
    }

    public void executeCommand(String key) {
        Command command = commands.get(key);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Opción no válida");
        }
    }
}
