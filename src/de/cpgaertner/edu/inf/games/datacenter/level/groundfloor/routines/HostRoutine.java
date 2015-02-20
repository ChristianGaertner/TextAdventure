package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.routines;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.RootRoutine;

import java.io.IOException;

public class HostRoutine extends RootRoutine {


    @Override
    public boolean handle(Player player, Location location, Command cmd, Adapter adapter) throws IOException {

        return false;
    }

}
