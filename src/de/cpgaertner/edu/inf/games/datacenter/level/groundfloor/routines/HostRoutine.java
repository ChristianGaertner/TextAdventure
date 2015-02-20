package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.routines;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;
import de.cpgaertner.edu.inf.api.routine.RootRoutine;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.command.GoCommand;

import java.io.IOException;

public class HostRoutine extends RootRoutine {


    public HostRoutine(CommandSystemManager csm) {
        super(csm);
    }

    public HostRoutine(Adapter adapter) {
        super(adapter);
    }

    @Override
    public boolean handle(Player player, Location location, Command cmd, Adapter adapter) throws IOException {

        if (cmd == null) return true;


        if (cmd instanceof GoCommand) {
            Location newLoc = player.getLocation().get(((GoCommand) cmd).getDirection());
            if (newLoc.isWalkable()) {
                player.setLocation(newLoc);
            } else {
                cmd.respondf("You cannot walk into the location in the %s", ((GoCommand) cmd).getDirection());
            }
            return true;
        }


        return false;
    }

}
