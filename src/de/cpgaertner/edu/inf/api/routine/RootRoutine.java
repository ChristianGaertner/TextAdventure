package de.cpgaertner.edu.inf.api.routine;

import de.cpgaertner.edu.inf.api.ExitRequestedException;
import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.parsing.BasicCommandSystemManager;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;

import java.io.IOException;

public class RootRoutine implements Routine {


    protected CommandSystemManager csm;

    public RootRoutine(CommandSystemManager csm) {
        this.csm = csm;
    }

    public RootRoutine(Adapter adapter) {
        this(new BasicCommandSystemManager(adapter));
    }

    @Override
    public String getPrompt() {
        return ">>";
    }

    @Override
    public CommandSystemManager getCommandSystemManager() {
        return csm;
    }

    @Override
    public boolean handle(Player player, Location location, Command cmd, Adapter adapter) throws IOException {

        if (cmd == null) {
            adapter.send("Type something, try 'help' to get started!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("help")) {
            cmd.respond(
                    "help: (no args) this help display\n" +
                    "test: (no args) prints 'ok'\n" +
                    "exit: (no args) quits the game"
            );
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("test")) {
            cmd.respond("ok");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("exit")) {
            cmd.respond("Exiting...");
            throw new ExitRequestedException();
        }

        adapter.sendf("Unknow comand <%s>, try 'help' to get started", cmd.getName());
        return true;
    }
}
