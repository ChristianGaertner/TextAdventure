package de.cpgaertner.edu.inf.api.routine;

import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;

public abstract class InteractionRoutine implements Routine {
    @Override
    public String getPrompt() {
        return null;
    }

    @Override
    public CommandSystemManager getCommandSystemManager() {
        return null;
    }
}
