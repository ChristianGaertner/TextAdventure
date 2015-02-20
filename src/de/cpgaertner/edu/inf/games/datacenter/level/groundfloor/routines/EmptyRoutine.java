package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.routines;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;
import de.cpgaertner.edu.inf.api.routine.Routine;

import java.io.IOException;

public class EmptyRoutine implements Routine {
    @Override
    public String getPrompt() {
        return ">>";
    }

    @Override
    public CommandSystemManager getCommandSystemManager() {
        return null;
    }

    @Override
    public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException {
        player.setName("Christian");
        return false;
    }
}
