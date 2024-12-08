package src.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private final Map<Integer, Command> commands = new HashMap<>();

    public void registerCommand(int key, Command command) {
        commands.put(key, command);
    }

    public Command getCommand(int key) {
        return commands.get(key);
    }
}