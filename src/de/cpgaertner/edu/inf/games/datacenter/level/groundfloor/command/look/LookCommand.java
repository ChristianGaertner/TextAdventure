package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.command.look;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.command.go.GoCommand;

public class LookCommand extends GoCommand {

    public static final String NAME = "look";

    public LookCommand(Location.Direction direction, Adapter adapter) {
        super(direction, adapter);
    }
}
