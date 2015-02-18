package de.cpgaertner.edu.inf.api.routine;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;

import java.io.IOException;

public class RootRoutine implements Routine {
    @Override
    public boolean handle(Player player, Location location, Command cmd, Adapter adapter) throws IOException {

        if (cmd == null) {
            adapter.send("Type something, try 'help' to get started!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("help")) {
            cmd.respond("help: (no args) this help display\ntest: (no args) prints 'ok'");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("test")) {
            cmd.respond("ok");
            return true;
        }

        adapter.sendf("Unknow comand <%s>, try 'help' to get started", cmd.getName());
        return true;
    }
}
