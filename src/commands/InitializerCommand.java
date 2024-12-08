package src.commands;

import src.Initializer;

public class InitializerCommand implements Command {
    private final Initializer initializer;

    public InitializerCommand(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public void execute() {
        initializer.initialize();
    }
}
