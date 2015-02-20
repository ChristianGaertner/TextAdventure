package de.cpgaertner.edu.inf.api.routine;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;

import java.io.IOException;

public interface Routine {

    public String getPrompt();

    public CommandSystemManager getCommandSystemManager();

    public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException;
}
